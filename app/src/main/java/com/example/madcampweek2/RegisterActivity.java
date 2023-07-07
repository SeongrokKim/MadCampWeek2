package com.example.madcampweek2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApi;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class RegisterActivity extends AppCompatActivity {
    private Button btnMakeAccount;
    private Button btnKakao;
    private Button btnGoogle;
    private Button btnNaver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        btnMakeAccount = findViewById(R.id.btnMakeAccount);
        btnKakao = findViewById(R.id.btnKakao);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnNaver = findViewById(R.id.btnNaver);
    }


}
