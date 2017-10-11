package paulygon.listexperimentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private Map<String, String> dictionary;
    private List<String> words;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    Toast.makeText(getApplicationContext(), "You are right!", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Oops wrong  :(", Toast.LENGTH_SHORT).show();
                }

                chooseWords();
           }
        );
    }

}
