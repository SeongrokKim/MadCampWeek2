package com.example.madcampweek2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.madcampweek2.databinding.FragmentFragment2Binding;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Fragment2 extends Fragment {

    private FragmentFragment2Binding binding;
    private TextView myText;
    private LocalDate selectedDate;
    private ImageButton btnPrev;
    private ImageButton btnNext;
    private RecyclerView recyclerView;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragment2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myText = root.findViewById(R.id.myText);
        btnPrev = root.findViewById(R.id.btn_prev);
        btnNext = root.findViewById(R.id.btn_next);
        selectedDate = LocalDate.now();
        recyclerView = root.findViewById(R.id.recyclerView);
        setMonthView();

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        return root;
    }

    private  String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 yyyy");
        return date.format(formatter);
    }

    private void setMonthView(){
        myText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> dayList = daysInMonthArray(selectedDate);
        CalendarAdapter adapter = new CalendarAdapter(dayList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date){
        ArrayList<String> dayList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int lastDay = yearMonth.lengthOfMonth();
        LocalDate firstDay = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();
        for (int i = 1; i<42; i++){
            if (i<=dayOfWeek || i>lastDay+dayOfWeek){
                dayList.add("");
            }else {
                dayList.add(String.valueOf(i-dayOfWeek));
            }
        }
        return dayList;
    }

}