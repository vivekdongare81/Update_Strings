package com.solutions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.solutions.*;

public class Hashmap {

       public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

              long start = System.currentTimeMillis();

              // preprocessing

              ArrayList<String> arr = new ArrayList();
         
              File file = new File("C:\\Users\\donga\\eclipse-workspace\\Find_String\\dataSet2.csv");

              BufferedReader reader = new BufferedReader(new FileReader(file));
              String st = "";
              while ((st = reader.readLine()) != null) {
                     arr.add("\"" + st.split(",")[2].trim() + "\"");
              }

              HashMap<String, Long> hm = new HashMap();
              JSONParser parser = new JSONParser();
              Object obj = parser
                            .parse(new FileReader("C:\\Users\\donga\\eclipse-workspace\\Find_String\\input2.json"));
              JSONObject jsonObject = (JSONObject) obj;
              JSONArray companyList = (JSONArray) jsonObject.get("inputPair");

           
              Iterator<JSONObject> iterator = companyList.iterator();
              while (iterator.hasNext()) {

                     JSONObject o = iterator.next();

                     String key = (String) o.get("str");
                     long value = (long) o.get("num");
                     hm.put("\"" + key + "\"", value);

              }

              long end = System.currentTimeMillis();
              System.out.println("Time - " + (end - start) + " pre processing");

              
              // processing

              long start1 = System.currentTimeMillis();

              BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Result.csv")));

              for (int i = 0; i < arr.size(); i++) {
                     if (hm.containsKey(arr.get(i))) {
                            arr.set(i, arr.get(i) + ", - ," + hm.get(arr.get(i)));

                     }
              }
              long end1 = System.currentTimeMillis();
              System.out.println("Time - " + (end1 - start1) + " ms   O(1* N)");

            
              // post processing
              
              long start2 = System.currentTimeMillis();

              for (int i = 0; i < arr.size(); i++) {

                     writer.write(arr.get(i));
                     writer.newLine();
              }

              long end2 = System.currentTimeMillis();
              System.out.println("Time - " + (end2 - start2) + " post processing");

       }
}
