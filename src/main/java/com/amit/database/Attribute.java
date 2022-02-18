/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amit.database;

/**
 *
 * @author unbxd
 */
public class Attribute<K, V> {
    
    K key;
    V value;
    
    public Attribute(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public V getValue(K key){
        return this.value;
    }
    
}
