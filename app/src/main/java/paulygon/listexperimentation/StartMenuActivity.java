package paulygon.listexperimentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class StartMenuActivity extends AppCompatActivity {

    private static final String TAG = "StartMenuActivity";
    private static final int REQ_CODE_ADD_WORD = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
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

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
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
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState() called");
    }
}
