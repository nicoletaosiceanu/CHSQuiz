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

            Button b1,b2,b3,b4;
            TextView t1_question,timerTxt,intrebare,cronometru;
            int total=0;
            int correct=0;
            DatabaseReference reference;
            int wrong =0;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                b1= (Button)findViewById(R.id.button1);
                b2= (Button)findViewById(R.id.button2);
                b3= (Button)findViewById(R.id.button3);
                b4= (Button)findViewById(R.id.button4);
                t1_question=(TextView)findViewById(R.id.questionsTxt);
                timerTxt=(TextView)findViewById(R.id.timerTxt);
                intrebare=(TextView)findViewById(R.id.intrb);
                cronometru=(TextView)findViewById(R.id.crono);


                updateQuestion();
                reverseTimer(30,timerTxt);
            }
            private  void updateQuestion()
            {
                total++;
                if(total>4)
                {
                    //open the result activity
                    Intent i=new Intent(com.example.chsquiz.MainActivity.this,ResultActivity.class);
                    i.putExtra("total",String.valueOf(total-1));
                    i.putExtra("correct",String.valueOf(correct));
                    i.putExtra("incorrect",String.valueOf(wrong));
                    startActivity(i);
                }
                else
                {
                    reference= FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
                    //total++;
                    reference.addValueEventListener(new ValueEventListener()
                    {
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            final Question question=dataSnapshot.getValue(Question.class);
                            t1_question.setText(question.getQuestion());
                            b1.setText(question.getOption1());
                            b2.setText(question.getOption2());
                            b3.setText(question.getOption3());
                            b4.setText(question.getOption4());


                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(b1.getText().toString().equals(question.getAnswer()))
                                    {
                                        b1.setBackgroundColor(Color.GREEN);
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);
                                    }
                                    else
                                    {
                                        //answer wrong, find the correct answer and make it green
                                        wrong++;
                                        b1.setBackgroundColor(Color.RED);

                                        if(b2.getText().toString().equals(question.getAnswer()))
                                        {
                                            b2.setBackgroundColor(Color.GREEN);
                                        }
                                        else  if(b3.getText().toString().equals(question.getAnswer()))
                                        {
                                            b3.setBackgroundColor(Color.GREEN);
                                        }
                                        else  if(b4.getText().toString().equals(question.getAnswer()))
                                        {
                                            b4.setBackgroundColor(Color.GREEN);
                                        }


                                        Handler handler=new Handler();
                                /*handler.postDelayed({
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();
                                        },1500);
                                */
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);




                                    }
                                }
                            });



                            b2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(b2.getText().toString().equals(question.getAnswer()))
                                    {
                                        b2.setBackgroundColor(Color.GREEN);
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);
                                    }
                                    else
                                    {
                                        //answer wrong, find the correct answer and make it green
                                        wrong++;
                                        b2.setBackgroundColor(Color.RED);

                                        if(b1.getText().toString().equals(question.getAnswer()))
                                        {
                                            b1.setBackgroundColor(Color.GREEN);
                                        }
                                        else  if(b3.getText().toString().equals(question.getAnswer()))
                                        {
                                            b3.setBackgroundColor(Color.GREEN);
                                        }
                                        else  if(b4.getText().toString().equals(question.getAnswer()))
                                        {
                                            b4.setBackgroundColor(Color.GREEN);
                                        }


                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);



                                    }
                                }
                            });

                            b3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(b3.getText().toString().equals(question.getAnswer()))
                                    {
                                        b3.setBackgroundColor(Color.GREEN);
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);
                                    }
                                    else
                                    {
                                        //answer wrong, find the correct answer and make it green
                                        wrong++;
                                        b3.setBackgroundColor(Color.RED);

                                        if(b1.getText().toString().equals(question.getAnswer()))
                                        {
                                            b1.setBackgroundColor(Color.GREEN);
                                        }
                                        else  if(b2.getText().toString().equals(question.getAnswer()))
                                        {
                                            b2.setBackgroundColor(Color.GREEN);
                                        }
                                        else  if(b4.getText().toString().equals(question.getAnswer()))
                                        {
                                            b4.setBackgroundColor(Color.GREEN);
                                        }


                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);



                                    }
                                }
                            });


                            b4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(b4.getText().toString().equals(question.getAnswer()))
                                    {
                                        b4.setBackgroundColor(Color.GREEN);
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);
                                    }
                                    else
                                    {
                                        //answer wrong, find the correct answer and make it green
                                        wrong++;
                                        b4.setBackgroundColor(Color.RED);

                                        if(b1.getText().toString().equals(question.getAnswer()))
                                        {
                                            b1.setBackgroundColor(Color.GREEN);
                                        }
                                        else  if(b2.getText().toString().equals(question.getAnswer()))
                                        {
                                            b2.setBackgroundColor(Color.GREEN);
                                        }
                                        else  if(b3.getText().toString().equals(question.getAnswer()))
                                        {
                                            b3.setBackgroundColor(Color.GREEN);
                                        }


                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);



                                    }

                                }
                            });








                        }
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }

                    });
                }
            }
            public void reverseTimer(int seconds,final TextView tv)
            {
                new CountDownTimer(seconds*1000+1000,1000)
                {
                    public void onTick(long millisUntilFinished)
                    {
                        int seconds=(int)(millisUntilFinished/1000);
                        int minutes=seconds/60;
                        seconds=seconds%60;
                        tv.setText(String.format("%02d",minutes)+":"+String.format("%02d",seconds));
                    }
                    public void onFinish()
                    {
                        tv.setText("Completed");
                        Intent myIntent=new Intent(com.example.chsquiz.MainActivity.this,ResultActivity.class);
                        myIntent.putExtra("total",String.valueOf(total));
                        myIntent.putExtra("correct",String.valueOf(correct));
                        myIntent.putExtra("incorrect",String.valueOf(wrong));
                        startActivity(myIntent);
                    }
                }.start();

            }
        }