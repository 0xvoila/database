/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amit.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
/**
 *
 * @author unbxd
 */
public class Main implements Serializable{
   
    String masterFile;
    ArrayList<Database> databaseList = new ArrayList<Database>();
        
    Main(){
        
        this.masterFile = "main";
    }
    
    public static void main(String args[]){
        
        
        Main obj = new Main();
         
        try{
//            Database database = obj.createDatabase("my_db");    
            
            Database database = obj.useDatabase("my_db");    
            Table table = database.createTable("my_table");
            
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
        catch(Exception exception){
            System.out.println(exception.getMessage());
        }
        
    }
    
    public Database createDatabase(String databaseName) throws Exception{
           
        
            Path path = Paths.get(masterFile);
            long size = 0;
            
            if(Files.exists(path)){
                size = Files.size(path);        
            }
            else{
                path = Files.createFile(Paths.get(masterFile));   
            }
            
            if(size == 0){
                
                Database database = new Database(databaseName);
                databaseList.add(database);
                
                FileOutputStream fileOutputStream = new FileOutputStream(masterFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(databaseList);
            
                objectOutputStream.close();
                fileOutputStream.close();
                
                return database;
                
            }
            else{
                
                
                FileInputStream fileInputStream = new FileInputStream(masterFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ArrayList<Database> arrDatabase = (ArrayList<Database>)objectInputStream.readObject();        
                
                for(Database database : arrDatabase){
                    
                    if(database.databaseName.equals(databaseName)){
                        throw new Exception("Database already exists");
                    }
                }
                
                Database database = new Database(databaseName);
                databaseList.add(database);
                
                FileOutputStream fileOutputStream = new FileOutputStream(masterFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(databaseList);
            
                objectOutputStream.close();
                fileOutputStream.close();
            }
        
        
        return null;
    }
    
    public Database useDatabase(String databaseName){
        
        try{
            Path path = Paths.get(masterFile);
            long size = 0;
            
            if(Files.exists(path)){
                size = Files.size(path);        
            }
            else{
                path = Files.createFile(Paths.get(masterFile));   
            }
            
            if(size == 0){
                throw new Exception("database does not exists");
            }
            else{
                
                
                FileInputStream fileInputStream = new FileInputStream(masterFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ArrayList<Database> arrDatabase = (ArrayList<Database>)objectInputStream.readObject();        
                
                for(Database database : arrDatabase){
                    
                    if(database.databaseName.equals(databaseName)){
                        return database;
                    }
                }
                
                throw new Exception("Database does not exists");
            }
        }
        catch(Exception exception){
            
        }
        return null;
    }
}
