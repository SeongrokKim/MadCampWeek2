package com.example.madcampweek2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;


import android.content.Intent;
import android.net.Uri;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;


import java.security.MessageDigest;
import android.Manifest;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText id;
    private EditText password;
    private Button joinButton;
    private View loginKakao;
    private View loginGoogle;
    private static final int RC_SIGN_IN = 123;
    private EditText login_id, login_password;
    public String accessToken;
    public String refreshToken;
    private String kakaoID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
        }



        login_id = findViewById( R.id.login_id);
        login_password = findViewById( R.id.login_password );
        loginButton = findViewById(R.id.login_button);
        id = findViewById(R.id.login_id);
        password = findViewById(R.id.login_password);
        joinButton = findViewById(R.id.join_button);
        loginKakao = findViewById(R.id.login_kakao);
        loginGoogle = findViewById(R.id.login_google);
        final String[] intro = {null};



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserId = login_id.getText().toString();
                String UserPwd = login_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );
                            intro[0] = jsonObject.getString("intro");
                            System.out.println(success);


                            if(success) {//로그인 성공시

                                String name = jsonObject.getString( "name" );
                                String UID = jsonObject.getString("uid");

                                Toast.makeText( getApplicationContext(), String.format("%s님 환영합니다.", name), Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( getApplicationContext(), MainActivity.class );

                                intent.putExtra( "name", name );
                                intent.putExtra("UID", UID);
                                intent.putExtra("intro", intro[0]);
                                intent.putExtra("photo", jsonObject.getString("photo"));

                                startActivity( intent );

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest( UserId, UserPwd, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );
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


    }

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
                String email = account.getEmail();
                final String[] uid = {null};
                final String[] intro = {null};
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            uid[0] = jsonObject.getString( "uid" );
                            intro[0] = jsonObject.optString("intro");

                            // MainActivity로 이동하기 위한 Intent를 생성합니다.
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            // 사용자 이름과 이미지를 Intent에 추가합니다.
                            intent.putExtra("name", name);
                            intent.putExtra("photoUri", String.valueOf(photoUri));
                            intent.putExtra("email", email);
                            intent.putExtra("UID", uid[0]);
                            intent.putExtra("intro", intro[0]);

                            if(uid[0] != null){
                                // MainActivity로 이동합니다.
                                startActivity(intent);
                            }
                            else System.out.println("error, null UID");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SocialRegisterRequest socialRegisterRequest = new SocialRegisterRequest( email, name, String.valueOf(photoUri), responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( socialRegisterRequest );


            } catch (ApiException e) {
                // Google 로그인에 실패한 경우

                Toast.makeText(getApplicationContext(),"구글 로그인 실패", Toast.LENGTH_SHORT).show();
                // 실패 처리를 수행합니다.
            }
        }
    }

    Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            //oAuthToken != null 이라면 로그인 성공
            if(oAuthToken!=null){
                // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                accessToken = oAuthToken.getAccessToken();
                refreshToken = oAuthToken.getRefreshToken();

                UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                    @Override
                    public Unit invoke(User user, Throwable throwable) {
                        if (user != null) {
                            // 사용자 id 출력
                            long userId = user.getId();
                            kakaoID = String.valueOf(userId);
                        }
                        return null;
                    }
                });
                updateKakaoLoginUi();

            }else {
                //로그인 실패
                if (throwable != null) {
                    String errorMessage = throwable.getMessage(); // 오류 메시지를 가져옵니다.
                    System.out.println("오류 메시지: " + errorMessage);
                }
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    };

    private  void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 로그인이 되어있으면
                if (user!=null){
                    final String[] uid = {null};
                    final String[] intro = {null};
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject( response );
                                uid[0] = jsonObject.getString( "uid" );
                                intro[0] = jsonObject.getString("intro");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("name", user.getKakaoAccount().getProfile().getNickname());
                                intent.putExtra("photo", String.valueOf(Uri.parse(user.getKakaoAccount().getProfile().getProfileImageUrl())));
                                intent.putExtra("UID", uid[0]);
                                intent.putExtra("intro", intro);

                                if(uid[0]!=null) startActivity(intent);
                                else System.out.println("ERROR: null UID");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    SocialRegisterRequest socialRegisterRequest = new SocialRegisterRequest( kakaoID, user.getKakaoAccount().getProfile().getNickname(), String.valueOf(Uri.parse(user.getKakaoAccount().getProfile().getProfileImageUrl())), responseListener );
                    RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                    queue.add( socialRegisterRequest );



                }else {
                    // 로그인이 되어 있지 않다면 위와 반대로
                }
                return null;

            }
        });
    }
}
