package com.solutions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;

public class LinearSearch {

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

                     for (int i = 0; i < arr.size(); i++) {

                            if (arr.get(i).equals("\"" + key + "\"")) {

                                   arr.set(i, arr.get(i) + ", -  ," + value);

                            }
                     }
              }

              long end1 = System.currentTimeMillis();
              System.out.println("Time - " + (end1 - start1) + " ms   O(m*N)");

              // postprocessing

              long start2 = System.currentTimeMillis();

              BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Result.csv")));

              for (int i = 0; i < arr.size(); i++) {

                     String str = arr.get(i);
                     writer.write(str);
                     writer.newLine();
              }

              long end2 = System.currentTimeMillis();
              System.out.println("Time - " + (end2 - start2) + " post processing");
       }
}
