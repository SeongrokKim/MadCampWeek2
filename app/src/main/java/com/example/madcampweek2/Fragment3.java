package com.example.madcampweek2;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madcampweek2.databinding.FragmentFragment3Binding;


public class Fragment3 extends Fragment{

    private FragmentFragment3Binding binding;
    private ImageView profile;
    private TextView name;

    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragment3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profile = root.findViewById(R.id.profileView);
        name = root.findViewById(R.id.nameView);


        return root;
    }


}