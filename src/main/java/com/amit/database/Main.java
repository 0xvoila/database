
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
        	
//        	
//        	while(true) {
//        		String command = scanner.nextLine();
//        		
//        		System.out.println(command);
//        		
//        		String[] x = command.split(" ",2);
//        		
//        		System.out.println(x[0]);
//        		System.out.println(x[1]);
//        		
//        	}
        	
        	
        	
        	while(true) {
        		
        		String command = scanner.nextLine();
        		command = command.replaceAll("\s+", " ");
        		command = command.replaceAll(",\s+", ",");
        		
        		System.out.println("Command is " + command );
        		
        		if(command.contains("use database")) {
        			
        			try {
        				database = obj.useDatabase(command.split(" ")[2]);
            			System.out.println("Using database " + command.split(" ")[2]);	
        			}
        			catch(Exception exception) {
        				System.out.println(exception.getMessage());
        				continue;
        			}
        			
        		}
        		else if (command.contains("create database")) {
        			
        			try {
        				database = obj.createDatabase(command.split(" ")[2]);
            			System.out.println("Created database " + command.split(" ")[2]);	
        			}
        			catch (Exception exception) {
						// TODO: handle exception
        				System.out.println(exception.getMessage());
        				continue;
					}
        			
        		}
        		
        		else if (command.contains("use table")) {
        			
        			try {
        				table = database.useTable(command.split(" ")[2]);
           			 	System.out.println("Using table " + command.split(" ")[2]);	
        			}
        			catch(Exception exception) {
        				// TODO: handle exception
        				System.out.println(exception.getMessage());
        				continue;
        			}
        			 
        		}
        		
        		else if (command.contains("create table")){
       			 	
        			try {
            			if(schema == null) {
            				System.out.println("please create the schema first by <create schema <fieldname> string/int");
            				continue;
            			}
            			
            			System.out.println("table name is " + command.split(" ")[2]);
            			System.out.println("schema is " + schema.getFieldSchema("id"));
            			System.out.println("schema is " + schema.getFieldSchema("name"));
            			
            			table = database.createTable(command.split(" ")[2], schema);
            			
            			System.out.println("Created table " + command.split(" ")[2]);        				
        			}
        			catch(Exception exception) {
        				// TODO: handle exception
        				System.out.println(exception.getMessage());
        				continue;
        			}

        		}
        		
        		else if (command.contains("create schema")) {
        			
        			try {
            			schema = new Schema();
            			String schemaString =  command.split(" ", 3)[2];
            			System.out.println(schemaString);
            					
            			String[] keySchemaList = schemaString.split(",");
            			
            			for(String keySchema: keySchemaList) {
            				
            				schema.addFieldSchema(keySchema.split(" ")[0].replaceAll("\s+", "") , keySchema.split(" ")[1].replaceAll("\s+", ""), "false");	
            			}
            			
            			System.out.println("Created schema for field " + command.split(" ")[2] + " of type " + command.split(" ")[3]);        				
        			}
        			catch(Exception exception) {
        				// TODO: handle exception
        				System.out.println(exception.getMessage());
        				continue;
        			}
        		}
        		
        		else if (command.contains("query")){
        			
        			try {
            			Record record = table.query(command.split(" ")[1], command.split(" ")[2]);
            			
            			System.out.println("Querying table " + table.tableName + " from database " + database.databaseName);
            			
            			for(Attribute attribute : record.attributeList) {
            				System.out.print(attribute.key + " " + attribute.value);
            			}        				
        			}
        			
        			catch(Exception exception) {
        				// TODO: handle exception
        				System.out.println(exception.getMessage());
        				continue;
        			}
        			

        		}
        		
        		else if (command.contains("insert")) {
        			
        			try {
            			System.out.println("Inserting into table " + table.tableName + " from database " + database.databaseName);
            			
            			String[] keyValueList = command.split(" ",2)[1].split(",");
            			
            			HashMap<String, Object> x = new HashMap<String, Object>();
            			
            			for(String keyValue: keyValueList) {
            				
            				String value = keyValue.split(" ",2)[1].replaceAll("\s+", "");
            				Boolean isInt =  false, isLong = false, isFloat = false;
            				
            				try {
            					x.put(keyValue.split(" ",2)[0].replaceAll("\s+", ""), Integer.parseInt(value));
            					isInt = true;
            				}
            				catch (Exception e) {
								// TODO: handle exception
            					isInt = false;
							}
            				
            				if(! isInt) {
            					try {
                					x.put(keyValue.split(" ",2)[0].replaceAll("\s+", ""), Long.parseLong(value));
                					isLong = true;
                				}
                				
                				catch(Exception exception) {
                					isLong = false;
                				}	
            				}
            				
            				if (!isInt && !isLong) {
                				try {
                					x.put(keyValue.split(" ",2)[0].replaceAll("\s+", ""), Float.parseFloat(value));
                					isFloat = true;
                				}
                				
                				catch(Exception exception) {
                					isFloat =  false;
                				}            					
            				}
            				
            				if(!isFloat && !isInt && !isLong) {
            					try {
                					x.put(keyValue.split(" ",2)[0].replaceAll("\s+", ""), value);
                					isFloat = true;
                				}
                				
                				catch(Exception exception) {
                					isFloat =  false;
                				}  
            				}
            				
            			}
            			
            			System.out.println(x);
            			Record record = schema.createRecord(x);                 
            			table.writeRecord(record);        				
        			}
        			
        			catch(Exception exception) {
        				// TODO: handle exception
        				System.out.println(exception.getMessage());
        				continue;
        			}
        			

        		
        		}
        		
        		else if (command.contains("create index")) {
        			
        			try {
            			System.out.println("Creating index into table " + table.tableName + " from database " + database.databaseName);
            			
            			table.createIndex(command.split(" ")[2]);        				
        			}
        			catch(Exception exception) {
        				// TODO: handle exception
        				System.out.println(exception.getMessage());
        				continue;
        			}
        			

        		}
        	}

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
