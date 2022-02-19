/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amit.database;

import java.util.*;
import java.io.*;

/**
 *
 * @author unbxd
 * 
 */
public class Table implements Serializable{
 
    
    TableMeta tableMeta;
    String tableName;
  
    public Table(String tableName){
        this.tableName = tableName;
        tableMeta = new TableMeta(this.tableName);
    }
    
    public void addRecord(Record record){
        
        
//        Check in table meta where information is suppose to go i.e in which file 

        tableMeta.writeRecord(record);

       

//        Write the information in the file 

//        Update the AVL Tree with the file node 

    }
    
    public void readRecord(){
        tableMeta.readRecord();
    }
}
