package paulygon.listexperimentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import stanford.androidlib.*;

public class DictionaryActivity extends AppCompatActivity {

    private static final String[] WORDS = {
            "test", "exam",
            "blank", "empty"
    };

    private Map<String, String> dictionary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        dictionary = new HashMap<>();

        for (int i = 0; i < WORDS.length; i += 2) {
            dictionary.put(WORDS[i], WORDS[i + 1]);

        }

        ListView list = (ListView) findViewById(R.id.word_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, //activity
                android.R.layout.simple_list_item_1, //layout,
                new ArrayList<String>(dictionary.keySet()) //WORDS //array
        );

        list.setAdapter(adapter);

///  list.setOnItemClickListener(this); ///Third method, must implement stanford library version
        list.setOnItemClickListener(new Mike()); ///Second Method

/// //// First Method... new technique is creating seperate class below
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String word = parent.getItemAtPosition(position).toString();
//                String defn = dictionary.get(word);
//
//                Toast.makeText(getApplicationContext(), defn, Toast.LENGTH_SHORT).show(); //toast(defn);
//
//            }
//        });

    }
        ///Second Method
      class Mike implements AdapterView.OnItemClickListener{
          @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String word = parent.getItemAtPosition(position).toString();
              String defn = dictionary.get(word);

               Toast.makeText(getApplicationContext(), defn, Toast.LENGTH_SHORT).show(); //toast(defn);

           }
        }

////   public void onItemClick(ListView parent, View view, int position, long id) {
////       String word = parent.getItemAtPosition(position).toString();
////        String defn = dictionary.get(word);
////
////        Toast.makeText(getApplicationContext(), defn, Toast.LENGTH_SHORT).show(); //toast(defn);
////
////    }
////    }

}
