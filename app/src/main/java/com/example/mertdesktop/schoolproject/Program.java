package com.example.mertdesktop.schoolproject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.List;

public class Program extends AppCompatActivity {
    TabHost tabHost;
    String writingString;
    StringBuilder stringBuilder;
    AlertDialog.Builder a_builder;
    TabHost.TabSpec tabMonday,tabSunday,tabTuesday , tabWednesday , tabFriday,tabThursday,tabSaturday;
    ListView listMonday,listSunday,listTuesday , listWednesday , listFriday,listThursday,listSaturday;
    VariableClass varObj = new VariableClass();
    Intent intent;
    public Button tabMondayUpdate,tabMondayDelete,tabTuesdayUpdate,tabTuesdayDelete,tabWednesdayUpdate
            ,tabWednesdayDelete,tabThursdayUpdate,tabThursdayDelete,tabFridayUpdate,tabFridayDelete
            ,tabSaturdayUpdate,tabSaturdayDelete,tabSundayUpdate,tabSundayDelete;
    ArrayAdapter adapterMonday,adapterTuesday,adapterWednesday,adapterThursday,adapterFriday,adapterSaturday,adapterSunday;
    public EditText tabMondayText,tabTuesdayText,tabWednesdayText,tabThursdayText,tabFridayText,tabSaturdayText,
    tabSundayText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.letter55);
        ab.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        ab.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        ab.setDisplayUseLogoEnabled(true);
        setupTabHost();
        setupListViews();
        ButtonListeners();
        varObj.tagsInitialize();
        setupTextViews();
    }
    public void setupTextViews(){
        tabMondayText = (EditText)findViewById(R.id.tab_monday_edittext);
        tabTuesdayText=(EditText)findViewById(R.id.tab_tuesday_edittext);
        tabWednesdayText=(EditText)findViewById(R.id.tab_wednesday_edittext);
        tabThursdayText=(EditText)findViewById(R.id.tab_thursday_edittext);
        tabFridayText=(EditText)findViewById(R.id.tab_friday_edittext);
        tabSaturdayText=(EditText)findViewById(R.id.tab_saturday_edittext);
        tabSundayText=(EditText)findViewById(R.id.tab_sunday_edittext);
    }

    private void setupListViews(){
        prepareListView();
        listMonday = (ListView) findViewById(R.id.tab_monday_listview);
        listMonday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabMondayText.length; i++) {
                    if (i == position) {
                        varObj.adapterListViewMonday[i] = varObj.adapterListViewBackup[i];
                        varObj.tabMondayText[i] = "";
                        writeStrings();
                    }
                }
                adapterMonday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewMonday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listMonday.setAdapter(adapterMonday);
                return true;
            }
        });
        listMonday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabMondayTags.length; i++) {
                    if (i == position) {
                        varObj.tabMondayTags[i] = true;
                    }
                }
            }
        });
        adapterMonday = new ArrayAdapter(this,android.R.layout.simple_list_item_1,varObj.adapterListViewMonday){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                return textView;
            }
        };
        listMonday.setAdapter(adapterMonday);

        listTuesday = (ListView) findViewById(R.id.tab_tuesday_listview);
        listTuesday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabTuesdayText.length; i++) {
                    if (i == position) {
                        varObj.adapterListViewTuesday[i] = varObj.adapterListViewBackup[i];
                        varObj.tabTuesdayText[i]="";
                        writeStrings();
                    }
                }
                adapterTuesday = new ArrayAdapter(Program.this,android.R.layout.simple_list_item_1,varObj.adapterListViewTuesday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listTuesday.setAdapter(adapterTuesday);
                return true;
            }
        });
        listTuesday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabTuesdayTags.length; i++) {
                    if (i == position)
                        varObj.tabTuesdayTags[i] = true;
                }
            }
        });
        adapterTuesday = new ArrayAdapter(this,android.R.layout.simple_list_item_1,varObj.adapterListViewTuesday){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                return textView;
            }
        };
        listTuesday.setAdapter(adapterTuesday);

        listWednesday = (ListView) findViewById(R.id.tab_wednesday_listview);
        listWednesday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabWednesdayText.length; i++) {
                    if (i == position) {
                        varObj.adapterListViewWednesday[i] = varObj.adapterListViewBackup[i];
                        varObj.tabWednesdayText[i]="";
                        writeStrings();
                    }
                }
                adapterWednesday = new ArrayAdapter(Program.this,android.R.layout.simple_list_item_1,varObj.adapterListViewWednesday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listWednesday.setAdapter(adapterWednesday);
                return true;
            }
        });
        listWednesday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabWednesdayTags.length; i++) {
                    if (i == position)
                        varObj.tabWednesdayTags[i] = true;
                }
            }
        });
        adapterWednesday = new ArrayAdapter(this,android.R.layout.simple_list_item_1,varObj.adapterListViewWednesday){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                return textView;
            }
        };
        listWednesday.setAdapter(adapterWednesday);

        listThursday = (ListView) findViewById(R.id.tab_thursday_listview);
        listThursday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabThursdayText.length; i++) {
                    if (i == position) {
                        varObj.adapterListViewThursday[i] = varObj.adapterListViewBackup[i];
                        varObj.tabThursdayText[i]="";
                        writeStrings();
                    }
                }
                adapterThursday = new ArrayAdapter(Program.this,android.R.layout.simple_list_item_1,varObj.adapterListViewThursday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listThursday.setAdapter(adapterThursday);
                return true;
            }
        });
        listThursday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabThursdayTags.length; i++) {
                    if (i == position)
                        varObj.tabThursdayTags[i] = true;
                }
            }
        });
        adapterThursday = new ArrayAdapter(this,android.R.layout.simple_list_item_1,varObj.adapterListViewThursday){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                return textView;
            }
        };
        listThursday.setAdapter(adapterThursday);

        listFriday = (ListView) findViewById(R.id.tab_friday_listview);
        listFriday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabFridayText.length; i++) {
                    if (i == position) {
                        varObj.adapterListViewFriday[i] = varObj.adapterListViewBackup[i];
                        varObj.tabFridayText[i]="";
                        writeStrings();
                    }
                }
                adapterFriday = new ArrayAdapter(Program.this,android.R.layout.simple_list_item_1,varObj.adapterListViewFriday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listFriday.setAdapter(adapterFriday);
                return true;
            }
        });
        listFriday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabFridayTags.length; i++) {
                    if (i == position)
                        varObj.tabFridayTags[i] = true;
                }
            }
        });
        adapterFriday = new ArrayAdapter(this,android.R.layout.simple_list_item_1,varObj.adapterListViewFriday){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                return textView;
            }
        };
        listFriday.setAdapter(adapterFriday);

        listSaturday = (ListView) findViewById(R.id.tab_saturday_listview);
        listSaturday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabSaturdayText.length; i++) {
                    if (i == position) {
                        varObj.adapterListViewSaturday[i] = varObj.adapterListViewBackup[i];
                        varObj.tabSaturdayText[i]="";
                        writeStrings();
                    }
                }
                adapterSaturday = new ArrayAdapter(Program.this,android.R.layout.simple_list_item_1,varObj.adapterListViewSaturday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listSaturday.setAdapter(adapterSaturday);
                return true;
            }
        });
        listSaturday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabSaturdayTags.length; i++) {
                    if (i == position)
                        varObj.tabSaturdayTags[i] = true;
                }
            }
        });
        adapterSaturday = new ArrayAdapter(this,android.R.layout.simple_list_item_1,varObj.adapterListViewSaturday){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                return textView;
            }
        };
        listSaturday.setAdapter(adapterSaturday);

        listSunday = (ListView) findViewById(R.id.tab_sunday_listview);
        listSunday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabSundayText.length; i++) {
                    if (i == position) {
                        varObj.adapterListViewSunday[i] = varObj.adapterListViewBackup[i];
                        varObj.tabSundayText[i]="";
                        writeStrings();
                    }
                }
                adapterSunday = new ArrayAdapter(Program.this,android.R.layout.simple_list_item_1,varObj.adapterListViewSunday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listSunday.setAdapter(adapterSunday);
                return true;
            }
        });
        listSunday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < varObj.tabSundayTags.length; i++) {
                    if (i == position)
                        varObj.tabSundayTags[i] = true;
                }
            }
        });
        adapterSunday = new ArrayAdapter(this,android.R.layout.simple_list_item_1,varObj.adapterListViewSunday){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                return textView;
            }
        };
        listSunday.setAdapter(adapterSunday);



    }

    public void ButtonListeners() {
        tabMondayUpdate = (Button) findViewById(R.id.tab_monday_button2);
        tabMondayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabMondayTags.length; i++) {
                    if (varObj.tabMondayTags[i] == true) {
                        varObj.tabMondayText[i] = tabMondayText.getText().toString();
                        varObj.adapterListViewMonday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabMondayText[i] + ")";
                        varObj.tabMondayTags[i] = false;
                    }
                }
                adapterMonday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewMonday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listMonday.setAdapter(adapterMonday);
            }
        });
        tabMondayDelete = (Button)findViewById(R.id.tab_monday_button1);
        tabMondayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Monday");
                varObj.ArrayAdapterReset("Monday");
                adapterMonday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewMonday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listMonday.setAdapter(adapterMonday);
                varObj.clearTabTexts("Monday");
            }
        });

        tabTuesdayUpdate = (Button)findViewById(R.id.tab_tuesday_button2);
        tabTuesdayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabTuesdayTags.length; i++) {
                    if (varObj.tabTuesdayTags[i] == true) {
                        varObj.tabTuesdayText[i] = tabTuesdayText.getText().toString();
                        varObj.adapterListViewTuesday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabTuesdayText[i] + ")";
                        varObj.tabTuesdayTags[i] = false;
                    }
                }
                adapterTuesday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewTuesday) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listTuesday.setAdapter(adapterTuesday);
            }
        });
        tabTuesdayDelete = (Button)findViewById(R.id.tab_tuesday_button1);
        tabTuesdayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Tuesday");
                varObj.ArrayAdapterReset("Tuesday");
                adapterTuesday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewTuesday) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                varObj.clearTabTexts("Tuesday");
            }
        });

        tabWednesdayUpdate = (Button)findViewById(R.id.tab_wednesday_button2);
        tabWednesdayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabWednesdayTags.length; i++) {
                    if (varObj.tabWednesdayTags[i] == true) {
                        varObj.tabWednesdayText[i] = tabWednesdayText.getText().toString();
                        varObj.adapterListViewWednesday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabWednesdayText[i] + ")";
                        varObj.tabWednesdayTags[i] = false;
                    }
                }
                adapterWednesday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewWednesday) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listWednesday.setAdapter(adapterWednesday);
            }
        });
        tabWednesdayDelete = (Button)findViewById(R.id.tab_wednesday_button1);
        tabWednesdayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Wednesday");
                varObj.ArrayAdapterReset("Wednesday");
                adapterWednesday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewWednesday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listWednesday.setAdapter(adapterWednesday);
                varObj.clearTabTexts("Wednesday");
            }
        });

        tabThursdayUpdate = (Button)findViewById(R.id.tab_thursday_button2);
        tabThursdayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabThursdayTags.length; i++) {
                    if (varObj.tabThursdayTags[i] == true) {
                        varObj.tabThursdayText[i] = tabThursdayText.getText().toString();
                        varObj.adapterListViewThursday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabThursdayText[i] + ")";
                        varObj.tabThursdayTags[i] = false;
                    }
                }
                adapterThursday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewThursday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listThursday.setAdapter(adapterThursday);
            }
        });
        tabThursdayDelete = (Button)findViewById(R.id.tab_thursday_button1);
        tabThursdayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Thursday");
                varObj.ArrayAdapterReset("Thursday");
                adapterThursday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewThursday) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listThursday.setAdapter(adapterThursday);
                varObj.clearTabTexts("Thursday");
            }
        });

        tabFridayUpdate = (Button)findViewById(R.id.tab_friday_button2);
        tabFridayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabFridayTags.length; i++) {
                    if (varObj.tabFridayTags[i] == true) {
                        varObj.tabFridayText[i] = tabFridayText.getText().toString();
                        varObj.adapterListViewFriday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabFridayText[i] + ")";
                        varObj.tabFridayTags[i] = false;
                    }
                }
                adapterFriday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewFriday) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listFriday.setAdapter(adapterFriday);
            }
        });
        tabFridayDelete = (Button)findViewById(R.id.tab_friday_button1);
        tabFridayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Friday");
                varObj.ArrayAdapterReset("Friday");
                adapterFriday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewFriday) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listFriday.setAdapter(adapterFriday);
                varObj.clearTabTexts("Friday");
            }
        });

        tabSaturdayUpdate = (Button)findViewById(R.id.tab_saturday_button2);
        tabSaturdayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabSaturdayTags.length; i++) {
                    if (varObj.tabSaturdayTags[i] == true) {
                        varObj.tabSaturdayText[i] = tabSaturdayText.getText().toString();
                        varObj.adapterListViewSaturday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabSaturdayText[i] + ")";
                        varObj.tabSaturdayTags[i] = false;
                    }
                }
                adapterSaturday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewSaturday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listSaturday.setAdapter(adapterSaturday);
            }
        });
        tabSaturdayDelete = (Button)findViewById(R.id.tab_saturday_button1);
        tabSaturdayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Saturday");
                varObj.ArrayAdapterReset("Saturday");
                adapterSaturday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewSaturday){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listSaturday.setAdapter(adapterSaturday);
                varObj.clearTabTexts("Saturday");
            }
        });

        tabSundayUpdate = (Button)findViewById(R.id.tab_sunday_button2);
        tabSundayUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < varObj.tabSundayTags.length; i++) {
                    if (varObj.tabSundayTags[i] == true) {
                        varObj.tabSundayText[i] = tabSundayText.getText().toString();
                        varObj.adapterListViewSunday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabSundayText[i] + ")";
                        varObj.tabSundayTags[i] = false;
                    }
                }
                adapterSunday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewSunday) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listSunday.setAdapter(adapterSunday);
            }
        });
        tabSundayDelete = (Button)findViewById(R.id.tab_sunday_button1);
        tabSundayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varObj.tagsReset("Sunday");
                varObj.ArrayAdapterReset("Sunday");
                adapterSunday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewSunday) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return textView;
                    }
                };
                listSunday.setAdapter(adapterSunday);
                varObj.clearTabTexts("Sunday");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_program, menu);

        return true;
    }
    private void setupTabHost(){


        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        tabMonday = tabHost.newTabSpec("Monday");
        tabMonday.setContent(R.id.tab_monday);
        tabMonday.setIndicator("Monday");
        tabHost.addTab(tabMonday);

        tabTuesday = tabHost.newTabSpec("Tuesday");
        tabTuesday.setContent(R.id.tab_tuesday);
        tabTuesday.setIndicator("Tuesday");
        tabHost.addTab(tabTuesday);

        tabWednesday = tabHost.newTabSpec("Wednesday");
        tabWednesday.setContent(R.id.tab_wednesday);
        tabWednesday.setIndicator("Wednesday");
        tabHost.addTab(tabWednesday);

        tabThursday = tabHost.newTabSpec("Thursday");
        tabThursday.setContent(R.id.tab_thursday);
        tabThursday.setIndicator("Thursday");
        tabHost.addTab(tabThursday);

        tabFriday = tabHost.newTabSpec("Friday");
        tabFriday.setContent(R.id.tab_friday);
        tabFriday.setIndicator("Friday");
        tabHost.addTab(tabFriday);

        tabSaturday = tabHost.newTabSpec("Saturday");
        tabSaturday.setContent(R.id.tab_saturday);
        tabSaturday.setIndicator("Saturday");
        tabHost.addTab(tabSaturday);

        tabSunday = tabHost.newTabSpec("Sunday");
        tabSunday.setContent(R.id.tab_sunday);
        tabSunday.setIndicator("Sunday");
        tabHost.addTab(tabSunday);

        for(int i = 0 ; i<tabHost.getTabWidget().getChildCount(); i++){
                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#d20c0c"));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                intent = new Intent(this,Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.action_remove:
                a_builder = new AlertDialog.Builder(Program.this);
                a_builder.setMessage("Do you want to clean your all history?")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        varObj.clearTabTexts("all");
                        varObj.tagsReset("all");
                        varObj.ArrayAdapterReset("all");
                        writeStrings();

                        adapterMonday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewMonday){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView textView = (TextView) super.getView(position, convertView, parent);
                                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                                return textView;
                            }
                        };
                        listMonday.setAdapter(adapterMonday);

                        adapterTuesday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewTuesday){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView textView = (TextView) super.getView(position, convertView, parent);
                                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                                return textView;
                            }
                        };
                        listTuesday.setAdapter(adapterTuesday);

                        adapterWednesday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewWednesday){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView textView = (TextView) super.getView(position, convertView, parent);
                                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                                return textView;
                            }
                        };
                        listWednesday.setAdapter(adapterWednesday);

                        adapterThursday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewThursday){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView textView = (TextView) super.getView(position, convertView, parent);
                                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                                return textView;
                            }
                        };
                        listThursday.setAdapter(adapterThursday);

                        adapterFriday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewFriday){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView textView = (TextView) super.getView(position, convertView, parent);
                                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                                return textView;
                            }
                        };
                        listFriday.setAdapter(adapterFriday);

                        adapterSaturday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewSaturday){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView textView = (TextView) super.getView(position, convertView, parent);
                                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                                return textView;
                            }
                        };
                        listSaturday.setAdapter(adapterSaturday);

                        adapterSunday = new ArrayAdapter(Program.this, android.R.layout.simple_list_item_1, varObj.adapterListViewSunday){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView textView = (TextView) super.getView(position, convertView, parent);
                                textView.setTextColor(Color.parseColor("#FFFFFFFF"));
                                return textView;
                            }
                        };
                        listSunday.setAdapter(adapterSunday);

                        tabMondayText.setText("");
                        tabTuesdayText.setText("");
                        tabWednesdayText.setText("");
                        tabThursdayText.setText("");
                        tabFridayText.setText("");
                        tabSaturdayText.setText("");
                        tabSundayText.setText("");

                    }
                });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Warning!");
                alert.show();

                break;
            case R.id.action_confirm:
                a_builder = new AlertDialog.Builder(Program.this);
                a_builder.setMessage("Do you want to save your program permanently?")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        writeStrings();
                        startService();

                    }
                });
                alert = a_builder.create();
                alert.setTitle("Warning!");
                alert.show();

                break;
            default:

        }

        return super.onOptionsItemSelected(item);
    }
    public void startService(){
        intent = new Intent(getApplicationContext(),NotificationClass.class);
        startService(intent);
        NotificationClass.startFlag=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void readString(){
        try {
            FileInputStream fileInputStream = openFileInput("MondayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabMondayText[i]=lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("TuesdayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i=0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabTuesdayText[i]=lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("WednesdayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabWednesdayText[i]=lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("ThursdayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabThursdayText[i]=lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("FridayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabFridayText[i]=lines;
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("SaturdayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabSaturdayText[i]=lines;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileInputStream = openFileInput("SundayString.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                varObj.tabSundayText[i]=lines;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStrings(){

        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabMondayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("MondayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabTuesdayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("TuesdayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabWednesdayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("WednesdayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabThursdayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("ThursdayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabFridayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("FridayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabSaturdayText[i]);
            stringBuilder.append("\n");
        }
        writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("SaturdayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        stringBuilder = new StringBuilder();
        for(int i = 0 ; i < 48 ; i++) {
            stringBuilder.append(varObj.tabSundayText[i]);
            stringBuilder.append("\n");
        }
       writingString = stringBuilder.toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("SundayString.txt", MODE_PRIVATE);
            fileOutputStream.write(writingString.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareListView(){
        varObj.clearTabTexts("all");
        readString();
        varObj.tagsInitialize();
        varObj.findTags();
        for (int i = 0; i < varObj.tabMondayTags.length; i++) {
            if (varObj.tabMondayTags[i] == true) {
                    varObj.adapterListViewMonday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabMondayText[i] + ")";
                varObj.tabMondayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabTuesdayTags.length; i++) {
            if (varObj.tabTuesdayTags[i] == true) {
                varObj.adapterListViewTuesday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabTuesdayText[i] + ")";
                varObj.tabTuesdayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabWednesdayTags.length; i++) {
            if (varObj.tabWednesdayTags[i] == true) {
                varObj.adapterListViewWednesday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabWednesdayText[i] + ")";
                varObj.tabWednesdayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabThursdayTags.length; i++) {
            if (varObj.tabThursdayTags[i] == true) {
                varObj.adapterListViewThursday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabThursdayText[i] + ")";
                varObj.tabThursdayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabFridayTags.length; i++) {
            if (varObj.tabFridayTags[i] == true) {
                varObj.adapterListViewFriday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabFridayText[i] + ")";
                varObj.tabFridayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabSaturdayTags.length; i++) {
            if (varObj.tabSaturdayTags[i] == true) {
                varObj.adapterListViewSaturday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabSaturdayText[i] + ")";
                varObj.tabSaturdayTags[i] = false;
            }
        }
        for (int i = 0; i < varObj.tabSundayTags.length; i++) {
            if (varObj.tabSundayTags[i] == true) {
                varObj.adapterListViewSunday[i] = varObj.adapterListViewBackup[i] + "    (" + varObj.tabSundayText[i] + ")";
                varObj.tabSundayTags[i] = false;
            }
        }


    }
}
