package com.example.madcampweek2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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

public class Fragment1_add extends Fragment {

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


        Bundle bundle = getArguments();
        String uid = bundle.getString("uid");
        System.out.println(uid);


        post_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("post button clicked");
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
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                Fragment1 fragment1 = (Fragment1) fragmentManager.findFragmentByTag("fragment1");
//                                fragmentTransaction.remove(fragment1);
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                if (fragmentManager.getBackStackEntryCount() > 0) {
                                    fragmentManager.popBackStack();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //서버로 Volley를 이용해서 요청
                AddSocialRequest addSocialRequest = new AddSocialRequest( uid, title, content, responseListener);
                RequestQueue queue = Volley.newRequestQueue( requireContext() );
                queue.add( addSocialRequest );
            }
        });

        return view;
    }
}