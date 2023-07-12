package com.example.madcampweek2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment1_detail extends Fragment {


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
    RecyclerView commentRecyclerView;
    TextView titleView;
    TextView nameView;
    TextView datetimeView;
    TextView textView, write_btn;
    ImageView menuView;
    List<String> uidList, datetimeList, contentList, nameList;
    EditText writeView;
    MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1_detail, container, false);
        Bundle bundle = getArguments();
        String newtext = bundle.getString("content");
        String no = bundle.getString("no");
        String my_uid = bundle.getString("my_uid"); //유저 uid
        String uid = bundle.getString("uid"); //글쓴이 uid
        String title = bundle.getString("title");
        String name = bundle.getString("name");
        String datetime = bundle.getString("datetime");
        String text = bundle.getString("text");

        titleView = view.findViewById(R.id.titleView);
        titleView.setText(title);
        nameView = view.findViewById(R.id.nameView);
        nameView.setText(name);
        datetimeView = view.findViewById(R.id.datetimeView);
        datetimeView.setText(datetime);
        textView = view.findViewById(R.id.textView);
        commentRecyclerView = view.findViewById(R.id.commentRecyclerView);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        writeView = view.findViewById(R.id.writeView);
        write_btn = view.findViewById(R.id.write_btn);

        uidList = new ArrayList<>();
        nameList = new ArrayList<>();
        datetimeList = new ArrayList<>();
        contentList = new ArrayList<>();

        if(text == "null"){
            text = "";
        }
        if(newtext != null){
            textView.setText(newtext);
        }
        else{
            textView.setText(text);
        }
        menuView = view.findViewById(R.id.menuView);


        if(!my_uid.equals(uid)){
            menuView.setVisibility(View.INVISIBLE);
        }
        String finalText = text;

        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = writeView.getText().toString();
                uidList.add(no);
                nameList.add(name);
                datetimeList.add(datetime);
                contentList.add(content);
                WriteCommentRequest writeCommentRequest = new WriteCommentRequest(no, my_uid, content, null);
                RequestQueue queue = Volley.newRequestQueue( requireContext() );
                queue.add(writeCommentRequest);
                writeView.setText("");
                adapter.notifyDataSetChanged();
            }
        });


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
                            Bundle bundle = new Bundle();
                            bundle.putString("no", no);
                            bundle.putString("my_uid", my_uid);
                            bundle.putString("uid", uid);
                            bundle.putString("text", finalText);
                            bundle.putString("title", title);
                            bundle.putString("name", name);
                            bundle.putString("datetime", datetime);
                            Fragment1_edit fragment1_edit = new Fragment1_edit();
                            fragment1_edit.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.container, fragment1_edit);
//                            fragmentTransaction.addToBackStack("detail");
                            fragmentTransaction.commit();

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
                                    DeleteBoardRequest deleteBoardRequest = new DeleteBoardRequest(no, null);
                                    RequestQueue queue = Volley.newRequestQueue( requireContext() );
                                    queue.add(deleteBoardRequest);
                                    dialog.dismiss(); // 다이얼로그 닫기
                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                    if (fragmentManager.getBackStackEntryCount() > 0) {
                                        fragmentManager.popBackStack();
                                    }
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

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject( response );
                    Boolean success = jsonObject.getBoolean("success");

                    if(!success){
                        Toast.makeText(requireContext(), "댓글 없음", Toast.LENGTH_SHORT);
                        return;
                    }
                    JSONArray result = jsonObject.getJSONArray("result");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject dataObject = result.getJSONObject(i);
                        String uid = dataObject.getString("writerUID");
                        String name = dataObject.getString("name"); // "name"은 배열의 각 객체에서 정의한 키
                        String datetime = dataObject.getString("datetime");
                        String content = dataObject.getString("content");
                        datetime = datetime.substring(0, datetime.length() - 5);

                        // 데이터 추가
                        uidList.add(uid);
                        datetimeList.add(datetime.replaceAll("[^0-9:-]", " "));
                        contentList.add(content);
                        nameList.add(name);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        CommentRequest commentRequest = new CommentRequest(no, responseListener);
        RequestQueue queue = Volley.newRequestQueue( requireContext() );
        queue.add(commentRequest);

        adapter = new Fragment1_detail.MyAdapter(uidList, datetimeList, nameList, contentList);
        commentRecyclerView.setAdapter(adapter);

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<Fragment1_detail.MyAdapter.ViewHolder> {
        private List<String> uid;
        private List<String> datetime;
        private List<String> name;
        private List<String> content;

        public MyAdapter(List<String> uid, List<String> datetime, List<String> name, List<String> content) {
            this.uid = uid;
            this.datetime = datetime;
            this.name = name;
            this.content = content;
        }
        public void setData(List<String> uid, List<String> datetime, List<String> name, List<String> content) {
            this.uid = uid;
            this.datetime = datetime;
            this.name = name;
            this.content = content;
        }

        @Override
        public Fragment1_detail.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_comment, parent, false);
            return new Fragment1_detail.MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(Fragment1_detail.MyAdapter.ViewHolder holder, int position) {
            String itemContent = content.get(position);
            String itemName = name.get(position);
            String itemDatetime = datetime.get(position);
            holder.bind(itemContent, itemName, itemDatetime);
        }

        @Override
        public int getItemCount() {
            return name.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView contentTextView;
            private TextView nameTextView;
            private TextView datetimeTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                contentTextView = itemView.findViewById(R.id.content);
                nameTextView = itemView.findViewById(R.id.name);
                datetimeTextView = itemView.findViewById(R.id.datetime);
            }

            public void bind(String itemContent, String itemName, String itemDatetime) {
                contentTextView.setText(itemContent);
                nameTextView.setText(itemName);
                datetimeTextView.setText(itemDatetime);
            }
        }
    }
}