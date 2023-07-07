package com.example.madcampweek2;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText id;
    private EditText password;
    private Button joinButton;
    private View loginKakao;
    private View loginGoogle;
    private View loginNaver;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google 로그인에 성공한 경우
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // 사용자 이름과 이미지를 가져옵니다.
                String name = account.getDisplayName();
                Uri photoUri = account.getPhotoUrl();

                // MainActivity로 이동하기 위한 Intent를 생성합니다.
                Intent intent = new Intent(this, MainActivity.class);
                // 사용자 이름과 이미지를 Intent에 추가합니다.
                intent.putExtra("name", name);
                intent.putExtra("photoUri", String.valueOf(photoUri));

                // MainActivity로 이동합니다.
                startActivity(intent);
            } catch (ApiException e) {
                // Google 로그인에 실패한 경우
                Toast.makeText(getApplicationContext(),"Google Sign-in failed", Toast.LENGTH_SHORT).show();
                // 실패 처리를 수행합니다.
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        id = findViewById(R.id.login_id);
        password = findViewById(R.id.login_password);
        joinButton = findViewById(R.id.join_button);
        loginKakao = findViewById(R.id.login_kakao);
        loginGoogle = findViewById(R.id.login_google);
        loginNaver = findViewById(R.id.login_naver);

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                //oAuthToken != null 이라면 로그인 성공
                if(oAuthToken!=null){
                    // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                    updateKakaoLoginUi();

                }else {
                    //로그인 실패
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "로그인 하는 중", Toast.LENGTH_SHORT).show();
            }
        });

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginKakao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                }else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }

        });

        loginGoogle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestProfile()
                        .build();

                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

        });

        loginNaver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }

        });



    }

    private  void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 로그인이 되어있으면
                if (user!=null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("name", user.getKakaoAccount().getProfile().getNickname());
                    intent.putExtra("photoUri", Uri.parse(user.getKakaoAccount().getProfile().getProfileImageUrl()));
                    startActivity(intent);
                }else {
                    // 로그인이 되어 있지 않다면 위와 반대로
                }
                return null;
            }
        });
    }
}