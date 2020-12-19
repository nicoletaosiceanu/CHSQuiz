package com.example.chsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class firstpage extends AppCompatActivity {

    TextView t1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage_main);
        t1=(TextView)findViewById(R.id.textView);


        Intent i=getIntent();

        String questions=i.getStringExtra("total intrb");


        t1.setText(questions);


    }
}