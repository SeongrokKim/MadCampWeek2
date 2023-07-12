package com.example.madcampweek2;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MetronomeActivity extends AppCompatActivity {
    private Button startButton;
    private Button stopButton;
    private SoundPool soundPool;
    private int tickSoundId;
    private Handler handler;
    private Runnable tickRunnable;
    private boolean isPlaying;
    private int bpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        EditText bpmView = findViewById(R.id.bpmView);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        tickSoundId = soundPool.load(this, R.raw.tick_sound_500, 1);

        handler = new Handler();
        tickRunnable = new Runnable() {
            @Override
            public void run() {
                playTickSound();
                handler.postDelayed(this, calculateTickDelay()-5);
            }
        };

        isPlaying = false;

        ImageView metronomeImageView = findViewById(R.id.metroView);
        ImageView plusButton = findViewById(R.id.plusButton);
        ImageView minusButton = findViewById(R.id.minusButton);



        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpmView.setText(String.valueOf(Integer.valueOf(bpmView.getText().toString())+5));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int val = Integer.valueOf(bpmView.getText().toString());
                if(val>5){
                    bpmView.setText(String.valueOf(val-5));
                }
            }
        });


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = bpmView.getText().toString();
                if(text.equals("")){
                    Toast.makeText(MetronomeActivity.this, "bpm을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(text.equals("0")){
                    Toast.makeText(MetronomeActivity.this, "1 이상의 bpm을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                bpm = Integer.valueOf(text);
                int distanceToMove = 500;
                Animation animation = new TranslateAnimation(0, distanceToMove, 0, 0);
                animation.setDuration((long)(calculateTickDelay()*0.952)); // 애니메이션의 지속 시간 설정 (예: 500밀리초)
                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.REVERSE);
                Toast.makeText(getApplicationContext(), "시작", Toast.LENGTH_SHORT).show();
                if (!isPlaying) {
                    handler.postDelayed(tickRunnable, calculateTickDelay());
                    isPlaying = true;
                }
                metronomeImageView.startAnimation(animation);

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "끝", Toast.LENGTH_SHORT).show();
                if (isPlaying) {
                    handler.removeCallbacks(tickRunnable);
                    isPlaying = false;
                }
                metronomeImageView.clearAnimation();
            }
        });


    }



    private void playTickSound() {
        soundPool.play(tickSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    private long calculateTickDelay() {
        // Calculate the delay between ticks based on the current BPM
        long delayInMillis = (long) (60000.0 / bpm);
        return delayInMillis;
    }
}