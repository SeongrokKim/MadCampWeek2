package com.example.madcampweek2;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.madcampweek2.databinding.FragmentFragment3Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment{

    private FragmentFragment3Binding binding;
    private ImageView profile;
    private TextView name;

    private String uid;

    public Fragment3(String uid) {
        // Required empty public constructor
        this.uid=uid;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragment3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profile = root.findViewById(R.id.profileView);
        name = root.findViewById(R.id.nameView);


//        nameList = new ArrayList<>();
//        datetimeList = new ArrayList<>();
//        textList = new ArrayList<>();
//        titleList = new ArrayList<>();
//
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//
//                    JSONObject jsonObject = new JSONObject( response );
//                    Boolean success = jsonObject.getBoolean("success");
//
//                    if(!success){
//                        Toast.makeText(requireContext(), "게시글 없음", Toast.LENGTH_SHORT);
//                        return;
//                    }
//                    JSONArray result = jsonObject.getJSONArray("result");
//                    for (int i = 0; i < result.length(); i++) {
//                        JSONObject dataObject = result.getJSONObject(i);
//                        String text = dataObject.getString("text");
//                        String name = dataObject.getString("name"); // "name"은 배열의 각 객체에서 정의한 키
//                        String datetime = dataObject.getString("datetime");
//                        String title = dataObject.getString("title");
//                        datetime = datetime.substring(0, datetime.length() - 5);
//
//                        // 데이터 추가
//                        textList.add(text);
//                        datetimeList.add(datetime.replaceAll("[^0-9:-]", " "));
//                        titleList.add(title);
//                        nameList.add(name);
//                    }
//                    adapter.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        MyBoardRequest myBoardRequest = new MyBoardRequest(uid, responseListener);
//        RequestQueue queue = Volley.newRequestQueue( requireContext() );
//        queue.add(myBoardRequest);
//
        return root;
    }
//
//    private class MyAdapter extends RecyclerView.Adapter<Fragment3.MyAdapter.ViewHolder> {
//        private List<String> name, text, title, datetime;
//
//        public MyAdapter(List<String> name, List<String> title, List<String> text, List<String> datetime) {
//            this.title = title;
//            this.name = name;
//            this.datetime = datetime;
//            this.text = text;
//        }
//        public void setData(List<String> name, List<String> title, List<String> text, List<String> datetime) {
//            this.title = title;
//            this.name = name;
//            this.datetime = datetime;
//            this.text = text;
//        }
//
//        @Override
//        public Fragment3.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_board, parent, false);
//            return new Fragment3.MyAdapter.ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(Fragment3.MyAdapter.ViewHolder holder, int position) {
//            String itemTitle = title.get(position);
//            String itemName = name.get(position);
//            String itemDatetime = datetime.get(position);
//            holder.bind(itemTitle, itemName, itemDatetime);
//        }
//
//        @Override
//        public int getItemCount() {
//            return title.size();
//        }
//
//
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//            private TextView titleTextView;
//            private TextView nameTextView;
//            private TextView datetimeTextView;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//                titleTextView = itemView.findViewById(R.id.title);
//                nameTextView = itemView.findViewById(R.id.name);
//                datetimeTextView = itemView.findViewById(R.id.datetime);
//                // 클릭 이벤트 리스너 설정
//                itemView.setOnClickListener(this);
//            }
//            // 클릭 이벤트 처리
//            @Override
//            public void onClick(View view) {
//                // 클릭한 아이템의 위치(position)을 가져옵니다.
//                int position = getAdapterPosition();
//
//                // 클릭한 아이템의 데이터를 가져옵니다.
//                String itemUid = uid.get(position);
//                String itemTitle = title.get(position);
//                String itemName = name.get(position);
//                String itemDatetime = datetime.get(position);
//                String itemText = text.get(position);
//                String itemNo = no.get(position);
//
//                Fragment1_detail fragment = new Fragment1_detail();
//                Bundle bundle = new Bundle();
//                bundle.putString("no", itemNo);
//                bundle.putString("my_uid", UID);
//                bundle.putString("uid", itemUid);
//                bundle.putString("title", itemTitle);
//                bundle.putString("name", itemName);
//                bundle.putString("datetime", itemDatetime);
//                bundle.putString("text", itemText);
//                fragment.setArguments(bundle);
//
//                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.addToBackStack(null);
//
////                recyclerView.setVisibility(View.INVISIBLE);
////                add_btn.setVisibility(View.INVISIBLE);
////                searchView.setVisibility(View.INVISIBLE);
//                fragmentTransaction.commit();
//            }
//
//            public void bind(String itemTitle, String itemName, String itemDatetime) {
//                titleTextView.setText(itemTitle);
//                nameTextView.setText(itemName);
//                datetimeTextView.setText(itemDatetime);
//            }
//        }
//    }

}