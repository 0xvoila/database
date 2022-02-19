/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.amit.database;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.*;

/**
 *
 * @author unbxd
 */
public class TableMeta implements Serializable{
    
    
    String nextRecordFile;
    ArrayList<String> dbRecordFiles = new ArrayList<String>();

    public TableMeta(String tableName) {
        
        this.nextRecordFile = tableName;
        this.dbRecordFiles.add(this.nextRecordFile);
    }
    
    public void readRecord(){
        
        try{
            
            for (String file  : this.dbRecordFiles){
                System.out.println(file);
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ArrayList<Record> arrRecord = (ArrayList<Record>)objectInputStream.readObject();  
            
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
