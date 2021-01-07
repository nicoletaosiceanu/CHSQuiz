 package com.example.chsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                        Intent i = new Intent(com.example.chsquiz.MainActivity.this, Alegemat.class);
                        String buttonText = b1.getText().toString();
                       i.putExtra("numean", String.valueOf(buttonText));
                        startActivity(i);
                    }
                });

                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(com.example.chsquiz.MainActivity.this, Alegemat.class);
                        String buttonText = b2.getText().toString();
                        i.putExtra("numean", String.valueOf(buttonText));
                        startActivity(i);
                    }
                });

                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(com.example.chsquiz.MainActivity.this, Alegemat.class);
                        String buttonText = b3.getText().toString();
                        i.putExtra("numean", String.valueOf(buttonText));
                        startActivity(i);
                    }
                });
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(com.example.chsquiz.MainActivity.this, Alegemat.class);
                        String buttonText = b4.getText().toString();
                        i.putExtra("numean", String.valueOf(buttonText));
                        startActivity(i);
                    }
                });
            }
        }
