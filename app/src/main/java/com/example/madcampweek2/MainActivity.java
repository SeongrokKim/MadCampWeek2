package com.example.madcampweek2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;

import com.example.madcampweek2.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.sdk.user.UserApiClient;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {
    private Button btnLogout;
    private ImageView profile;
    private TextView nickname;
    private DrawerLayout drawerLayout;
    private ImageView btnOpenPanel;
    private TextView titleText;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private ViewPager pager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem prevMenuItem;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        titleText = findViewById(R.id.titleText);

        pager = findViewById(R.id.pager);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(3);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tab2);
        titleText.setText("홈");
        pager.setCurrentItem(1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.tab1){
                    titleText.setText("게시판");
                    pager.setCurrentItem(0);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                } else if (item.getItemId() == R.id.tab2) {
                    titleText.setText("홈");
                    pager.setCurrentItem(1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                } else if (item.getItemId() == R.id.tab3) {
                    titleText.setText("설정");
                    pager.setCurrentItem(1);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
                }
                return true;
            }
        });

        // 뷰페이저 페이지 변경 시 바텀 네비게이션뷰 아이템 변경
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                changeTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        profile = findViewById(R.id.profile);
        nickname = findViewById(R.id.nickname);

        drawerLayout = findViewById(R.id.drawer_layout);

        btnOpenPanel = findViewById(R.id.btn_open_panel);
        btnOpenPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

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
    private void changeTitle(int pos){
        if(pos==0){
            titleText.setText("게시판");
        } else if (pos==1) {
            titleText.setText("홈");
        } else {
            titleText.setText("설정");
        }
    };

}

