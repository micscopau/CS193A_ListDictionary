package paulygon.listexperimentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import stanford.androidlib.*;

public class DictionaryActivity extends AppCompatActivity {

    private static final String TAG = "DictionaryActivity";
    private Map<String, String> dictionary;
    private List<String> words;
    private MediaPlayer mp;
    private int points;
    private int highScore;

    private void readFileHelper(Scanner scan){
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] parts = line.split("\t");

            dictionary.put(parts[0], parts[1]);
            words.add(parts[0]);
        }
    }

    private void readFileData(){
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.simpledict));

        readFileHelper(scan);

        try {
            Scanner scan2 = new Scanner(openFileInput("added_words.txt"));
            readFileHelper(scan2);
        } catch (Exception e){
            // do nothing...
        }

    }


    //pick the word and some random definitions (one being correct)
    // show on screen.
    private void chooseWords(){
        Random randy = new Random();
        int randomIndex = randy.nextInt(words.size());

        String theWord = words.get(randomIndex);
        String theDefn = dictionary.get(theWord);

        List<String> defns = new ArrayList<>(dictionary.values());

        defns.remove(theDefn);

        Collections.shuffle(defns);
        defns = defns.subList(0,4);
        defns.add(theDefn);
        Collections.shuffle(defns);

        //TextView wordText = (TextView) findViewById(R.id.the_word);
        //wordText.setText(theWord);
        ((TextView) findViewById(R.id.the_word)).setText(theWord);

        ListView list = (ListView) findViewById(R.id.word_list);

        //Dynamic List in Android requires an adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, //activity
                android.R.layout.simple_list_item_1, //layout,
                new ArrayList<String>(defns) //WORDS //array
        );
        list.setAdapter(adapter);

    }

    public void addWordClick(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");

        points = 0;

        setContentView(R.layout.activity_dictionary);

        dictionary = new HashMap<>();
        words = new ArrayList<>();

        readFileData();

        chooseWords();


        ListView list = (ListView) findViewById(R.id.word_list);

        //Lambda function used to determine specific item in a list that is clicked
        list.setOnItemClickListener( (parent, view, position, id) -> {

                String defnClicked = parent.getItemAtPosition(position).toString();
                String theWord = ((TextView) findViewById(R.id.the_word)).getText().toString();
                String correctDefn = dictionary.get(theWord);

                if (defnClicked.equals(correctDefn)){
                    points++;
                    if (points > highScore){
                        highScore = points;

                        SharedPreferences prefs = getSharedPreferences("myprefs", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor = prefs.edit();
                        prefsEditor.putInt("highScore", highScore);
                        prefsEditor.apply(); //.commit() might also work

                    }
                    Toast.makeText(getApplicationContext(), "You are right! Score = " + points + ", highScore = " + highScore, Toast.LENGTH_SHORT).show();
                } else{
                    points--;
                    Toast.makeText(getApplicationContext(), "Oops wrong  :(  Score: " + points + ", highScore = " + highScore, Toast.LENGTH_SHORT).show();

                }

                chooseWords();
           }
        );

        //load high score
        SharedPreferences prefs = getSharedPreferences("myprefs", MODE_PRIVATE);
        highScore = prefs.getInt("highScore", /*default*/ 0);

        mp = MediaPlayer.create(this, R.raw.cartoon_computer);
        mp.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");

        mp.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");

        mp.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called");

        outState.putInt("points", points);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState() called");
        points = savedInstanceState.getInt("points", /*Default */ 0 );

    }

}
