package com.example.mertdesktop.schoolproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class ListChangePopUp extends Activity {
    public EditText myText;
    public  static String InsideMyText;
    private Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .7), (int) (height * .15));
        myText = (EditText)findViewById(R.id.editText);
        myButton=(Button)findViewById(R.id.button);
        buttonClickListener();
    }
    public void buttonClickListener(){

        myButton=(Button)findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             myText = (EditText)findViewById(R.id.editText);
             InsideMyText=myText.getText().toString();
             finish();
            }
        });
    }
}
