package com.example.chsquiz;

 import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
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

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;

        import Model.Question;

        import static android.os.CountDownTimer.*;

public class quizut extends AppCompatActivity implements SensorEventListener  {

    Button b1,b2,b3,b4;
    TextView t1_question,timerTxt,intrebare,cronometru;
    int total=0;
    int correct=0;
    int k=0;
    int READINGRATE = 1000000; // time in us ,500 ms   1 s
    int MIN_TIME_BETWEEN_SAMPLES_NS=1500000000; //500 ms  800 ms
    long mLastTimestamp=0;
    DatabaseReference reference;
    int wrong =0;
    String materia;
     Question question ;
    public List<Integer> questionNos = new ArrayList<>();
   public  CountDownTimer timer;
    long milliLeft;
    private SensorManager sensorManager; //Represents the Android sensor service
    private Sensor accelerometer; // Represents a specific sensor

     float deltaX = 0;
     float deltaY = 0;
     float deltaZ = 0;

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
        for (int i = 1; i < 7; i++) {
            questionNos.add(i);
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent ii=getIntent();
        materia=ii.getStringExtra("numematerie");
        reverseTimer(30,timerTxt);
        updateQuestion();

    }
    private  void updateQuestion()
    {

        Random r = new Random();
        int index = r.nextInt(questionNos.size());
        int nrintrebare= questionNos.get(index);
         questionNos.remove(index);


        total++;
        if(total>4)
        {
            //open the result activity
            k=1;
            Intent i=new Intent(com.example.chsquiz.quizut.this,ResultActivity.class);
            i.putExtra("total",String.valueOf(total-1));
            i.putExtra("correct",String.valueOf(correct));
            i.putExtra("incorrect",String.valueOf(wrong));
            startActivity(i);

        }
        else
        {
            reference= FirebaseDatabase.getInstance().getReference().child("TOATEMATERIILE").child(materia).child(String.valueOf(nrintrebare));
            //total++;
            reference.addValueEventListener(new ValueEventListener()
            {
                public void onDataChange(DataSnapshot dataSnapshot) {
                     question = dataSnapshot.getValue(Question.class);
                    t1_question.setText(question.getQuestion());
                    b1.setText(question.getOption1());
                    b2.setText(question.getOption2());
                    b3.setText(question.getOption3());
                    b4.setText(question.getOption4());



                }
                public void onCancelled(DatabaseError databaseError)
                {

                }

            });
        }


    }


    //millisInFuture = The number of millis in the future from the call to start() until the countdown is done and onFinish() is called.
    //countDownInterval =The interval at which you would like to receive timer updates.
    public void reverseTimer(int seconds,final TextView tv) {

        timer=new CountDownTimer(seconds * 1000 + 1000, 1000) {

                public void onTick(long millisUntilFinished)
                {
                    milliLeft=millisUntilFinished;
                    int seconds = (int) (millisUntilFinished / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;

                    tv.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                }


                public void onFinish() {
                    tv.setText("Completed");
                    if (k == 0  ) {
                        Intent myIntent = new Intent(com.example.chsquiz.quizut.this, ResultActivity.class);
                        myIntent.putExtra("total", String.valueOf(total));
                        myIntent.putExtra("correct", String.valueOf(correct));
                        myIntent.putExtra("incorrect", String.valueOf(wrong));
                        startActivity(myIntent);
                    }
                }
            }.start();
    }

    public void timerResume()
    {
        if (timer == null) {
            reverseTimer((int) milliLeft / 1000, timerTxt);
        }
    }

    public void onBackPressed() {
       timer.cancel();
       super.onBackPressed();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onStart() {
        super.onStart();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            //get a Sensor object
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            // register a listener
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            // current activity ,the Sensor object to listen to,a delay constant from the SensorManager class
            //The data delay controls the interval at which sensor events are sent to app via the onSensorChanged()
        }
    }

    //onResume() register the accelerometer for listening the events
    protected void onResume() {
        super.onResume();
        timerResume();
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Called when the activity is no longer visible to the user. This may happen either because a new activity is
    // being started on top, an existing one is being brought in front of this one, or this one is being destroyed.
    protected void onStop() {
        super.onStop();
        //timer.cancel();
       // timer=null;
        sensorManager.unregisterListener(this);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        timer.cancel();
        timer=null;
        sensorManager.unregisterListener(this);
    }
    //The Android system calls the onSensorChanged() method when the sensor reports new data, passing in a SensorEvent object.
    public void onSensorChanged(SensorEvent event) {

        if (event.timestamp - mLastTimestamp < MIN_TIME_BETWEEN_SAMPLES_NS) {
        } else {
            deltaX = event.values[0];
            deltaY = event.values[1];
            deltaZ = event.values[2];

            mLastTimestamp = event.timestamp;
            // if the change is below 2, it is just plain noise

            if (deltaX < 2 && deltaX > -2)
                deltaX = 0;
            if (deltaY < 2 && deltaY > -2)
                deltaY = 0;
            if (deltaZ < 2 && deltaZ > -2)
                deltaZ = 0;

            if (deltaY > 2.0 && deltaZ > 6) {
                if (b1.getText().toString().equals(question.getAnswer())) {
                    b1.setBackgroundColor(Color.GREEN);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            correct++;
                            b1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            updateQuestion();
                        }
                    }, 1500);
                } else {
                    //answer wrong, find the correct answer and make it green
                    wrong++;
                    b1.setBackgroundColor(Color.RED);

                    if (b2.getText().toString().equals(question.getAnswer())) {
                        b2.setBackgroundColor(Color.GREEN);
                    } else if (b3.getText().toString().equals(question.getAnswer())) {
                        b3.setBackgroundColor(Color.GREEN);
                    } else if (b4.getText().toString().equals(question.getAnswer())) {
                        b4.setBackgroundColor(Color.GREEN);
                    }


                    Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            b1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            updateQuestion();
                        }
                    }, 1500);


                }
            }
            if (deltaY < -2 && deltaZ > 6) {   /*gslben */

                // b1.setBackgroundColor(Color.parseColor("#FFFF00"));
                if (b2.getText().toString().equals(question.getAnswer())) {
                    b2.setBackgroundColor(Color.GREEN);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            correct++;
                            b2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            updateQuestion();
                        }
                    }, 1500);
                } else {
                    //answer wrong, find the correct answer and make it green
                    wrong++;
                    b2.setBackgroundColor(Color.RED);

                    if (b1.getText().toString().equals(question.getAnswer())) {
                        b1.setBackgroundColor(Color.GREEN);
                    } else if (b3.getText().toString().equals(question.getAnswer())) {
                        b3.setBackgroundColor(Color.GREEN);
                    } else if (b4.getText().toString().equals(question.getAnswer())) {
                        b4.setBackgroundColor(Color.GREEN);
                    }


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            b1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            updateQuestion();
                        }
                    }, 1500);


                }
            }
            if (deltaX > 2.0 && deltaZ > 6.0) {  /*albastru*/

                // b1.setBackgroundColor(Color.parseColor("#0000FF"));
                if (b4.getText().toString().equals(question.getAnswer())) {
                    b4.setBackgroundColor(Color.GREEN);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            correct++;
                            b4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            updateQuestion();
                        }
                    }, 1500);
                } else {
                    //answer wrong, find the correct answer and make it green
                    wrong++;
                    b4.setBackgroundColor(Color.RED);

                    if (b1.getText().toString().equals(question.getAnswer())) {
                        b1.setBackgroundColor(Color.GREEN);
                    } else if (b2.getText().toString().equals(question.getAnswer())) {
                        b2.setBackgroundColor(Color.GREEN);
                    } else if (b3.getText().toString().equals(question.getAnswer())) {
                        b3.setBackgroundColor(Color.GREEN);
                    }


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            b1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            updateQuestion();
                        }
                    }, 1500);


                }
            }

            if (deltaX < -2.0 && deltaZ > 6.0) {
                //b1.setBackgroundColor(Color.parseColor("#FF0000"));
                if (b3.getText().toString().equals(question.getAnswer())) {
                    b3.setBackgroundColor(Color.GREEN);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            correct++;
                            b3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            updateQuestion();
                        }
                    }, 1500);
                } else {
                    //answer wrong, find the correct answer and make it green
                    wrong++;
                    b3.setBackgroundColor(Color.RED);

                    if (b1.getText().toString().equals(question.getAnswer())) {
                        b1.setBackgroundColor(Color.GREEN);
                    } else if (b2.getText().toString().equals(question.getAnswer())) {
                        b2.setBackgroundColor(Color.GREEN);
                    } else if (b4.getText().toString().equals(question.getAnswer())) {
                        b4.setBackgroundColor(Color.GREEN);
                    }


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            b1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            updateQuestion();
                        }
                    }, 1500);


                }
            }
        }
    }
}