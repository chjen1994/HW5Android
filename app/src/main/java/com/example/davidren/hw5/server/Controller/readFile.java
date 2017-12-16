package com.example.davidren.hw5.server.Controller;

import java.io.*;
import java.util.*;
/**
 * Created by davidren on 12/16/17.
 */

public class readFile {
    private List<String> choosenWord;
    private int number;
    private static readFile instance;


    private readFile(){
        choosenWord = new ArrayList<>();
        storeWord();
    }

    public List<String> getWords() {
        return choosenWord;
    }


    public static readFile getInstance() {
        if(instance == null) {
            instance = new readFile();
        }
        return instance;
    }





    private void storeWord(){
        String read_file = "/Users/davidren/AndroidStudioProjects/HW5/app/src/main/java/com/example/davidren/hw5/server/Controller/word.txt";// open up the file

        try{
            FileReader file_reader = new FileReader (read_file);
            BufferedReader bufferedReader = new BufferedReader(file_reader);
            number = 0;
            String word;
            while((word = bufferedReader.readLine()) != null) {
                choosenWord.add(word);
            }
            //System.out.println(number);
            bufferedReader.close();
        } catch(FileNotFoundException ex){
            System.out.println(
                    "Unable to open file '");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }
}
