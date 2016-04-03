package test.patienttracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FileChooser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_chooser);

        String[] files = getFilesDir().list();

        Log.d("Benji", files[0]);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files);

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> view) {
                Log.d("Benji", "Nothing selected");
            }

            @Override
            public void onItemSelected(AdapterView<?> aView, View view, int position, long id) {
                Log.d("Benji", Integer.toString(position) + " id " + Long.toString(id) + " selected item: " +  aView.getSelectedItem());
            }
        });
    }
}
