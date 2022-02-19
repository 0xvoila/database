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
public class Attribute<V> implements Serializable{
    
    String key;
    V value;
    
    public Attribute(String key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public V getValue(String key){
        return this.value;
    }
    
}
