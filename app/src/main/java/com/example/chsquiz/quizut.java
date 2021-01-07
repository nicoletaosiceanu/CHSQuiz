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

public class quizut extends AppCompatActivity implements SensorEventListener  {

    Button b1,b2,b3,b4;
    TextView text_question,timerTxt,intrebare,cronometru;
    int total=0;
    int count=0;
    int correct=0;
    int k=0;
    int MIN_TIME_BETWEEN_SAMPLES_NS= 1500000000; //1500 ms
    long mLastTimestamp=0;
    DatabaseReference reference;
    int wrong =0;
    String materia;
     Question question ;
    public List<Integer> questionNr = new ArrayList<>();
   public  CountDownTimer timer;
    long milliLeft;
    private SensorManager sensorManager; //Represents the Android sensor service
    private Sensor accelerometer; // Represents a specific sensor

     float X = 0;
     float Y = 0;
     float Z = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1= (Button)findViewById(R.id.button1);
        b2= (Button)findViewById(R.id.button2);
        b3= (Button)findViewById(R.id.button3);
        b4= (Button)findViewById(R.id.button4);
        text_question =(TextView)findViewById(R.id.questionsTxt);
        timerTxt=(TextView)findViewById(R.id.timerTxt);
        intrebare=(TextView)findViewById(R.id.intrb);
        cronometru=(TextView)findViewById(R.id.crono);

        //questionNos contine numerele intrebarilor
        for (int i = 1; i < 7; i++) {
            questionNr.add(i);
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent ii=getIntent();
        materia=ii.getStringExtra("numematerie");
        createTimer(40,timerTxt);
        updateQuestion();

    }
    private  void updateQuestion()
    {
        //nextInt(n) is used to get a random number between 0(inclusive) and the number passed in this argument(n), exclusive,
              // in our case 0-5
        Random r = new Random();
        int index = r.nextInt(questionNr.size());
        //nrintrebare este nr intrebarii din questionNr de la pozitia index
        int nrintrebare= questionNr.get(index);
        //stergem nr intrebarii din questionNr de la pozitia index pentru a nu folosi din nou acea intrebare
         questionNr.remove(index);


        total++;
        if(total>4)
        {
            //daca am parcurs cele 4 intrebari , ajungem pe pagina cu rezultate
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
            reference.addValueEventListener(new ValueEventListener()
            {
                public void onDataChange(DataSnapshot dataSnapshot) {
                     question = dataSnapshot.getValue(Question.class);
                     count++;
                    text_question.setText(question.returneazaIntrebare());
                    b1.setText(question.returneazaOptiunea1());
                    b2.setText(question.returneazaOptiunea2());
                    b3.setText(question.returneazaOptiunea3());
                    b4.setText(question.returneazaOptiunea4());

                }
                public void onCancelled(DatabaseError databaseError)
                {
                }

            });
        }
    }

    //millisInFuture = The number of millis in the future from the call to start() until the countdown is done and onFinish() is called.
    //countDownInterval =The interval at which you would like to receive timer updates.
    public void createTimer(int seconds, final TextView tv) {

        timer=new CountDownTimer(seconds * 1000, 1000) {

                public void onTick(long millisUntilFinished)
                {
                    milliLeft=millisUntilFinished;
                    int seconds = (int) (millisUntilFinished / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;

                    tv.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                }
                public void onFinish() {
                    tv.setText("Gata");
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
            createTimer((int) milliLeft / 1000, timerTxt);
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
            //get a Sensor object
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            // register a listener
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                         // current activity ,the Sensor object to listen to,a delay constant from the SensorManager class
            //The data delay controls the interval at which sensor events are sent to app via the onSensorChanged()
        }
    }


    protected void onResume() {
        super.onResume();
        timerResume();
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        timer.cancel();
        timer=null;
        sensorManager.unregisterListener(this);
    }
    //The Android system calls the onSensorChanged() method when the sensor reports new data, passing in a SensorEvent object.
    public void onSensorChanged(SensorEvent event) {

        //timestamp  returneaza timpul la care evenimentul a fost creat
        //daca diferenta dintre evenimentul actual si cel trecut e mai mare de 1,5 secunde si a fost afisata cel putin
        // o intrebare ,continuam si verificam ce buton a fost selectat
        if (event.timestamp - mLastTimestamp < MIN_TIME_BETWEEN_SAMPLES_NS) {
        } else if(count >0) {
            X = event.values[0];
            Y = event.values[1];
            Z = event.values[2];

            mLastTimestamp = event.timestamp;

            //daca valoarea e sub 2 si mai mare ca -2, o consideram 0
            if (X < 2 && X > -2)
                X = 0;
            if (Y < 2 && Y > -2)
                Y = 0;
            if (Z < 2 && Z > -2)
                Z = 0;

            //suntem pe axa Y
            if (Y > 2.0 && Z > 6 && (X ==0)) {
                //daca textul butonului b1 este raspunsul corect, butonul o sa devina verde
                if (b1.getText().toString().equals(question.getAnswer())) {
                    b1.setBackgroundColor(Color.GREEN);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            correct++;
                            b1.setBackgroundColor(Color.WHITE);
                            updateQuestion();
                        }
                    }, 1500);
                } else {
                    // raspunsul e gresit ,se face rosu, cautam raspunsul corect , acesta se face verde
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
                            b1.setBackgroundColor(Color.WHITE);
                            b2.setBackgroundColor(Color.WHITE);
                            b3.setBackgroundColor(Color.WHITE);
                            b4.setBackgroundColor(Color.WHITE);
                            updateQuestion();
                        }
                    }, 1500);


                }
            }
            if (Y < -2 && Z > 6 && (X ==0)) {
                if (b2.getText().toString().equals(question.getAnswer())) {
                    b2.setBackgroundColor(Color.GREEN);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            correct++;
                            b2.setBackgroundColor(Color.WHITE);
                            updateQuestion();
                        }
                    }, 1500);
                } else {
                    // raspunsul e gresit ,se face rosu, cautam raspunsul corect , acesta se face verde
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
                            b1.setBackgroundColor(Color.WHITE);
                            b2.setBackgroundColor(Color.WHITE);
                            b3.setBackgroundColor(Color.WHITE);
                            b4.setBackgroundColor(Color.WHITE);
                            updateQuestion();
                        }
                    }, 1500);


                }
            }

            //suntem pe axa X
            if (X > 2.0 && Z > 6.0 && (Y ==0)) {
                if (b4.getText().toString().equals(question.getAnswer())) {
                    b4.setBackgroundColor(Color.GREEN);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            correct++;
                            b4.setBackgroundColor(Color.WHITE);
                            updateQuestion();
                        }
                    }, 1500);
                } else {
                    // raspunsul e gresit ,se face rosu, cautam raspunsul corect , acesta se face verde
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
                            b1.setBackgroundColor(Color.WHITE);
                            b2.setBackgroundColor(Color.WHITE);
                            b3.setBackgroundColor(Color.WHITE);
                            b4.setBackgroundColor(Color.WHITE);
                            updateQuestion();
                        }
                    }, 1500);


                }
            }

            if (X < -2.0 && Z > 6.0 && (Y ==0)) {
                //b1.setBackgroundColor(Color.parseColor("#FF0000"));
                if (b3.getText().toString().equals(question.getAnswer())) {
                    b3.setBackgroundColor(Color.GREEN);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            correct++;
                            b3.setBackgroundColor(Color.WHITE);
                            updateQuestion();
                        }
                    }, 1500);
                } else {
                    // raspunsul e gresit ,se face rosu, cautam raspunsul corect , acesta se face verde
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
                            b1.setBackgroundColor(Color.WHITE);
                            b2.setBackgroundColor(Color.WHITE);
                            b3.setBackgroundColor(Color.WHITE);
                            b4.setBackgroundColor(Color.WHITE);
                            updateQuestion();
                        }
                    }, 1500);


                }
            }
        }
    }
}