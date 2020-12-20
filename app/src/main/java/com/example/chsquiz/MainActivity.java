 package com.example.chsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Question;

        public class MainActivity extends AppCompatActivity {

            Button b1, b2, b3, b4;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.firstpage_main);
                b1 = (Button) findViewById(R.id.button);
                b2 = (Button) findViewById(R.id.button2);
                b3 = (Button) findViewById(R.id.button3);
                b4 = (Button) findViewById(R.id.button4);

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(com.example.chsquiz.MainActivity.this, quizut.class);
                        startActivity(i);
                    }
                });

                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(com.example.chsquiz.MainActivity.this, Alegemat.class);
                        startActivity(i);
                    }
                });

                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(com.example.chsquiz.MainActivity.this, Alegemat.class);
                        startActivity(i);
                    }
                });
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(com.example.chsquiz.MainActivity.this, Alegemat.class);
                        startActivity(i);
                    }
                });
            }
        }
