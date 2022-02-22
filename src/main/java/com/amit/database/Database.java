
package com.amit.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author unbxd
 */
public class Database implements  Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Table> tableList = new ArrayList<Table>();
    String databaseName;
    String databaseFile;
    
    public Database(String databaseName){
        this.databaseName = databaseName;
        this.databaseFile = databaseName;
    }
    
    public Table useTable(String tableName) throws Exception{
    	
        Path path = Paths.get(databaseFile);
        long size = 0;
        
        if(Files.exists(path)){
            size = Files.size(path);        
        }
        else{
        	throw new Exception("Database files does not exists");   
        }
        
        if(size > 0){
            
            FileInputStream fileInputStream = new FileInputStream(databaseFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            @SuppressWarnings("unchecked")
			ArrayList<Table> arrTable = (ArrayList<Table>)objectInputStream.readObject();        
            objectInputStream.close();
            
            for(Table table : arrTable){
                
                if(table.tableName.equals(tableName)){
                	return table;
                }
            }
            
            throw new Exception("table does not exists");
           
        }
        
            
        return null;
    }
    
    public Table createTable(String tableName, Schema schema) throws Exception{
        
//        First read the databaseFile and check if table already exists, if yes then throw error
//        If table does not exists, then create the new table and append it to the list and serialize again
        Table newTable = null;
        
        
            Path path = Paths.get(databaseFile);
            long size = 0;
            
            if(Files.exists(path)){
                size = Files.size(path);        
            }
            else{
            	path = Files.createFile(Paths.get(databaseFile));    
            }
            
            if(size > 0){
                
                FileInputStream fileInputStream = new FileInputStream(databaseFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                @SuppressWarnings("unchecked")
				ArrayList<Table> arrTable = (ArrayList<Table>)objectInputStream.readObject();        
                objectInputStream.close();
                
                for(Table table : arrTable){
                    
                    if(table.tableName.equals(tableName)){
                        throw new Exception("Table already exists");
                    }
                }
               
            }
            
            
            
            newTable = new Table(tableName, schema);
            tableList.add(newTable);
            
            FileOutputStream fileOutputStream = new FileOutputStream(this.databaseFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tableList);
            
            objectOutputStream.close();
            fileOutputStream.close();
                
            return newTable;
               
    }
    
    public void deleteTable(String tableName){
        
    }
}
