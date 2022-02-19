/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amit.database;

import java.util.*;
/**
 *
 * @author unbxd
 */
public class Main {
    
    public static void main(String args[]){
        
        long recordBytes =  500;
        long approxNumberOfRecords = 10000;
        
        Database database = new Database("my_db");
        Table table = new Table("my_table");
        
        ArrayList<Attribute> attrList = new ArrayList<Attribute>();
        
        Attribute<Integer> intAttribute = new Attribute<Integer>("id",5);
        attrList.add(intAttribute);
        
        Attribute<String> stringAttribute = new Attribute<String>("name","Amit Aggarwal");
        attrList.add(stringAttribute);
                
        Record record = new Record(attrList);          
        table.addRecord(record);
        
        attrList.clear();
        intAttribute = new Attribute<Integer>("id",10);
        attrList.add(intAttribute);
        
        stringAttribute = new Attribute<String>("name","Deepak Aggarwal");
        attrList.add(stringAttribute);
        
        record = new Record(attrList);          
        table.addRecord(record);
        
        table.readRecord();
        
        
        
    }
}
