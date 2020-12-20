
package com.example.chsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Alegemat extends AppCompatActivity {

    Button b1,b2;
    TextView t1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alegematerie);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        t1=(TextView)findViewById(R.id.textView8);

        Intent i=getIntent();
        String anstud=i.getStringExtra("numean");
        t1.setText(anstud);

       // String sir =  t1.getText().toString();


        if(anstud.equals("AN I"))
        {
            b1.setText("FIZICA");
            b2.setText("FIMR");
        }

        if(anstud.equals("AN II"))
        {
            b1.setText("POO");
            b2.setText("PTDM");
        }

        if(anstud.equals("AN III"))
        {
            b1.setText("FIC");
            b2.setText("MM");
        }

        if(anstud.equals("AN IV"))
        {
            b1.setText("CHS");
            b2.setText("PBD");
        }
    }
}