package com.example.mertdesktop.schoolproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main2Activity extends AppCompatActivity {
    CheckBox rememberPast,stopSch,stopDel;
    TextView textViewPast,textViewSchedule,textViewLocation;
    StringBuilder stringBuilder;
    String writingString;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.letter55);
        ab.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        ab.setDisplayUseLogoEnabled(true);
        onClicklistener();
        checkBoxPrep();
        prepTextViews();

    }
    public void prepTextViews(){
        textViewPast=(TextView)findViewById(R.id.textView);
        textViewPast.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textViewSchedule=(TextView)findViewById(R.id.textView2);
        textViewSchedule.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textViewLocation=(TextView)findViewById(R.id.textView3);
        textViewLocation.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
    public void checkedRememberPast(){
        if(rememberPast.isChecked()) {
            VariableClass.settingArray[0] = "checkBox is checked";
        }
        else {
            VariableClass.settingArray[0] = "checkBox is not checked";
        }
        if(stopSch.isChecked()) {
            VariableClass.settingArray[1] = "checkBox is checked";
        }
        else {
            VariableClass.settingArray[1] = "checkBox is not checked";
            intent = new Intent(getApplicationContext(),NotificationClass.class);
            startService(intent);
            NotificationClass.startFlag=true;
        }
        if(stopDel.isChecked()) {
            VariableClass.settingArray[2] = "checkBox is checked";
        }
        else {
            VariableClass.settingArray[2] = "checkBox is not checked";
        }
        writeForSettings();
    }
    public void checkBoxPrep(){
        readSettings();
        if (VariableClass.settingArray[0].equals("checkBox is checked"))
                rememberPast.setChecked(true);
        if (VariableClass.settingArray[0].equals("checkBox is not checked"))
                rememberPast.setChecked(false);
        if (VariableClass.settingArray[1].equals("checkBox is checked"))
            stopSch.setChecked(true);
        if (VariableClass.settingArray[1].equals("checkBox is not checked"))
            stopSch.setChecked(false);
        if (VariableClass.settingArray[2].equals("checkBox is checked"))
            stopDel.setChecked(true);
        if (VariableClass.settingArray[2].equals("checkBox is not checked"))
            stopDel.setChecked(false);
    }
    public void onClicklistener() {
        rememberPast = (CheckBox) findViewById(R.id.checkBox);
        stopSch=(CheckBox)findViewById(R.id.checkBox2);
        stopDel=(CheckBox)findViewById(R.id.checkBox3);
        int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
        stopDel.setButtonDrawable(id);
        stopSch.setButtonDrawable(id);
        rememberPast.setButtonDrawable(id);
        stopSch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedRememberPast();
            }
        });
        rememberPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedRememberPast();
            }
        });
        stopDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedRememberPast();
            }
        });
    }
    public void writeForSettings() {
        stringBuilder = new StringBuilder();
        for (int i = 0; i < VariableClass.settingArray.length; i++) {
            if (!VariableClass.settingArray[i].isEmpty()) {
                stringBuilder.append(VariableClass.settingArray[i]);
                stringBuilder.append("\n");
            }
        }
        writingString = stringBuilder.toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("Setting.txt", MODE_PRIVATE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
