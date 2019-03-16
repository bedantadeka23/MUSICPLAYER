package com.example.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class playerActivity extends AppCompatActivity {
    Button next, pause, previous;
    TextView songname;
    SeekBar sb;
    static MediaPlayer myMediaPlayer;
    int position;
   ArrayList<File> mysongs;
    Thread updateSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);
        pause = (Button) findViewById(R.id.pause);
        songname = (TextView) findViewById(R.id.Textview);
        sb = (SeekBar) findViewById(R.id.Seekbar);


        updateSeekbar = new Thread() {
            @Override
            public void run() {
                int totalDuration = myMediaPlayer.getDuration();
                int currentposition = 0;

                while (currentposition < totalDuration) {
                    try {
                        sleep(500);
                        currentposition = myMediaPlayer.getCurrentPosition();
                        sb.setProgress(currentposition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        if (myMediaPlayer != null) {
            myMediaPlayer.stop();
            myMediaPlayer.release();
        }
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mysongs=(ArrayList) bundle.getParcelableArrayList("songs");


    }
}