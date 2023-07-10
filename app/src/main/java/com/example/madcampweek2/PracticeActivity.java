package com.example.madcampweek2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private EditText countView;
    private Button startButton, stopButton, resetButton, plusButton, minusButton, finishButton;
    private ImageView writeButton;
    private boolean isRunning;
    private int seconds;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        writeButton = findViewById(R.id.writeView);

        plusButton = findViewById(R.id.plus_btn);
        minusButton = findViewById(R.id.minus_btn);
        countView = findViewById(R.id.count);

        finishButton = findViewById(R.id.finish_btn);

        handler = new Handler();

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
                WriteJournalFragment writeJournalFragment = new WriteJournalFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, writeJournalFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Toast.makeText(requireContext(), "게시글 삭제", Toast.LENGTH_SHORT).show();
//                // 다이얼로그를 생성하기 위한 빌더 객체 생성
//                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//                // 다이얼로그 메시지 설정
//                builder.setMessage("게시글을 삭제하시겠습니까?");
//                // 취소 버튼 설정
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss(); // 다이얼로그 닫기
//                    }
//                });
//                // 확인 버튼 설정
//                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 게시글 삭제 코드 등을 작성
//                        DeleteBoardRequest deleteBoardRequest = new DeleteBoardRequest(no, null);
//                        RequestQueue queue = Volley.newRequestQueue( requireContext() );
//                        queue.add(deleteBoardRequest);
//                        dialog.dismiss(); // 다이얼로그 닫기
//                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                        if (fragmentManager.getBackStackEntryCount() > 0) {
//                            fragmentManager.popBackStack();
//                        }
//                    }
//                });
//                // 다이얼로그 생성 및 표시
//                AlertDialog dialog = builder.create();
//                dialog.show();
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
