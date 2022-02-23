 

package com.amit.database;

import com.amit.database.Index.AVLNode;

/**
 *
 * @author unbxd
 */


public class IntegerIndex extends Index<Integer> {
	
	
	public Boolean compareKey(Integer key1, Integer key2) {
		
		if(key1 > key2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private String inOrderFilter(AVLNode rootNode, Integer key) {
		
		if(rootNode == null) {
			return null;
		}
		
		inOrderFilter(rootNode.leftNode, key);
		
		if(rootNode.key == key) {
			return rootNode.recordFile;
		}
		inOrderFilter(rootNode.rightNode, key);
		
		return null;
	}
	public String getByKey(Integer key) {
		
		return inOrderFilter(this.rootNode, key);
		
	}
    
    
}
