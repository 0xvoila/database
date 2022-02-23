package com.amit.database;


// This Index implemented using AVLTree. As of now it does not handle equal keys. 
// If a new key K1 being inserted in the AVL where K2 already exists where K2 = K1, then K1 is being considered lesser than K2.
public abstract class Index<T>{
	
    AVLNode rootNode = null;
    
	public class AVLNode{
	    T key;
	    int height;
	    String recordFile;
	    AVLNode leftNode;
	    AVLNode rightNode;
	    
	    AVLNode(T key, String recordFile){
	        this.key = key;
	        this.recordFile = recordFile;
	        
	    }
	           
	} // AVL Node close 
	
    public int height(AVLNode node){
        
        int height = 0;
        
        if ( node == null){
            return -1;
        }
        
        int leftHeight = height(node.leftNode);
        int rightHeight = height(node.rightNode);
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
	
    
    private int getBalanceFactor(AVLNode node){
        
        if ( node == null ){
            return 0;
        }
        else{
            return height(node.leftNode) - height(node.rightNode);
        }
    }
    
    public AVLNode addNode(AVLNode rootNode, T key, String recordFile){
        
        AVLNode node = new AVLNode(key, recordFile);
        
        return attachNode(rootNode, node);
    }
    
    
    public abstract Boolean compareKey(T nodeKey, T newNodeKey);
    
    private AVLNode attachNode(AVLNode rootNode, AVLNode newNode){
        
        if (rootNode == null){
            return newNode;
        }
        
        if(compareKey(rootNode.key, newNode.key)){
            rootNode.leftNode = attachNode(rootNode.leftNode, newNode);
            
//            Now rotate the tree 
        }
        else if(!compareKey(rootNode.key, newNode.key)) {
            rootNode.rightNode = attachNode(rootNode.rightNode, newNode);
            
//            Now rotate the tree 
        }
        else{
            return rootNode;
        }
        
//        Here rebalance the tree 
        
        int balanceFactor = getBalanceFactor(rootNode);
        
        if ( balanceFactor > 1 &&  !compareKey(newNode.key, rootNode.leftNode.key)){
//            It is left left case
            return rightRotate(rootNode);
        }
        if ( balanceFactor > 1 && compareKey(newNode.key, rootNode.rightNode.key)){
//         It is left right case    
            rootNode.leftNode = leftRotate(rootNode.leftNode);
            return rightRotate(rootNode);
        }
        if ( balanceFactor < -1 && compareKey(newNode.key,rootNode.rightNode.key)){
//           It is right right case
             return leftRotate(rootNode);
        }
        
        if ( balanceFactor < -1 && !compareKey(newNode.key, rootNode.rightNode.key)){
//            it is right left case 
              rootNode.rightNode = rightRotate(rootNode.rightNode);
              return leftRotate(rootNode); 
                      
        }
        
        return rootNode;
        
    }
    
    private AVLNode rightRotate(AVLNode node){
        
        AVLNode rootNode = node.leftNode;
        AVLNode rightNode = rootNode.rightNode;
        
        rootNode.rightNode = node;
        node.leftNode = rightNode;
        
        return rootNode;
        
    }
    
    private AVLNode leftRotate(AVLNode node){
        
        AVLNode rootNode = node.rightNode;
        AVLNode leftNode = rootNode.leftNode;
        
        rootNode.leftNode = node;
        node.rightNode = leftNode;
        
        return rootNode;
    }
    
    public void inOrder(AVLNode rootNode){
        
        if(rootNode == null){
            return;
        }
        
        inOrder(rootNode.leftNode);
        System.out.println(rootNode.key);
        inOrder(rootNode.rightNode);
    }
    
    public abstract String getByKey(T key);
    
    public void preOrder(AVLNode node){
        
        if(node == null){
            return;
        }
        
        System.out.println(node.key);
        preOrder(node.leftNode);
        preOrder(node.rightNode);
    }
   
	
}
