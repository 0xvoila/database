/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amit.database;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author unbxd
 */
public class Record implements Serializable{
    
    ArrayList<Attribute> attributeList = new ArrayList<Attribute>();

    public Record(ArrayList<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
    
    
}
