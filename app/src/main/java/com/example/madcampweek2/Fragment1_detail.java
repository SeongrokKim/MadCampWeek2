package com.example.madcampweek2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.health.SystemHealthManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1_detail, container, false);
        Bundle bundle = getArguments();
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

        return view;
    }
}