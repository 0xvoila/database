package com.amit.database;


/**
 *
 * @author unbxd
 */


public class StringIndex extends Index<String> {
	
	
	public Boolean compareKey(String key1, String key2) {
		
		if(key1.compareToIgnoreCase(key2) > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private String inOrderFilter(AVLNode rootNode, String key) {
		
		if(rootNode == null) {
			return null;
		}
		
		inOrderFilter(rootNode.leftNode, key);
		
		if(rootNode.equals(key)) {
			return rootNode.recordFile;
		}
		inOrderFilter(rootNode.rightNode, key);
		
		
		return null;
	}
	public String getByKey(String key) {
		
		return inOrderFilter(this.rootNode, key);
		
	}
	    
    
}
