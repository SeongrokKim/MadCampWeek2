package com.example.madcampweek2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.health.SystemHealthManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Fragment1_edit extends Fragment {

    private EditText titleView, contentView;
    private Button post_btn;

    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1_add, container, false);
        titleView = view.findViewById(R.id.editTitle);
        contentView = view.findViewById(R.id.editText);
        post_btn = view.findViewById(R.id.post_btn);
        post_btn.setText("수 정");

        Bundle bundle = getArguments();
        String my_uid = bundle.getString("my_uid");
        String uid = bundle.getString("uid");
        String no = bundle.getString("no");
        String text = bundle.getString("text");
        String title = bundle.getString("title");
        String name = bundle.getString("name");
        String datetime = bundle.getString("datetime");

        titleView.setText(title);
        contentView.setText(text);

        post_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String title = titleView.getText().toString();
                String content = contentView.getText().toString();

                //제목 비어있으면 다시
                if (title.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    dialog = builder.setMessage("제목을 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
//                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                                if (fragmentManager.getBackStackEntryCount() > 0) {
//                                    Fragment previousFragment = fragmentManager.findFragmentByTag("detail"); // 이전 Fragment의 태그를 사용하여 Fragment를 가져옵니다.
//
//                                    if (previousFragment instanceof Fragment1_edit) {
//
//                                        previousFragment.setArguments(bundle);
//                                    }
//                                    fragmentManager.popBackStack();
//                                }
                                Bundle bundle1 = new Bundle();
                                bundle1.putString("content", content);
                                bundle1.putString("my_uid", my_uid);
                                bundle1.putString("uid", uid);
                                bundle1.putString("title", title);
                                bundle1.putString("name", name);
                                bundle1.putString("datetime", datetime);
                                Fragment1_detail fragment1_detail = new Fragment1_detail();
                                fragment1_detail.setArguments(bundle1);
                                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.container, fragment1_detail);
                                fragmentTransaction.commit();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 요청
                EditBoardRequest editBoardRequest = new EditBoardRequest(no, title, content, responseListener);
                RequestQueue queue = Volley.newRequestQueue( requireContext() );
                queue.add( editBoardRequest );
            }
        });

        return view;
    }
}
