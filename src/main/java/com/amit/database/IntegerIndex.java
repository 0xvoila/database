 

package com.amit.database;


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
    
    
}
