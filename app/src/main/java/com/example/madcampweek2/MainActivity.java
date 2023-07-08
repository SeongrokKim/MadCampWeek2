package com.example.madcampweek2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.bumptech.glide.Glide;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    private Button btnLogout;
    private ImageView profile;
    private TextView nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile = findViewById(R.id.profile);
        nickname = findViewById(R.id.nickname);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Uri photoUri = intent.getParcelableExtra("photoUri");
        //Toast.makeText(getApplicationContext(),intent.getStringExtra("photoUri"),Toast.LENGTH_SHORT).show();
        if (photoUri != null) {
            Glide.with(profile).load(photoUri).circleCrop().into(profile);
        } else {
            Glide.with(profile).load(R.drawable.init_profile).circleCrop().into(profile);
        }
        if (name != null){
            nickname.setText(name);
        }


        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 카카오톡으로 로그인한 경우 로그아웃 처리
                profile.setImageBitmap(null);
                nickname.setText(null);
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)) {
                    UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                        @Override
                        public Unit invoke(Throwable throwable) {
                            // 로그아웃 성공 시 LoginActivity로 이동
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            return null;
                        }
                    });
                }
                // 구글로 로그인한 경우 로그아웃 처리
                else {
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(MainActivity.this,
                            GoogleSignInOptions.DEFAULT_SIGN_IN);
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // 로그아웃 성공 시 LoginActivity로 이동
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });

    }


}
