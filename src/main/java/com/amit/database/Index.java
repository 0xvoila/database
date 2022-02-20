 

package com.amit.database;

/**
 *
 * @author unbxd
 */

class AVLNode{
    int key;
    int value;
    int height;
    String recordFile;
    AVLNode leftNode;
    AVLNode rightNode;
    
    AVLNode(int key, int value){
        this.key = key;
        this.value = value;
        
    }
           
}

public class Index {
    
    AVLNode rootNode = null;
     
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
    
    public AVLNode addNode(AVLNode rootNode, int key, int value){
        
        AVLNode node = new AVLNode(key, value);
        
        return attachNode(rootNode, node);
    }
    
    private AVLNode attachNode(AVLNode rootNode, AVLNode newNode){
        
        if (rootNode == null){
            return newNode;
        }
        
        if(rootNode.key > newNode.key){
            rootNode.leftNode = attachNode(rootNode.leftNode, newNode);
            
//            Now rotate the tree 
        }
        else if(rootNode.key < newNode.key) {
            rootNode.rightNode = attachNode(rootNode.rightNode, newNode);
            
//            Now rotate the tree 
        }
        else{
            return rootNode;
        }
        
//        Here rebalance the tree 
        
        int balanceFactor = getBalanceFactor(rootNode);
        
        if ( balanceFactor > 1 && newNode.key < rootNode.leftNode.key){
//            It is left left case
            return rightRotate(rootNode);
        }
        if ( balanceFactor > 1 && newNode.key > rootNode.rightNode.key){
//         It is left right case    
            rootNode.leftNode = leftRotate(rootNode.leftNode);
            return rightRotate(rootNode);
        }
        if ( balanceFactor < -1 && newNode.key > rootNode.rightNode.key){
//           It is right right case
             return leftRotate(rootNode);
        }
        
        if ( balanceFactor < -1 && newNode.key < rootNode.rightNode.key){
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
    
    public void preOrder(AVLNode node){
        
        if(node == null){
            return;
        }
        
        System.out.println(node.key);
        preOrder(node.leftNode);
        preOrder(node.rightNode);
    }
    
    public static void main(String args[]){
        Index avlTree = new Index();
        
        avlTree.rootNode = avlTree.addNode(avlTree.rootNode,10, 5);
        avlTree.rootNode = avlTree.addNode(avlTree.rootNode, 20, 0);
        avlTree.rootNode = avlTree.addNode(avlTree.rootNode,30, 0);
        avlTree.rootNode = avlTree.addNode(avlTree.rootNode,40, 0);
        avlTree.rootNode = avlTree.addNode(avlTree.rootNode,50, 0);
        avlTree.rootNode = avlTree.addNode(avlTree.rootNode,25, 0);
        avlTree.rootNode = avlTree.addNode(avlTree.rootNode,70, 0);
        
        avlTree.preOrder(avlTree.rootNode);
        
        int height = avlTree.height(avlTree.rootNode);
        System.out.println(height);
        
        int balanceFactor = avlTree.getBalanceFactor(avlTree.rootNode);
        System.out.println(balanceFactor);
    }
}
