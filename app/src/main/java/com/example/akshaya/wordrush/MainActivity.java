package com.example.akshaya.wordrush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView wordList ;
    TextView scores;
    EditText word;
    Button submit;
    Button reset;
    Button start;
    String letters;
    Dictionary dictionary;
    String submittedWord;
    Thread t;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordList = (TextView) findViewById(R.id.wordList);
        word = (EditText) findViewById(R.id.word);
        submit = (Button) findViewById(R.id.submit);
        scores = (TextView) findViewById(R.id.score);
        reset = (Button) findViewById(R.id.reset);
        start = (Button) findViewById(R.id.start);
        score = 0;
        //dictionary = new Dictionary();
        letters = "";
        try {
            InputStream is=getAssets().open("words.txt");
            dictionary =new Dictionary(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startGame(View view){
        letters = "";
        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

    }
    /*@Deprecated
    public void destroy()
    {
        throw new NoSuchMethodError("Thread.destroy()");
    }*/
    private void updateTextView()
    {
        Random random= new Random();

        char c = (char) (random.nextInt(26)+'A');
        letters = letters+ c;
        wordList.setText(letters);
        if(letters.length()>20) {
            //Toast.makeText(MainActivity.this, "GAME OVER!!", Toast.LENGTH_LONG).show();
            wordList.setText("GAME OVER !! :( YOUR SCORE :"+score);
            scores.setText("SCORE: "+score);
            word.setText("");
            t.interrupt();
        }
        start.setEnabled(false);
    }
    public void resetWord(View view)
    {
        wordList.setText("");
        word.setText("");
        scores.setText("SCORE : 0");
        start.setEnabled(true);
        score = 0;
        t.interrupt();

    }

    public void submitWord(View view)
    {
        String s ;
        submittedWord = word.getText().toString();
        letters = wordList.getText().toString();
        ArrayList<Character> wordListArray =new ArrayList<>();
        ArrayList<Character> letterListArray = new ArrayList<>();
        ArrayList<Character> newList = new ArrayList<>();
        ArrayList<Character> oldList = new ArrayList<>();
        for(int i=0;i<submittedWord.length();i++)
            wordListArray.add(submittedWord.charAt(i));
        for(int j=0;j<letters.length();j++)
            letterListArray.add(Character.toLowerCase(letters.charAt(j)));
        Log.d(submittedWord,"submitted word");
           if(dictionary.isWord(submittedWord) == true)
           {
               //t.interrupt();
               if(letterListArray.containsAll(wordListArray)) {
                   score = score + 1;
                   wordList.setText("Good Word !!!");
                   for (char c : letterListArray) {
                       if (wordListArray.contains(c)) {
                           //newList.add(c);
                           oldList.add(c);

                       }else {
                           newList.add(c);
                       }
                       //letterListArray.removeAll(wordListArray);
                   }
               }
               else{
                   wordList.setText("LETTER NOT IN LIST!!!");
               }
               s = convertToString(newList);
               //s = convertToString(letterListArray);
               letters = s.toUpperCase();
               word.setText("");
           }
           else if(dictionary.isWord(submittedWord) == false)
           {
                wordList.setText("NOT A VALID WORD!!!");
               //wordList.setText("YOU LOSE!!!!");
                word.setText("");
           }
        scores.setText("SCORE :"+score);
        start.setEnabled(true);
    }
    String convertToString(ArrayList<Character> list)
    {
        StringBuilder string = new StringBuilder(list.size());
        for(Character c:list)
        {
            string.append(c);
        }
        return string.toString();
    }

}
