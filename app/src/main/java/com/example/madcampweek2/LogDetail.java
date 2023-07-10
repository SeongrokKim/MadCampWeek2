package com.example.madcampweek2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LogDetail extends AppCompatActivity {
    TextView date, category, title, count, time, journal;

    String dt,ct,ti,cnt,tm,jn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);

        date = findViewById(R.id.date);
        category = findViewById(R.id.category);
        title = findViewById(R.id.title);
        count = findViewById(R.id.count);
        time = findViewById(R.id.time);
        journal = findViewById(R.id.journal);

        Intent intent = getIntent();
        dt = intent.getExtras().getString("date");
        ct = intent.getExtras().getString("category");
        ti = intent.getExtras().getString("title");
        cnt = intent.getExtras().getString("count");
        tm = intent.getExtras().getString("time");
        jn = intent.getExtras().getString("journal");
        Log.d("dt",dt);
        Log.d("ct",ct);
        Log.d("ti",ti);
        Log.d("cnt",cnt);
        Log.d("tm",tm);
        Log.d("jn",jn);

        if (dt.equals("null")){
            date.setText("");
        } else{
            date.setText(dt);
        }
        if (ct.equals("null")){
            category.setText("");
        } else{
            category.setText(ct);
        }
        if (ti.equals("null")){
            title.setText("");
        } else{
            title.setText(ti);
        }
        if (cnt.equals("null")){
            count.setText("");
        } else{
            count.setText(cnt);
        }
        if (tm.equals("null")){
            time.setText("");
        } else{
            time.setText(tm);
        }
        if (jn.equals("null")){
            journal.setText("");
        } else{
            journal.setText(jn);
        }

    }
}