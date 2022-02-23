
package com.amit.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        	
        	Scanner scanner = new Scanner(new InputStreamReader(System.in));
        	Database database = null;
        	Table table = null;
        	Schema schema = null;
        	
        	while(true) {
        		
        		String command = scanner.nextLine();
        		
        		if(command.contains("use database")) {
        			
        			database = obj.useDatabase(command.split(" ")[2]);
        			System.out.println("Using database " + command.split(" ")[2]);
        		}
        		else if (command.contains("create database")) {
        			database = obj.createDatabase(command.split(" ")[2]);
        			
        			System.out.println("Created database " + command.split(" ")[2]);
        		}
        		
        		else if (command.contains("use table")) {
        			
        			 table = database.useTable(command.split(" ")[2]);
        			 System.out.println("Using table " + command.split(" ")[2]);
        		}
        		
        		else if (command.contains("create table")){
       			 	
        			if(schema == null) {
        				throw new Exception("please create the schema first by <create schema <fieldname> string/int");
        			}
        			table = database.createTable(command.split(" ")[2], schema);
        			
        			System.out.println("Created table " + command.split(" ")[2]);
        		}
        		
        		else if (command.contains("create schema")) {
        			schema = new Schema();
        			schema.addFieldSchema(command.split(" ")[2], command.split(" ")[3], "false");
        			
        			System.out.println("Created schema for field " + command.split(" ")[2] + " of type " + command.split(" ")[3]);
        		}
        		
        		else if (command.contains("query")){
        			
        			Record record = table.query(command.split(" ")[1], command.split(" ")[2]);
        			
        			System.out.println("Querying table " + table.tableName + " from database " + database.databaseName);
        			
        			for(Attribute attribute : record.attributeList) {
        				System.out.print(attribute.key + " " + attribute.value);
        			}
        		}
        		
        		else if (command.contains("insert")) {
        			
        			System.out.println("Inserting into table " + table.tableName + " from database " + database.databaseName);
        			
        			String[] keyValueList = command.split(" ")[1].split(",");
        			
        			HashMap<String, Object> x = new HashMap<String, Object>();
        			
        			for(String keyValue: keyValueList) {
        				x.put(keyValue.split(" ")[0], keyValue.split(" ")[1]);
        			}
        			
        			Record record = schema.createRecord(x);                 
        			table.writeRecord(record);
        		
        		}
        		
        		else if (command.contains("create index")) {
        			
        			System.out.println("Creating index into table " + table.tableName + " from database " + database.databaseName);
        			
        			table.createIndex(command.split(" ")[0]);
        		}
        	}
//            Database database = obj.createDatabase("my_db");    
            
            
            
//            Schema schema = new Schema();
//            schema.addFieldSchema("id", "int", "false");
//            schema.addFieldSchema("name", "string", "false");
            
//            Table table = database.useTable("my_table");
//            
//            Record record = table.query("id", 3);
//            
//            System.out.println(record);
            
//            Table table = database.useTable("my_table");
//            Schema schema = table.getSchema();
            
//            HashMap<String, Object> x = new HashMap<String, Object>();
////            x.put("id", 1);
//            x.put("name", "DA");
//            Record record = schema.createRecord(x);                 
//            table.writeRecord(record);
//        
//            x.put("id", 2);
//            x.put("name", "XA");
//            record = schema.createRecord(x);
//            table.writeRecord(record);
            
//            x.put("id", 3);
//            x.put("name", "DB");	
//            record = schema.createRecord(x);
//            table.writeRecord(record);
            
//            table.createIndex("id");
//            table.createIndex("name");
        
//            table.readRecord();
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
                @SuppressWarnings("unchecked")
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
                @SuppressWarnings("unchecked")
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
