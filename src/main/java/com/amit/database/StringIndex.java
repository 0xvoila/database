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
	    
    
}
