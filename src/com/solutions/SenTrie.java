package com.solutions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SenTrie {

       static class TrieNode {
              TrieNode[] childrens;
              boolean isWord;
              int id;
              int value = -1;

              TrieNode() {
                     this.childrens = new TrieNode[27];
                     isWord = false;
              }
       }

       static TrieNode root;

       SenTrie() {
              this.root = new TrieNode();
       }

       void insert(String str, int id) { // "abc"
              TrieNode temp = root;
              for (int i = 0; i < str.length(); i++) {
                     int inx;
                     if (str.charAt(i) == ' ') {
                            inx = 26;
                     } else {
                            inx = str.charAt(i) - 'a';
                     }

                     // System.out.println(inx);
                     if (temp.childrens[inx] == null) {
                            temp.childrens[inx] = new TrieNode();
                            temp = temp.childrens[inx];
                     } else {
                            temp = temp.childrens[inx];
                     }
                     if (i == str.length() - 1) {
                            temp.isWord = true;
                            temp.id = id;
                     }
              }
       }

       boolean searchSubString(String str) {
              TrieNode temp = root;
              int i;
              for (i = 0; i < str.length(); i++) {
                     int inx;
                     if (str.charAt(i) == ' ') {
                            inx = 26;
                     } else {
                            inx = str.charAt(i) - 'a';
                     }
                     if (temp.childrens[inx] == null) {
                            return false;
                     } else {
                            temp = temp.childrens[inx];
                            char c = (char) (inx + 97);
                         
                     }
              }
              return true;
       }

       boolean search(String str) {
              TrieNode temp = root;
              int i;
              for (i = 0; i < str.length(); i++) {
                     int inx;
                     if (str.charAt(i) == ' ') {
                            inx = 26;
                     } else {
                            inx = str.charAt(i) - 'a';
                     }

                     if (temp.childrens[inx] == null) {
                            return false;
                     } else {
                            temp = temp.childrens[inx];
                            char c = (char) (inx + 97);
                      
                     }
                     if (i == str.length() - 1 && temp.isWord == false) {
                            return false;
                     }
                     System.out.println(temp.value);
              }
              return true;
       }

       boolean update(String str, int value) {
              TrieNode temp = root;
              int i;
              for (i = 0; i < str.length(); i++) {
                     int inx;
                     if (str.charAt(i) == ' ') {
                            inx = 26;
                     } else {
                            inx = str.charAt(i) - 'a';
                     }

                     if (temp.childrens[inx] == null) {
                            return false;
                     } else {
                            temp = temp.childrens[inx];
                            char c = (char) (inx + 97);
                      
                     }
                     if (i == str.length() - 1 && temp.isWord == false) {
                            return false;
                     }
                     if (i == str.length() - 1)
                            temp.value = value;
              }
              return true;
       }

       static TreeMap<Integer, ArrayList<String>> hm = new TreeMap<>();

       void printAllStrings(TrieNode myRoot, String str) {

              int i;
              if (myRoot.isWord == true) {
  
                     ArrayList<String> arr = new ArrayList<String>();
                     arr.add(str);
                     if (myRoot.value == -1) {
                            arr.add("null");
                     } else {
                            arr.add(Integer.toString(myRoot.value));
                     }

                     hm.put(myRoot.id, arr);
              }
              for (i = 0; i < 27; i++) {
                     if (myRoot.childrens[i] != null) {
                            char ch;
                            if (i == 26) {
                                   ch = ' ';
                            } else {
                                   ch = (char) (i + 97);
                            }
                            printAllStrings(myRoot.childrens[i], str + ch);
                     }
              }
       }

       void printChildStrings(TrieNode myRoot, String str, String inp) {

              int i;
              if (str.compareTo(inp) == 0) {
                     printAllStrings(myRoot, str);
                     return;
              }
              for (i = 0; i < 27; i++) {
                     if (myRoot.childrens[i] != null) {
                            char ch;
                            if (i == 26) {
                                   ch = ' ';
                            } else {
                                   ch = (char) (i + 97);
                            }
                            printChildStrings(myRoot.childrens[i], str + ch, inp);
                     }
              }
       }

       public static void main(String[] args) throws IOException, ParseException {

              long start = System.currentTimeMillis();

              // prework
              SenTrie trr = new SenTrie();
             
              File file = new File("C:\\Users\\donga\\eclipse-workspace\\Find_String\\dataSet2.csv");

              BufferedReader reader = new BufferedReader(new FileReader(file));
              String st = "";
              int i = 0;
              while ((st = reader.readLine()) != null) {
                     st = "\"" + st.split(",")[2].trim() + "\"";
                     st = st.toLowerCase();
                     st = st.replaceAll("[^a-z ]+", "");
              
                     trr.insert(st, i);
                     i++;

              }

              long end = System.currentTimeMillis();
              System.out.println("Time - " + (end - start) + " ms   pre processing ");

              long start1 = System.currentTimeMillis();

              // mainwork


              JSONParser parser = new JSONParser();
              Object obj = parser
                            .parse(new FileReader("C:\\Users\\donga\\eclipse-workspace\\Find_String\\input2.json"));
              JSONObject jsonObject = (JSONObject) obj;
              JSONArray companyList = (JSONArray) jsonObject.get("inputPair");

              Iterator<JSONObject> iterator = companyList.iterator();
              while (iterator.hasNext()) {

                     JSONObject o = iterator.next();

                     String key = (String) o.get("str");
                     key = key.toLowerCase();
                     key = key.replaceAll("[^a-z ]+", "");
                     long value = (long) o.get("num");
                     trr.update(key, (int) value);
              }

              long end1 = System.currentTimeMillis();
              System.out.println("Time - " + (end1 - start1) + " ms   O(m* logN) ");

              // postwork

              long start2 = System.currentTimeMillis();

              BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Result.csv")));
              trr.printAllStrings(root, "");
              for (Map.Entry<Integer, ArrayList<String>> e : SenTrie.hm.entrySet()) {

                     if (e.getValue().get(1) != "null") {
                          
                            writer.write(e.getValue().get(0) + ", - ," + e.getValue().get(1));
                     } else {
                    
                            writer.write(e.getValue().get(0));

                     }

                     writer.newLine();
              }

              long end2 = System.currentTimeMillis();
              System.out.println("Time - " + (end2 - start2) + " ms   post processing ");
       }
}
