package com.example.madcampweek2;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.madcampweek2.databinding.FragmentFragment3Binding;

import org.json.JSONException;
import org.json.JSONObject;


public class Fragment3 extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private FragmentFragment3Binding binding;
    private ImageView profile;
    private TextView name;
    private String uid, userName, userPhotoUri, intro;
    private TextView changePw, changeIntro, myPost, myComment, introView;

    public Fragment3(String uid, String name, String photoUri ,String intro) {
        this.uid = uid;
        this.userName = name;
        this.userPhotoUri = photoUri;
        this.intro = intro;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragment3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profile = root.findViewById(R.id.profileView);
        name = root.findViewById(R.id.nameView);
        changePw = root.findViewById(R.id.change_pw);
        changeIntro = root.findViewById(R.id.change_introduce);
        myPost = root.findViewById(R.id.my_post);
        myComment = root.findViewById(R.id.my_comment);
        introView = root.findViewById(R.id.introView);
        Log.d("UID",uid);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    System.out.println(success);

                    if (success) {//로그인 성공시
                        intro = jsonObject.optString("intro");

                        if (intro.equals("")){
                            introView.setText("한 줄 소개가 없습니다.");
                        }
                        else{
                            introView.setText(intro);
                        }

                    } else {//로그인 실패시

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        CheckIntroRequest checkIntroRequest = new CheckIntroRequest(uid, responseListener);
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        queue.add(checkIntroRequest);


        if (userPhotoUri == null) {
            Glide.with(profile).load(R.drawable.init_profile).circleCrop().into(profile);
        } else if (userPhotoUri.equals("null")) {
            Glide.with(profile).load(R.drawable.init_profile).circleCrop().into(profile);
        } else {
            Glide.with(profile).load(userPhotoUri).circleCrop().into(profile);
        }
        if (userName != null) {
            name.setText(userName);
        }

        changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다이얼로그 레이아웃을 인플레이트합니다.
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View dialogView = inflater.inflate(R.layout.dialog_change_password, null);

                // 다이얼로그 요소들을 참조합니다.
                EditText currentPasswordEditText = dialogView.findViewById(R.id.current_password_edit_text);
                EditText newPasswordEditText = dialogView.findViewById(R.id.new_password_edit_text);
                EditText confirmPasswordEditText = dialogView.findViewById(R.id.confirm_password_edit_text);
                Button cancelButton = dialogView.findViewById(R.id.cancel_button);
                Button doneButton = dialogView.findViewById(R.id.done_button);

                // 다이얼로그 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                // 취소 버튼 클릭 이벤트 처리
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // 완료 버튼 클릭 이벤트 처리
                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 비밀번호 변경 작업 수행
                        String currentPassword = currentPasswordEditText.getText().toString();
                        String newPassword = newPasswordEditText.getText().toString();
                        String confirmPassword = confirmPasswordEditText.getText().toString();

                        if (currentPassword.equals("")) {
                            Toast.makeText(getContext(), "현재 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else if (newPassword.equals("")) {
                            Toast.makeText(getContext(), "새로운 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else if (confirmPassword.equals("")) {
                            Toast.makeText(getContext(), "비밀번호 확인을 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else if (currentPassword.equals(newPassword)) {
                            Toast.makeText(getContext(), "현재 비밀번호와 같습니다.", Toast.LENGTH_SHORT).show();
                        } else if (!newPassword.equals(confirmPassword)) {
                            Toast.makeText(getContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        System.out.println(success);

                                        if (success) {//로그인 성공시
                                            Toast.makeText(requireContext(), "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();


                                        } else {//로그인 실패시
                                            Toast.makeText(requireContext(), "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            PwChangeRequest pwChangeRequest = new PwChangeRequest(uid, currentPassword, newPassword, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(requireActivity());
                            queue.add(pwChangeRequest);
                        }

                    }
                });

                // 다이얼로그 표시
                dialog.show();
            }
        });

        changeIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다이얼로그 레이아웃을 인플레이트합니다.
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View dialogView = inflater.inflate(R.layout.dialog_change_introduce, null);

                // 다이얼로그 요소들을 참조합니다.
                EditText oneLineIntro = dialogView.findViewById(R.id.one_line_intro_edit_text);
                Button cancelButton = dialogView.findViewById(R.id.cancel_button);
                Button doneButton = dialogView.findViewById(R.id.done_button);


                // 다이얼로그 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                oneLineIntro.setText(intro);

                // 취소 버튼 클릭 이벤트 처리
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // 완료 버튼 클릭 이벤트 처리
                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 비밀번호 변경 작업 수행
                        String intro = oneLineIntro.getText().toString();
                        if (intro.equals("")){
                            intro = "한 줄 소개가 없습니다.";
                        }

                        String finalIntro = intro;
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    System.out.println(success);

                                    if (success) {//로그인 성공시
                                        introView.setText(finalIntro);
                                        dialog.dismiss();


                                    } else {//로그인 실패시

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        IntroChangeRequest introChangeRequest = new IntroChangeRequest(uid, intro, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(requireActivity());
                        queue.add(introChangeRequest);

                    }
                });

                // 다이얼로그 표시
                dialog.show();
            }
        });


        return root;
    }

}