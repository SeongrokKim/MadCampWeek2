package com.example.madcampweek2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madcampweek2.databinding.FragmentFragment2Binding;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class Fragment2 extends Fragment{

    private FragmentFragment2Binding binding;
    private TextView myText;
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
        CalendarUtil.selectedDate = LocalDate.now();
        CalendarUtil.today = LocalDate.now();
        recyclerView = root.findViewById(R.id.recyclerView);
        setMonthView();

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        return root;
    }

    private  String yearMonthFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");
        return date.format(formatter);
    }

    private void setMonthView(){
        myText.setText(yearMonthFromDate(CalendarUtil.selectedDate));
        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtil.selectedDate);
        CalendarAdapter adapter = new CalendarAdapter(dayList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<LocalDate> daysInMonthArray(LocalDate date){
        ArrayList<LocalDate> dayList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int lastDay = yearMonth.lengthOfMonth();
        LocalDate firstDay = CalendarUtil.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();
        for (int i = 1; i<42; i++){
            if (i<=dayOfWeek || i>lastDay+dayOfWeek){
                dayList.add(null);
            }else {
                dayList.add(LocalDate.of(CalendarUtil.selectedDate.getYear(), CalendarUtil.selectedDate.getMonth(), i-dayOfWeek));
            }
        }
        return dayList;
    }

}
