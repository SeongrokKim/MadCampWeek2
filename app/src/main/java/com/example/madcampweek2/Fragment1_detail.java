package com.example.madcampweek2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.health.SystemHealthManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Fragment1_detail extends Fragment {


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
    TextView titleView;
    TextView nameView;
    TextView datetimeView;
    TextView textView;
    ImageView menuView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1_detail, container, false);
        Bundle bundle = getArguments();
        String my_uid = bundle.getString("my_uid"); //유저 uid
        String uid = bundle.getString("uid"); //글쓴이 uid
        String title = bundle.getString("title");
        String name = bundle.getString("name");
        String datetime = bundle.getString("datetime");
        String text = bundle.getString("text");
        titleView = view.findViewById(R.id.titleView);
        titleView.setText(title);
        System.out.println(title);
        nameView = view.findViewById(R.id.nameView);
        nameView.setText(name);
        datetimeView = view.findViewById(R.id.datetimeView);
        datetimeView.setText(datetime);
        textView = view.findViewById(R.id.textView);
        if(text == "null"){
            text = "";
        }
        textView.setText(text);
        menuView = view.findViewById(R.id.menuView);

        if(!my_uid.equals(uid)){
            menuView.setVisibility(View.INVISIBLE);
        }
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(requireContext(),view);
                requireActivity().getMenuInflater().inflate(R.menu.menu_board,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.edit_btn){
                            Toast.makeText(requireContext(), "게시글 수정", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(requireContext(), "게시글 삭제", Toast.LENGTH_SHORT).show();
                            // 다이얼로그를 생성하기 위한 빌더 객체 생성
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            // 다이얼로그 메시지 설정
                            builder.setMessage("게시글을 삭제하시겠습니까?");
                            // 취소 버튼 설정
                            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); // 다이얼로그 닫기
                                }
                            });
                            // 확인 버튼 설정
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 게시글 삭제 코드 등을 작성

                                    dialog.dismiss(); // 다이얼로그 닫기
                                }
                            });
                            // 다이얼로그 생성 및 표시
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        return view;
    }
}