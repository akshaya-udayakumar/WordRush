package com.example.akshaya.wordrush;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Created by Akshaya on 23-10-2016.
 */

public class Dictionary {
    private ArrayList<String> wordlist ;
    public Dictionary(InputStream words)throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(words));
        wordlist = new ArrayList<>();
        String line = null;
        while ((line = in.readLine()) != null) {
            wordlist.add(line);
        }
    }

    public boolean isWord(String string)
    {
          return wordlist.contains(string);
    }
}
