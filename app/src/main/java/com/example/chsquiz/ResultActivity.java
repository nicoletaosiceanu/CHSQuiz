package com.example.chsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView t1,t2,t3;
    Button b1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        t1=(TextView)findViewById(R.id.textView4);
        t2=(TextView)findViewById(R.id.textView5);
        t3=(TextView)findViewById(R.id.textView6);
        b1 = (Button) findViewById(R.id.button1);

        Intent i=getIntent();

        String questions=i.getStringExtra("total");
        String correct=i.getStringExtra("correct");
        String wrong=i.getStringExtra("incorrect");

        t1.setText(questions);
        t2.setText(correct);
        t3.setText(wrong);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.chsquiz.ResultActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void onBackPressed() {
        Intent ii = new Intent(com.example.chsquiz.ResultActivity.this, MainActivity.class);
        startActivity(ii);
    }
}