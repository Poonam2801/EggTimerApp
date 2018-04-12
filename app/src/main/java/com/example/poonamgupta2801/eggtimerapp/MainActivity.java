package com.example.poonamgupta2801.eggtimerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerBar;
    TextView timerTextView;
    Button controllerButton;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText ("0:00");
        timerBar.setProgress ( 0 );
        timerBar.setEnabled ( true );
        countDownTimer.cancel ();
        controllerButton.setText ( "Go" );
        counterIsActive=false;

    }

    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        String secondsString = Integer.toString ( seconds );


        if (seconds < 10) secondsString = "0" + secondsString;
        timerTextView.setText ( Integer.toString ( minutes ) + ":" + secondsString );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );


        ImageView eggTimer = (ImageView) findViewById ( R.id.eggTimer );
        controllerButton = (Button) findViewById ( R.id.controlButton );
        timerTextView = (TextView) findViewById ( R.id.timertextView );
        timerBar = (SeekBar) findViewById ( R.id.timerBar );

        timerBar.setMax ( 600 );
        timerBar.setProgress ( 10 );

        timerBar.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener () {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer ( progress );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        } );

    }

    public void controller(View view) {

        if (counterIsActive == false) {

            counterIsActive=true;
            timerBar.setEnabled ( false );
            controllerButton.setText ( "STOP" );

            countDownTimer=  new CountDownTimer ( timerBar.getProgress () * 1000 + 100, 1000 ) {

                public void onTick(long millisUntilFinished) {

                    updateTimer ( (int) millisUntilFinished / 1000 );

                }

                public void onFinish() {
                    resetTimer ();
                    MediaPlayer mediaPlayer = MediaPlayer.create ( getApplicationContext (), R.raw.horn );
                    mediaPlayer.start ();
                }


            }.start ();
        }else {
           resetTimer ();

        }
    }
}

