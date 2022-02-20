
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
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
            
//            Schema schema = new Schema();
//            schema.addFieldSchema("id", "int", "false");
//            schema.addFieldSchema("name", "string", "false");
            
//            Table table = database.createTable("my_table",schema);
            
            Table table = database.useTable("my_table");
            Schema schema = table.getSchema();
            
            HashMap<String, Object> x = new HashMap<String, Object>();
//            x.put("id", 100);
//            x.put("name", "Praveen Menon");
//            Record record = schema.createRecord(x);                 
//            table.writeRecord(record);
//        
            x.put("id", "1");
            x.put("name", "Deepak Aggarwal");
            Record record = schema.createRecord(x);
            table.writeRecord(record);
        
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
                objectInputStream.close();
                
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
                objectInputStream.close();
                
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
