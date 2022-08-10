package com.solutions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LinkedHashmap {

       public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

              long start = System.currentTimeMillis();

              // prework
              LinkedHashMap<String, Long> Lhm = new LinkedHashMap();
              File file = new File("C:\\Users\\donga\\eclipse-workspace\\Find_String\\dataSet2.csv");

              BufferedReader reader = new BufferedReader(new FileReader(file));
              String st = "";
              while ((st = reader.readLine()) != null) {

                     Lhm.put("\"" + st.split(",")[2].trim() + "\"", null);
              }

              long end = System.currentTimeMillis();
              System.out.println("Time - " + (end - start) + " pre processing");

              // mainwork

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
                     if (Lhm.containsKey("\"" + key + "\"")) {

                            Lhm.put("\"" + key + "\"", value);
                     }
              }

              long end1 = System.currentTimeMillis();
              System.out.println("Time - " + (end1 - start1) + " ms   O(m*1)");

              // postprocessing

              long start2 = System.currentTimeMillis();

              BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Result.csv")));

              for (Map.Entry<String, Long> e : Lhm.entrySet()) {

                     if (e.getValue() != null) {
                            writer.write(e.getKey() + ", - ," + e.getValue());
                     } else {
                            writer.write(e.getKey());
                     }

                     writer.newLine();
              }

              long end2 = System.currentTimeMillis();
              System.out.println("Time - " + (end2 - start2) + " post processing");

       }
}
