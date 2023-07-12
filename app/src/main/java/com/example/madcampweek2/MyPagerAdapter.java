package com.example.madcampweek2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private String uid;
    public MyPagerAdapter(@NonNull FragmentManager fm, String uid) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.uid = uid;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment1 fragment1 = new Fragment1(uid);
                return fragment1;
            case 1:
                Fragment2 fragment2 = new Fragment2(uid);
                return fragment2;
            case 2:
                Fragment3 fragment3 = new Fragment3(uid);
                return fragment3;
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }


    @Override
    public int getCount() {
        return 3;
    }
}

