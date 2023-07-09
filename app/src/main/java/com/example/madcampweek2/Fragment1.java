package com.example.madcampweek2;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.health.SystemHealthManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.madcampweek2.databinding.FragmentFragment1Binding;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    private Fragment1ViewModel mViewModel;

    public static Fragment1 newInstance() {
        return new Fragment1();
    }

    private RecyclerView recyclerView;
    private ImageView add_btn;
    private MyAdapter adapter;
    private List<String> titleList;
    private List<String> nameList;
    private List<String> datetimeList;
    private List<String> textList;
    private FragmentFragment1Binding binding;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);

        // RecyclerView 초기화
        recyclerView = rootView.findViewById(R.id.recyclerView);
        add_btn = rootView.findViewById(R.id.add_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 데이터 리스트 초기화
        titleList = new ArrayList<>();
        nameList = new ArrayList<>();
        datetimeList = new ArrayList<>();
        textList = new ArrayList<>();

        // UID 가져오기
        Bundle bundle = getArguments();
        final String[] uid = {null};
        if(bundle != null){
            uid[0] = bundle.getString("UID");
        }

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("uid", uid[0]);
                Fragment1_add fragment1_add = new Fragment1_add();
                fragment1_add.setArguments(bundle1);

                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment1_add);
                fragmentTransaction.addToBackStack(null);
                recyclerView.setVisibility(View.INVISIBLE);
                fragmentTransaction.commit();

            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    JSONArray result = jsonObject.getJSONArray("result");
//                    String name = jsonObject.getString( "name" );
//                    String datetime = jsonObject.getString("datetime");
//                    String title = jsonObject.getString("title");
//                    String text = jsonObject.getString("text");

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject dataObject = result.getJSONObject(i);

                        String name = dataObject.getString("name"); // "name"은 배열의 각 객체에서 정의한 키
                        String datetime = dataObject.getString("datetime");
                        String title = dataObject.getString("title");
                        String text = dataObject.getString("text");

                        datetime = datetime.substring(0, datetime.length() - 5);

                        // 데이터 추가
                        titleList.add(title);
                        nameList.add(name);
                        datetimeList.add(datetime.replaceAll("[^0-9:-]", " "));
                        textList.add(text);

                    }
                    // 어댑터 갱신
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SocialTextRequest socialTextRequest = new SocialTextRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue( requireContext() );
        queue.add(socialTextRequest);

        // 어댑터 초기화 및 RecyclerView에 설정
        adapter = new MyAdapter(titleList, nameList, datetimeList, textList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    // RecyclerView 어댑터 클래스
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> title;
        private List<String> name;
        private List<String> datetime;
        private List<String> text;

        public MyAdapter(List<String> title, List<String> name, List<String> datetime, List<String> text) {
            this.title = title;
            this.name = name;
            this.datetime = datetime;
            this.text = text;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_board, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String itemTitle = title.get(position);
            String itemName = name.get(position);
            String itemDatetime = datetime.get(position);
            holder.bind(itemTitle, itemName, itemDatetime);
        }

        @Override
        public int getItemCount() {
            return title.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView titleTextView;
            private TextView nameTextView;
            private TextView datetimeTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.title);
                nameTextView = itemView.findViewById(R.id.name);
                datetimeTextView = itemView.findViewById(R.id.datetime);
                // 클릭 이벤트 리스너 설정
                itemView.setOnClickListener(this);
            }
            // 클릭 이벤트 처리
            @Override
            public void onClick(View view) {
                // 클릭한 아이템의 위치(position)을 가져옵니다.
                int position = getAdapterPosition();

                // 클릭한 아이템의 데이터를 가져옵니다.
                String itemTitle = title.get(position);
                String itemName = name.get(position);
                String itemDatetime = datetime.get(position);
                String itemText = text.get(position);

                Fragment1_detail fragment = new Fragment1_detail();
                Bundle bundle = new Bundle();
                bundle.putString("title", itemTitle);
                bundle.putString("name", itemName);
                bundle.putString("datetime", itemDatetime);
                bundle.putString("text", itemText);
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                recyclerView.setVisibility(View.INVISIBLE);
                fragmentTransaction.commit();


            }

            public void bind(String itemTitle, String itemName, String itemDatetime) {
                titleTextView.setText(itemTitle);
                nameTextView.setText(itemName);
                datetimeTextView.setText(itemDatetime);
            }
        }
    }

}