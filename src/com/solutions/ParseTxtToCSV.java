package com.solutions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ParseTxtToCSV {
	
    public static void main(String[] args) throws IOException {
	
	  File file = new File(
	            "C:\\Users\\donga\\eclipse-workspace\\Find_String\\sentences.txt");
	 
	        BufferedReader reader
	            = new BufferedReader(new FileReader(file));
	        BufferedWriter writer = null;
	       writer = new BufferedWriter(new FileWriter(new File("sentences.csv"))); 
	        
	        Map<String,String> output = new HashMap<>();
		
	        String st;
   int i=0;
	        while ((st = reader.readLine()) != null) {
	      
	            if(!output.containsKey(st) && st.matches("[a-zA-Z0-9._^%$#!~@, -]+")) {
	          i++;
	            String s = st.replace("\"", "\"\"");
                       s = "\"" + s + "\"";
	            	   writer.write(s);
	            	 
	                   writer.newLine();
	            }
	            output.put(st,"");
	         }
	       
            writer.flush();
            writer.close();
            
	       System.out.println("Parsing done");
	 
	}
}
