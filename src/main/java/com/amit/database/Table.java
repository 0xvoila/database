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
import java.security.KeyStore.Entry;
import java.util.*;

/**
 *
 * @author unbxd
 */
public class Table implements Serializable{
    
 
	private static final long serialVersionUID = 1L;
	String nextRecordFile;
	String tableName;
    ArrayList<String> dbRecordFiles = new ArrayList<String>();
    HashMap<String, Index> tableIndexMap = new HashMap<String, Index>();
    Schema schema = null;   

    public Table(String tableName, Schema schema) throws Exception {
        
    	this.tableName = tableName;
        this.nextRecordFile = tableName;
        this.dbRecordFiles.add(this.nextRecordFile);
        this.schema = schema;
        
        if(schema == null) {
        	throw new Exception("Schema of the table can not be null");
        }
    }

    public Schema getSchema() {
    	return this.schema;
    }
    
    public void createIndex(String fieldName) {
    	
    	Index index = new Index();
    	this.tableIndexMap.put(fieldName, index);
    }
    
    public void readRecord(){
        
        try{
            
            for (String file  : this.dbRecordFiles){
                System.out.println(file);
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ArrayList<Record> arrRecord = (ArrayList<Record>)objectInputStream.readObject();
                objectInputStream.close();
            
                for (Record record : arrRecord){
                    for ( Attribute attribute : record.attributeList){
                        System.out.println(attribute.getValue(attribute.key));    
                        }
                
                }                
            }

        }
        catch(Exception exception){
            exception.printStackTrace();
        }
        
    }
    
    public void writeRecord(Record record){
        
        try{
            Path path = Paths.get(nextRecordFile);
            long size = 0;
            
            if(Files.exists(path)){
                size = Files.size(path);        
            }
            else{
                path = Files.createFile(Paths.get(nextRecordFile));   
            }
            
            
            if(size < 10 & size > 0){
                
                System.out.println("Records exists so far");
                
//                Open the file first, deserialize all objects and append this object then serialize again
                
                FileInputStream fileInputStream = new FileInputStream(this.nextRecordFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ArrayList<Record> arrRecord = (ArrayList<Record>)objectInputStream.readObject();    
                objectInputStream.close();
                arrRecord.add(record);
                    
                    //Write to this file only 
                FileOutputStream fileOutputStream = new FileOutputStream(this.nextRecordFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                
                objectOutputStream.writeObject(arrRecord);
                objectOutputStream.close();
                fileOutputStream.close();
                
                
              
            }
            else if (size == 0 ){
                
                System.out.println("No record exists so far");
                
                //Write to this file only 
                ArrayList<Record> arrRecord = new ArrayList<>();
                
                arrRecord.add(record);
                FileOutputStream fileOutputStream = new FileOutputStream(this.nextRecordFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                
                objectOutputStream.writeObject(arrRecord);
                objectOutputStream.close();
                fileOutputStream.close();
            }
            else{
                
                System.out.println("Creating new output file");
                
                ArrayList<Record> arrRecord = new ArrayList<>();
                arrRecord.add(record);
                
//                Close this file and open new file and assign it to nextRecordFile
                String[] x = this.nextRecordFile.split("-");
                
                if(x.length == 1){
                    this.nextRecordFile = this.nextRecordFile +  "-" + "1";
                    this.dbRecordFiles.add(this.nextRecordFile);
                }
                else{
                    this.nextRecordFile = x[0] + "-" + Integer.toString(Integer.parseInt(x[1]) + 1);
                    this.dbRecordFiles.add(this.nextRecordFile);
                }
                
                //Write to this file only 
                FileOutputStream fileOutputStream = new FileOutputStream(this.nextRecordFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                
                objectOutputStream.writeObject(arrRecord);
                objectOutputStream.close();
                fileOutputStream.close();
                
            }
        }
        catch(Exception e){
            
            e.printStackTrace();
        }
        
                    
    }
     
}
