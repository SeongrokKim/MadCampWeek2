package com.example.madcampweek2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class PracticeActivity extends AppCompatActivity {
    private TextView timerTextView;
    private EditText countView, categoryView, titleView;
    WriteJournalFragment writeJournalFragment;
    private Button startButton, stopButton, resetButton,  finishButton;
    private ImageView plusButton, minusButton, writeButton;
    private boolean isRunning;
    private int seconds;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        writeJournalFragment = new WriteJournalFragment();

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        writeButton = findViewById(R.id.writeView);

        plusButton = findViewById(R.id.plus_btn);
        minusButton = findViewById(R.id.minus_btn);
        countView = findViewById(R.id.count);

        categoryView = findViewById(R.id.category);
        titleView = findViewById(R.id.title);

        finishButton = findViewById(R.id.finish_btn);

        handler = new Handler();
        String journal[] ={null};
        String uid[] = {null};
        String category = null;
        String title = null;
        String count = null;
        String time = null;
        Intent intent = getIntent();
        if(intent != null){
            uid[0] = intent.getStringExtra("uid");
            journal[0] = intent.getStringExtra("journal");
            category = intent.getStringExtra("category");
            title = intent.getStringExtra("title");
            count = intent.getStringExtra("count");
            time = intent.getStringExtra("time");
        }
        if(journal[0]==null){
            journal[0]="";
        }

        categoryView.setText(category);
        titleView.setText(title);
        if(count!=null){
            countView.setText(count);
        }
        if(time!=null){
            timerTextView.setText(time);
        }
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer cnt = Integer.valueOf(String.valueOf(countView.getText()));
                countView.setText(String.valueOf(cnt+1));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer cnt = Integer.valueOf(String.valueOf(countView.getText()));
                if(cnt>=1) {
                    countView.setText(String.valueOf(cnt - 1));
                }
            }
        });

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                System.out.println("print uid[0]"+uid[0]);
                bundle.putString("uid", uid[0]);
                bundle.putString("journal", journal[0]);
                bundle.putString("category", categoryView.getText().toString());
                bundle.putString("title", titleView.getText().toString());
                bundle.putString("count", countView.getText().toString());
                bundle.putString("time", timerTextView.getText().toString());
                writeJournalFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, writeJournalFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 다이얼로그를 생성하기 위한 빌더 객체 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(PracticeActivity.this);
                // 다이얼로그 메시지 설정
                builder.setMessage("연습을 마치고 기록하시겠습니까?");
                // 취소 버튼 설정
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // 다이얼로그 닫기
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String time = timerTextView.getText().toString();
                        String count = countView.getText().toString();
                        String category = categoryView.getText().toString();
                        String title = titleView.getText().toString();

                        if(category.equals("")){
                            Toast.makeText(getBaseContext(), "연습한 항목을 기입하세요", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        System.out.println("ssss"+journal[0]+"aaa");
                        PracticeRequest practiceRequest = new PracticeRequest(uid[0], time, count, journal[0], category, title, null);
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(practiceRequest);

                        dialog.dismiss(); // 다이얼로그 닫기
                        Intent intent1 = new Intent(PracticeActivity.this, MainActivity.class);
                        intent1.putExtra("UID", uid[0]);
                        finishAffinity();
                        startActivity(intent1);
                        finish();

                    }
                });
                // 다이얼로그 생성 및 표시
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void startTimer() {
        if (!isRunning) {
            isRunning = true;
            handler.postDelayed(timerRunnable, 1000);
        }
    }

    private void stopTimer() {
        if (isRunning) {
            isRunning = false;
            handler.removeCallbacks(timerRunnable);
        }
    }

    private void resetTimer() {
        stopTimer();
        seconds = 0;
        updateTimerText();
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            seconds++;
            updateTimerText();
            handler.postDelayed(this, 1000);
        }
    };

    private void updateTimerText() {
        int minutes = (seconds / 60) % 60;
        int hours = minutes / 60;
        int remainingSeconds = seconds % 60;

        String time = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
        timerTextView.setText(time);
    }


}
