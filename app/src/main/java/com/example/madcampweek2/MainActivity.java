package com.example.madcampweek2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.madcampweek2.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.sdk.user.UserApiClient;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MainActivity extends AppCompatActivity {
    private Button btnLogout;
    public ImageView profile;
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
    private ActivityMainBinding binding;
    private LinearLayout practiceView, metronomeView;
    private ImageView rankView;

    private List<String> uidList, nameList, totList, rankList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String UID = intent.getStringExtra("UID");
        String name = intent.getStringExtra("name");
        String photoUri = intent.getStringExtra("photo");
        String intro = intent.getStringExtra("intro");
        Log.d("photoUri", photoUri);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragment1 = new Fragment1(UID);
        fragment2 = new Fragment2(UID);
        fragment3 = new Fragment3(UID, name, photoUri, intro);

        titleText = findViewById(R.id.titleText);
        rankView = findViewById(R.id.rankView);




        Bundle bundle = new Bundle();
        bundle.putString("UID", UID);
        fragment2.setArguments(bundle);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tab2);
        titleText.setText("홈");

        pager = findViewById(R.id.pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), UID, name, photoUri, intro);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(3);
        pager.setCurrentItem(1);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.tab1){
                    titleText.setText("게시판");
                    pager.setCurrentItem(0);
                } else if (item.getItemId() == R.id.tab2) {
                    titleText.setText("홈");
                    pager.setCurrentItem(1);
                } else if (item.getItemId() == R.id.tab3) {
                    titleText.setText("설정");
                    pager.setCurrentItem(2);
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
        practiceView = findViewById(R.id.book);
        metronomeView = findViewById(R.id.met);

        btnOpenPanel = findViewById(R.id.btn_open_panel);
        btnOpenPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

//        Toast.makeText(getApplicationContext(), photoUri,Toast.LENGTH_SHORT).show();
        if (photoUri != null && !photoUri.equals("null")){
            Glide.with(this)
                    .load(Uri.parse(photoUri))
                    .circleCrop()
                    .into(profile);

        }else{
            Glide.with(this).load(R.drawable.init_profile).circleCrop().into(profile);
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

        rankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uidList = new ArrayList<>();
                nameList = new ArrayList<>();
                totList = new ArrayList<>();
                rankList = new ArrayList<>();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            JSONArray result = jsonObject.getJSONArray("result");

                            for (int i = 0; i < result.length(); i++) {
                                JSONObject dataObject = result.getJSONObject(i);
                                String uid = dataObject.getString("uid");
                                String name = dataObject.getString("name");
                                String total_time = dataObject.getString("total_time");
                                String rank = dataObject.getString("rank");

                                System.out.println("tine::::"+total_time);
                                // 데이터 추가
                                uidList.add(uid);
                                nameList.add(name);
//                                totList.add(datetime.replaceAll("[^0-9:-]", " "));
                                totList.add(total_time);
                                rankList.add(rank);

                            }
                            showRankListDialog(MainActivity.this, rankList, nameList, totList, UID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RankRequest rankRequest = new RankRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue( getApplicationContext() );
                queue.add(rankRequest);
            }
        });

        metronomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, MetronomeActivity.class);
                intent1.putExtra("uid", UID);
                startActivity(intent1);
            }
        });

        practiceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, PracticeActivity.class);
                intent1.putExtra("uid", UID);
                startActivity(intent1);
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

    private void showRankListDialog(Context context, List<String> ranks, List<String> names, List<String> totals, String UID) {
        // 다이얼로그 레이아웃 가져오기
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_rank_list, null);

        LinearLayout rankContainer = dialogView.findViewById(R.id.rank_container);

        for (int i = 0; i < ranks.size(); i++) {
            String rank = ranks.get(i);
            String name = names.get(i);
            String total = totals.get(i);

            View rankItemView = inflater.inflate(R.layout.dialog_rank_list, rankContainer, false);

            TextView rankTextView = rankItemView.findViewById(R.id.rank_text_view);
            TextView nameTextView = rankItemView.findViewById(R.id.name_text_view);
            TextView totalTextView = rankItemView.findViewById(R.id.total_text_view);

            rankTextView.setText(rank);
            nameTextView.setText(name);
            totalTextView.setText(total);

            // 현재 사용자의 UID와 랭킹 항목의 UID 비교하여 테두리 스타일 설정
            if (UID.equals(uidList.get(i))) {
                rankItemView.setBackgroundResource(R.drawable.border_red);
            }

            rankContainer.addView(rankItemView);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("랭킹 리스트")
                .setView(dialogView)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

