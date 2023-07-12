package com.example.madcampweek2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.madcampweek2.databinding.FragmentFragment1Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.SearchView;

public class Fragment1 extends Fragment implements SearchView.OnQueryTextListener {

//    private Fragment1ViewModel mViewModel;

    private String UID;

    public Fragment1(String uid) {
        // Required empty public constructor
        this.UID=uid;
    }

    private RecyclerView recyclerView;
    private androidx.appcompat.widget.SearchView searchView;
    private ImageView add_btn;
    private MyAdapter adapter;
    private List<String> noList;
    private List<String> uidList;
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
        searchView = rootView.findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 데이터 리스트 초기화
        titleList = new ArrayList<>();
        nameList = new ArrayList<>();
        datetimeList = new ArrayList<>();
        textList = new ArrayList<>();
        uidList = new ArrayList<>(); //로그인한 유저 말고 글쓴 유저 uid 리스트
        noList = new ArrayList<>();

        // UID 가져오기
        Bundle bundle = getArguments();
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("uid", UID);
                Fragment1_add fragment1_add = new Fragment1_add();
                fragment1_add.setArguments(bundle1);

                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment1_add);
                fragmentTransaction.addToBackStack(null);
//                recyclerView.setVisibility(View.INVISIBLE);
//                add_btn.setVisibility(View.INVISIBLE);
//                searchView.setVisibility(View.INVISIBLE);
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
                        String no = String.valueOf(dataObject.getInt("no"));
                        String uid = dataObject.getString("uid");
                        String name = dataObject.getString("name"); // "name"은 배열의 각 객체에서 정의한 키
                        String datetime = dataObject.getString("datetime");
                        String title = dataObject.getString("title");
                        String text = dataObject.getString("text");

                        datetime = datetime.substring(0, datetime.length() - 5);

                        // 데이터 추가
                        uidList.add(uid);
                        titleList.add(title);
                        nameList.add(name);
                        datetimeList.add(datetime.replaceAll("[^0-9:-]", " "));
                        textList.add(text);
                        noList.add(no);
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
        adapter = new MyAdapter(uidList, titleList, nameList, datetimeList, textList, noList);
        recyclerView.setAdapter(adapter);

        SearchView searchView = rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        return rootView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // 입력된 텍스트를 사용하여 데이터를 필터링
        List<String> filteredUidList = new ArrayList<>();
        List<String> filteredTitleList = new ArrayList<>();
        List<String> filteredNameList = new ArrayList<>();
        List<String> filteredDatetimeList = new ArrayList<>();
        List<String> filteredTextList = new ArrayList<>();
        List<String> filteredNoList = new ArrayList<>();

        for (int i = 0; i < titleList.size(); i++) {
            String title = titleList.get(i);
            String text = textList.get(i);
            String name = nameList.get(i);
            Boolean check = title.toLowerCase().contains(newText.toLowerCase()) || text.toLowerCase().contains(newText.toLowerCase());
            if (check||name.toLowerCase().contains(newText.toLowerCase())) {
                filteredUidList.add(uidList.get(i));
                filteredTitleList.add(title);
                filteredNameList.add(name);
                filteredDatetimeList.add(datetimeList.get(i));
                filteredTextList.add(text);
                filteredNoList.add(noList.get(i));
            }
        }
        adapter.setData(filteredUidList, filteredTitleList, filteredNameList, filteredDatetimeList, filteredTextList, filteredNoList);
        adapter.notifyDataSetChanged();
        return true;
    }

    private List<String> filterData(List<String> dataList, String query) {
        List<String> filteredList = new ArrayList<>();
        for (String item : dataList) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    // RecyclerView 어댑터 클래스
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> uid;
        private List<String> title;
        private List<String> name;
        private List<String> datetime;
        private List<String> text;
        private List<String> no;

        public MyAdapter(List<String> uid, List<String> title, List<String> name, List<String> datetime, List<String> text, List<String> no) {
            this.uid = uid;
            this.title = title;
            this.name = name;
            this.datetime = datetime;
            this.text = text;
            this.no = no;
        }
        public void setData(List<String> uid, List<String> title, List<String> name, List<String> datetime, List<String> text, List<String> no) {
            this.uid = uid;
            this.title = title;
            this.name = name;
            this.datetime = datetime;
            this.text = text;
            this.no = no;
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
                String itemUid = uid.get(position);
                String itemTitle = title.get(position);
                String itemName = name.get(position);
                String itemDatetime = datetime.get(position);
                String itemText = text.get(position);
                String itemNo = no.get(position);

                Fragment1_detail fragment = new Fragment1_detail();
                Bundle bundle = new Bundle();
                bundle.putString("no", itemNo);
                bundle.putString("my_uid", UID);
                bundle.putString("uid", itemUid);
                bundle.putString("title", itemTitle);
                bundle.putString("name", itemName);
                bundle.putString("datetime", itemDatetime);
                bundle.putString("text", itemText);
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);

//                recyclerView.setVisibility(View.INVISIBLE);
//                add_btn.setVisibility(View.INVISIBLE);
//                searchView.setVisibility(View.INVISIBLE);
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