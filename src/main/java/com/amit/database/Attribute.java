/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amit.database;

import java.io.Serializable;

/**
 *
 * @author unbxd
 */
public class Attribute implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String key;
    Object value;
    
    public Attribute(String key, Object value) {
        this.key = key;
        this.value = value;
    }
    
    public Object getValue(String key){
        return this.value;
        
    }
    
}
