package paulygon.listexperimentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.PrintStream;

public class AddWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        //extract "extra" data from the intent that loaded this activity (returns null if called on startup activity
        Intent intent = getIntent();
        String text = intent.getStringExtra("initialText");

        ((TextView) findViewById(R.id.new_word)).setText(text);

    }

    public void addWordClick(View view) {
        //add given word/defn to dictionary

        String newWord = ((TextView) findViewById(R.id.new_word)).getText().toString();
        String newDefn = ((TextView) findViewById(R.id.new_defn)).getText().toString();


        PrintStream output = null;
        try {
            output = new PrintStream(openFileOutput("added_words.txt", MODE_PRIVATE | MODE_APPEND));
        }
        catch (Exception e)
        {
            //do nothing? create and open file?
        }
        output.println(newWord + "\t" + newDefn);
        output.close();

        //go back to activity that called this activity
        //("returning" new word/defn back)
        Intent goBack = new Intent();
        goBack.putExtra("newWord", newWord);
        goBack.putExtra("newDefn", newDefn);
        setResult(RESULT_OK, goBack);
        finish();

    }
}
