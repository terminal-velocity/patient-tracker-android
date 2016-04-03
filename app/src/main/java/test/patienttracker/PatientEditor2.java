package test.patienttracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PatientEditor2 extends AppCompatActivity {

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        final String json = "{ \"Q1\": {  \"type\": \"textbox\",   \"question\": \"My first question\",  \"data\": \"Hello World\" }, \"Q2\": {  \"type\": \"dropdown\",   \"question\": \"My second question\",  \"data\": {    \"0\": \"Answer Zero\",    \"1\": \"Answer One\",    \"2\": \"Answer Two\"  },  \"value\": 1 }  }";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_editor2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonReader jO = new JsonReader(new StringReader(json));


                try {
                    jO.beginObject();
                    while (jO.hasNext()) {
                        readMessage(jO, view);
                    }
                    jO.endObject();
                } catch (IOException ignored) {
                    Snackbar.make(view, ignored.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
        */

    String[] files;
    HashMap<EditText, CustomInputField> textData = new HashMap<>();
    HashMap<Spinner, CustomInputField> spinData = new HashMap<>();
    HashMap<CheckBox, CustomInputField> checkData = new HashMap<>();
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<CustomInputField> cifsInOrder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        //final String json = "{ \"Q1\": {  \"type\": \"textbox\",   \"question\": \"My first question\",  \"data\": \"Hello World\" }, \"Q2\": {  \"type\": \"dropdown\",   \"question\": \"My second question\",  \"data\": {    \"0\": \"Answer Zero\",    \"1\": \"Answer One\",    \"2\": \"Answer Two\"  },  \"value\": 1 }  }";
        Log.d("Benji", "Startup");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_editor2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        files = getFilesDir().list();
        final Context this2 = this;
        /*addFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/
        try {
            File f = new File("data1.json");
            f.getCanonicalFile().delete();
            f = new File("dat.json");
            f.getCanonicalFile().delete();
        }
        catch (IOException e) {
            Log.d("Benji", "IOError in renamer: " + e.getMessage());
        }

        makeMenu("data1");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Benji", "Short");
                for (HashMap.Entry<EditText, CustomInputField> etCif : textData.entrySet()) {
                    etCif.getValue().data = etCif.getKey().getText().toString();
                }
                for (HashMap.Entry<Spinner, CustomInputField> spCif : spinData.entrySet()) {
                    spCif.getValue().choice = spCif.getKey().getSelectedItemPosition();
                }
                for (HashMap.Entry<CheckBox, CustomInputField> cbCif : checkData.entrySet()) {
                    cbCif.getValue().checked = cbCif.getKey().isChecked();
                }

                Gson gson = new Gson();
                String json = gson.toJson(cifsInOrder);
                Log.d("benji", "Saving: " + json);

                String path = "data1";
                try {
                    FileOutputStream fos = openFileOutput(path, Context.MODE_PRIVATE);
                    fos.write(json.getBytes());
                    fos.close();
                }
                catch (IOException ignored) {}
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("Benji", "Long");
                final String json = "[{ \"type\": \"textbox\", \"question\": \"My first question\", \"data\": \"Hello World\", \"options\"=[], \"choice\"=0, \"checked\"=true}, { \"type\": \"dropdown\", \"question\": \"My second question\", \"data\": \"\", \"options\"=[\"One\", \"Two\", \"Three\"], \"choice\"=1, \"checked\"=true}, { \"type\": \"checkbox\", \"question\": \"My third question\", \"data\": \"Hey\", \"options\"=[\"One\", \"Two\", \"Three\"], \"choice\"=1, \"checked\"=true}]\n";
                String path = "data1";
                try {
                    FileOutputStream fos = openFileOutput(path, Context.MODE_PRIVATE);
                    fos.write(json.getBytes());
                    fos.close();
                    makeMenu("data1");
                }
                catch (IOException ignored) { return false; }
                return true;
            }
        });

        //EditText et = (EditText)findViewById(R.id.eTx);
        //et.setText("LOL");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    /*
    protected void readMessage(JsonReader reader, View view) throws IOException {
        Log.d("Benji", "Hey there");
        Snackbar.make(view, "Start", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        reader.beginObject();
        String name = reader.nextName();
        if (name.equals("type")) {
            String type = reader.nextString();
            if (type.equals("textbox")) {
                // Make a text box with text in the data field
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        reader.endObject();
/*
        while (reader.hasNext()) {
            name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextLong();
            } else if (name.equals("text")) {
                text = reader.nextString();
            } else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
                geo = readDoublesArray(reader);
            } else if (name.equals("user")) {
                user = readUser(reader);
            } else {
                reader.skipValue();
            }
        }*/
    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_editor2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.ChoosePatient) {
            Log.d("Benji", "Launching dialog");
            //startActivity(new Intent(this2, FileChooser.class));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose a patient")
                    .setItems(files, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            makeMenu(files[which]);
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if (id == R.id.AddPatient) {
            Log.d("Benji", "Launching dialog");

            final EditText input = new EditText(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setTitle("Choose a new file name").setView(input).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String path = input.getText().toString();
                            if (path.matches("[a-zA-Z][a-zA-Z0-9]*")) {
                                try {
                                    copyFile("data1", path);
                                    makeMenu(path);
                                    files = getFilesDir().list();
                                } catch (IOException e) {
                                    Log.d("Benji", "IOError: " + e.getMessage());
                                }
                            }
                            else {
                                Snackbar.make(fab, "Bad file name", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void copyFile(String src, String dst) throws IOException {
        InputStream in = openFileInput(src);
        OutputStream out = openFileOutput(dst, Context.MODE_PRIVATE);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }


    protected void makeMenu(String path) {
        Log.d("Benji", "Refreshing");
        String json;
        StringBuilder jsonBuilder = new StringBuilder();
        try{
            FileInputStream fis = openFileInput(path);
            char current;
            while (fis.available() > 0) {
                current = (char) fis.read();
                jsonBuilder.append(String.valueOf(current));

            }
            json = jsonBuilder.toString();
        }
        catch (IOException e) {
            Log.d("Benji", "IO Exception in makeMenu: " + e.getMessage());
            json = "[{ \"type\": \"textbox\", \"question\": \"My first question\", \"data\": \"Hello World\", \"options\"=[], \"choice\"=0, \"checked\"=true}, { \"type\": \"dropdown\", \"question\": \"My second question\", \"data\": \"\", \"options\"=[\"One\", \"Two\", \"Three\"], \"choice\"=1, \"checked\"=true}, { \"type\": \"checkbox\", \"question\": \"My third question\", \"data\": \"Hey\", \"options\"=[\"One\", \"Two\", \"Three\"], \"choice\"=1, \"checked\"=true}]";
        }

        Gson gson =  new Gson();
        CustomInputField[] cifArray = gson.fromJson(json, CustomInputField[].class);

        for (EditText et : textData.keySet()) {
            et.setVisibility(View.GONE);
        }
        for (Spinner sp : spinData.keySet()) {
            sp.setVisibility(View.GONE);
        }
        for (CheckBox cb : checkData.keySet()) {
            cb.setVisibility(View.GONE);
        }
        for (TextView tv : textViews) {
            tv.setVisibility(View.GONE);
        }

        textData.clear();
        spinData.clear();
        checkData.clear();
        cifsInOrder.clear();
        for (CustomInputField cif : cifArray) {
            cifsInOrder.add(cif);
            if (cif.type.equals("textbox")) {
                TextView textView = new TextView(this);
                textView.setText(cif.question);
                textViews.add(textView);

                EditText et = new EditText(this);
                et.setText(cif.data);

                textData.put(et, cif);


                LinearLayout ll = (LinearLayout) findViewById(R.id.pe3);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(textView);
                ll.addView(et);
            }
            else if (cif.type.equals("dropdown")) {
                TextView textView = new TextView(this);
                textView.setText(cif.question);
                textViews.add(textView);

                Spinner sp = new Spinner(this);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, R.layout.support_simple_spinner_dropdown_item, cif.options);
                sp.setAdapter(adapter);
                sp.setSelection(cif.choice);
                spinData.put(sp, cif);

                LinearLayout ll = (LinearLayout) findViewById(R.id.pe3);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(textView);
                ll.addView(sp);
            }
            else if (cif.type.equals("checkbox")) {
                TextView textView = new TextView(this);
                textView.setText(cif.question);
                textViews.add(textView);

                CheckBox cb = new CheckBox(this);
                cb.setText(cif.data);
                cb.setChecked(cif.checked);
                checkData.put(cb, cif);

                LinearLayout ll = (LinearLayout) findViewById(R.id.pe3);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(textView);
                ll.addView(cb);
            }
            else {
                Log.d("Benji", "Bad JSON");
            }
                        /*
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            EditText et = new EditText(this);
            ((LinearLayout) findViewById(R.id.container)).addView(et);*/
        }
        // Squash of the random extra input fields
        EditText et = (EditText)findViewById(R.id.eTx);
        et.setVisibility(View.GONE);
        Spinner sp = (Spinner)findViewById(R.id.spinner1);
        sp.setVisibility(View.GONE);
        CheckBox cb = (CheckBox)findViewById(R.id.cB);
        cb.setVisibility(View.GONE);
        //Log.d("Benji", ctb[0].question + ctb[0].data + ctb[0].type + ctb[0].choice);
    }
}
