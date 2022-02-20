package com.amit.database;

import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.lang.model.element.Element;

public class Schema implements Serializable{
	
	HashMap<String, HashMap<String, String>> schema = new HashMap<String, HashMap<String,String>>();
	
	public void addFieldSchema(String fieldName,  String fieldType, String nullable) {
		
		HashMap<String, String> x = new HashMap<String,String>();
	
        x.put("not_null", nullable );
        x.put("type", fieldType);
        
        this.schema.put(fieldName,x);
	}
	
	public HashMap<String, String> getFieldSchema(String field){
		
		return this.schema.get(field);
		
	}
	
	public Record createRecord(HashMap<String, Object> json) throws Exception{
		
		ArrayList<Attribute> attrList = new ArrayList<Attribute>();
		
		for (Entry<String, Object> x : json.entrySet()) {
			
			String keyString = x.getKey();
			Object valObject = x.getValue();
			
			Boolean isValidated  = validateFieldSchema(keyString, valObject);
			
			if(isValidated) {
				HashMap<String, String> y = schema.get(keyString);
				
				if(y.get("type").equals("int")) {
					attrList.add(new Attribute<Integer>(keyString,(int)valObject));
					
				}
				else if(y.get("type").equals("string")) {
					attrList.add(new Attribute<String>(keyString,(String)valObject));
				}
				else if(y.get("type").equals("long")) {
					attrList.add(new Attribute<Long>(keyString,(long)valObject));
				}
			}
			else {
				throw new Exception("Schema validation error");
			}
		}
		
		return new Record(attrList);
	}
	
	private Boolean validateFieldSchema(String key, Object value) throws Exception{
		
		Boolean isValidated = true;
		
		HashMap<String, String> y = schema.get(key);
		
		if(y == null) {
			throw new Exception("Give field does not exists in the schema");
		}
		
		if(y.get("type").equals("int")) {
			
			if(!(value instanceof Integer)) {
				isValidated = false;
			}
			
		}
		else if(y.get("type").equals("string")) {
			
			if(!(value instanceof String)) {
				isValidated = false;
			}
			
		}
		else if(y.get("type").equals("long")) {
			
			if(!(value instanceof Long)) {
				isValidated = false;
			}
		}
		
		if(y.get("not_null").equals("true")) {
			
			if(value == null) {
				isValidated = false;
			}
		}
		
		return isValidated;
	}
}
