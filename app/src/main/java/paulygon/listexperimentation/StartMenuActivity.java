package paulygon.listexperimentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartMenuActivity extends AppCompatActivity {
    private static final int REQ_CODE_ADD_WORD = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }

    public void playGameClick(View view) {
        // go to DictionaryActivity

        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);

    }

    public void addWordClick(View view) {
        // go to Add Word activity
        Intent intent = new Intent(this, AddWordActivity.class);
        intent.putExtra("initialText", "FooBar"); //example features

        //startActivity(intent); //if this activity doesn't need to wait for a result
        startActivityForResult(intent, REQ_CODE_ADD_WORD);

    }

    //this gets called when a called activity is "finished()" and returns to this activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_ADD_WORD) {
            //intent
            //-> "newWord", "newDefn"

            String newWord = data.getStringExtra("newWord");
            String newDefn = data.getStringExtra("newDefn");

            String toastMsg = " You added the word: " + newWord;
            Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
