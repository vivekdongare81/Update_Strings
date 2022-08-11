package com.solutions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BinaryStringTree {

       private String data;
       private BinaryStringTree left;
       private BinaryStringTree right;
       private ArrayList<Integer> id = new ArrayList<Integer>();
       private int value;

       public BinaryStringTree() {
              this.data = null;
              this.left = null;
              this.right = null;
              this.id.add(-1);
       }

       public BinaryStringTree(String data, int id) {
              this.data = data;
              this.left = null;
              this.right = null;
              this.id.add(id);
       }

       public static BinaryStringTree createTree(String content) {
              BinaryStringTree bstree = new BinaryStringTree();
              if (content != null) {

                     bstree = new BinaryStringTree();
                     bstree.addNode(content, -1);
              }
              return bstree;
       }

       public void addNode(String data, int id) {
              if (this.data == null) {
                     this.data = data; // System.out.println(data+" "+"here");
              } else {
                     // System.out.println(this.data+" "+data+" "+this.data.compareTo(data));
                  if(this.data.compareTo(data)==0) {
                	  this.id.add(id);
                  }   
                  else if (this.data.compareTo(data) < 0) {
                    	 
                            if (this.left != null) {
                                   this.left.addNode(data, id);
                            } else {
                                   this.left = new BinaryStringTree(data, id);
                            }

                     } else {
                            if (this.right != null) {
                                   this.right.addNode(data, id);
                            } else {
                                   this.right = new BinaryStringTree(data, id);
                            }

                     }
              }
       }

       public void update(String data, int value) {
              if (this.data == null) {
                     this.data = data;
                     // System.out.println(" no got");
              } else if (this.data.equals(data)) {
                     this.value = value;
                     System.out.println(" got it");
              } else {
                     if (this.data.compareTo(data) < 0) {
                            if (this.left != null) {
                                   this.left.update(data, value);
                            } else {
                                   System.out.println(" not found");
                            }

                     } else {
                            if (this.right != null) {
                                   this.right.update(data, value);
                            } else {
                                   System.out.println("not found");
                            }

                     }
              }
       }

       static TreeMap<Integer, String> hm = new TreeMap<Integer, String>();

       public void traverseInOrder() {
              if (this.left != null) {
                     this.left.traverseInOrder();
              }
              if (this.value != 0) {
            	  for(int i=0;i<this.id.size();i++) {
            		  hm.put(this.id.get(i), this.data + ", - ," + this.value);
            	  }
            	     
              }else {
            	  for(int i=0;i<this.id.size();i++) {
            		  hm.put(this.id.get(i), this.data);
            	  }
              }
                
           
              // System.out.println(this.data+" "+this.id+" " +this.value);
              if (this.right != null) {
                     this.right.traverseInOrder();
              }
       }

       public static void main(String[] args) throws IOException, ParseException {

              BinaryStringTree root = BinaryStringTree.createTree("m"); 

              long start = System.currentTimeMillis();
          
              // preprocessing

              File file = new File("C:\\Users\\donga\\eclipse-workspace\\Find_String\\dataSet2.csv");

              BufferedReader reader = new BufferedReader(new FileReader(file));
              String st = "";
              int i = 0;
              while ((st = reader.readLine()) != null) {
                     i++;

                     root.addNode("\"" + st.split(",")[2].trim() + "\"", i);
              }
              long end = System.currentTimeMillis();
              System.out.println("Time - " + (end - start) + " pre processing");

          
              // processing

              JSONParser parser = new JSONParser();
              Object obj = parser
                            .parse(new FileReader("C:\\Users\\donga\\eclipse-workspace\\Find_String\\input2.json"));
              JSONObject jsonObject = (JSONObject) obj;
              JSONArray companyList = (JSONArray) jsonObject.get("inputPair");

              long start1 = System.currentTimeMillis();

              Iterator<JSONObject> iterator = companyList.iterator();
              while (iterator.hasNext()) {

                     JSONObject o = iterator.next();

                     String key = (String) o.get("str");
                     long value = (long) o.get("num");
             
                     root.update("\"" + key + "\"", (int) value);
              }

              long end1 = System.currentTimeMillis();
              System.out.println("Time - " + (end1 - start1) + " ms   O(m* logN) ");

         
              // postprocessing
              
              long start2 = System.currentTimeMillis();

              root.traverseInOrder();

              BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Result.csv")));

              for (Map.Entry<Integer, String> e : root.hm.entrySet()) {
                     if (e.getKey() == -1) {
                            continue;
                     }
                     writer.write(e.getValue());
                     writer.newLine();
              }

              long end2 = System.currentTimeMillis();
              System.out.println("Time - " + (end2 - start2) + " post processing");

       }

}
