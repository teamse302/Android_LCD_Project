package com.example.mertdesktop.schoolproject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button send;
    String writingString;
    StringBuilder stringBuilder;
    AutoCompleteTextView sendText;
    Intent intent;
    VariableClass varObj = new VariableClass();
    ListView listFavorites;
    ArrayAdapter adapter;
    int positionOfList;
    ArrayAdapter adapterFavor;
    Service myService;
    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.letter55);
        ab.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        ab.setDisplayUseLogoEnabled(true);
        varObj.resetAddapterArray();
        varObj.resetFavorList();
        sendText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        send = (Button) findViewById(R.id.button3);
        addItemText();
        sendClickListener();
        setUpList();
        intent = new Intent(this,Service.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    public void setUpList() {
        listFavorites = (ListView) findViewById(R.id.listView);
        read("mylist.txt");
        adapterFavor = new ArrayAdapter(this, android.R.layout.simple_list_item_1, varObj.addapterListFavor);
        listFavorites.setAdapter(adapterFavor);
        listFavorites.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, varObj.addapterListFavor) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                return textView;
            }
        });



        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!varObj.addapterListFavor[position].equals("<Long press to add a new message>") && !varObj.addapterListFavor[position].isEmpty())
                sendText.setText(varObj.addapterListFavor[position]);
            }
        });
        listFavorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                positionOfList = position;
                read("mytext.txt");
                for(int i = 0 ; i< varObj.addapterArray.length;i++) {
                    if(varObj.addapterArray[i].isEmpty()) {
                        if (i > 0) {
                            if(!Arrays.asList(varObj.addapterListFavor).contains(varObj.addapterArray[i - 1])) {
                                if(!varObj.addapterArray[i - 1].isEmpty()) {
                                    varObj.addapterListFavor[positionOfList] = varObj.addapterArray[i - 1];
                                    writeForFavor();
                                }
                            }
                            else
                                Toast.makeText(MainActivity.this, "The Message Already Exists", Toast.LENGTH_SHORT).show();
                            refreshList();
                            break;
                        }
                    }

                }
                return true;
            }
        });
    }
    public void writeForFavor() {
        stringBuilder = new StringBuilder();
        for (int i = 0; i < varObj.addapterListFavor.length; i++) {
            stringBuilder.append(varObj.addapterListFavor[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("mylist.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readSettings() {
        try {
            FileInputStream fileInputStream = openFileInput("Setting.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                VariableClass.settingArray[i] = lines;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void addItemText() {

            sendText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
            read("mytext.txt");
            adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, varObj.addapterArray);
            sendText.setThreshold(2);
            sendText.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        readSettings();
        if(VariableClass.settingArray[0].equals("checkBox is not checked")) {
            removePast("mytext.txt");
            addItemText();
        }
    }


    public void sendClickListener() {
        sendText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(VariableClass.settingArray[0].equals("checkBox is checked")) {
                    write(sendText.getText().toString(), "mytext.txt");
                }
                addItemText();
                /*

        Gönderme baglantı işlemi


             */
                sendText.setText("");
            }
        });
    }

    public void refreshList() {
        read("mylist.txt");
        adapterFavor = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, varObj.addapterListFavor){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                return textView;
            }
        };
        listFavorites.setAdapter(adapterFavor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void write(String str, String text) {
        sendText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        stringBuilder = new StringBuilder();
        read(text);
        if (text == "mytext.txt") {
            boolean flag = false;
            for (int i = 0; i < varObj.addapterArray.length; i++) {
                if (Arrays.asList(varObj.addapterArray).contains(str)) {
                    flag = true;
                }
            }
            if (flag == false) {
                for (int i = 0; i < varObj.addapterArray.length; i++) {
                    if (!varObj.addapterArray[i].isEmpty()) {
                        stringBuilder.append(varObj.addapterArray[i]);
                        stringBuilder.append("\n");
                    }
                }
                stringBuilder.append(str);
                writingString = stringBuilder.toString();
                try {
                    FileOutputStream fileOutputStream = openFileOutput(text, MODE_PRIVATE);
                    fileOutputStream.write(writingString.getBytes());
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (text == "mylist.txt") {
            boolean flag = false;
            for (int i = 0; i < varObj.addapterListFavor.length; i++) {
                if (Arrays.asList(varObj.addapterListFavor).contains(str)) {
                    flag = true;
                }
            }
            if (flag == false) {
                for (int i = 0; i < varObj.addapterListFavor.length; i++) {
                    if (!varObj.addapterListFavor[i].isEmpty()) {
                        stringBuilder.append(varObj.addapterListFavor[i]);
                        stringBuilder.append("\n");
                    }
                }
                stringBuilder.append(str);
                writingString = stringBuilder.toString();
                try {
                    FileOutputStream fileOutputStream = openFileOutput(text, MODE_PRIVATE);
                    fileOutputStream.write(writingString.getBytes());
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void removePast(String text) {

        stringBuilder = new StringBuilder();
        if (text == "mytext.txt") {
            for (int i = 0; i < varObj.addapterArray.length; i++) {
                stringBuilder.append("");
                stringBuilder.append("\n");
            }
            String writingString1 = stringBuilder.toString();

            try {
                FileOutputStream fileOutputStream = openFileOutput(text, MODE_PRIVATE);
                fileOutputStream.write(writingString1.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (text == "mylist.txt") {
            for (int i = 0; i < varObj.addapterListFavor.length; i++) {
                stringBuilder.append("<Long press to add a new message>");
                stringBuilder.append("\n");
            }
            String writingString1 = stringBuilder.toString();

            try {
                FileOutputStream fileOutputStream = openFileOutput(text, MODE_PRIVATE);
                fileOutputStream.write(writingString1.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void read(String text) {
        if (text == "mylist.txt") {
            try {
                FileInputStream fileInputStream = openFileInput(text);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String lines;
                int i = 0;
                while ((lines = bufferedReader.readLine()) != null) {
                    varObj.addapterListFavor[i] = lines;
                    i++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (text == "mytext.txt") {
            try {
                FileInputStream fileInputStream = openFileInput(text);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String lines;
                int i = 0;
                while ((lines = bufferedReader.readLine()) != null) {
                    varObj.addapterArray[i] = lines;
                    i++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent("com.example.mertdesktop.schoolproject.Main2Activity");
                startActivity(intent);
                break;
            case R.id.action_remove:
                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                a_builder.setMessage("Do you want to clean your history")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removePast("mytext.txt");
                                removePast("mylist.txt");
                                refreshList();
                                addItemText();
                                sendText.setText("");
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Warning!");
                alert.show();
                break;
            case R.id.action_program:
                intent = new Intent("com.example.mertdesktop.schoolproject.Program");
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }



    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Service.LocalBinder binder = (Service.LocalBinder) service;
            myService = ((Service.LocalBinder) service).getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
}
