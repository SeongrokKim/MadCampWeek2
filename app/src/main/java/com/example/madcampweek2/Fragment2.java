package com.example.madcampweek2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.madcampweek2.databinding.FragmentFragment2Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private RecyclerView recyclerViewLog;
    private String title;
    private String count;
    private String time;
    private ArrayList<String> dateList, titleList, timeList, countList, journalList, categoryList;
    private String uid;

    public Fragment2(String uid) {
        this.uid=uid;
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
        String dateOfYear = String.valueOf(CalendarUtil.selectedDate.getYear());
        String dateOfMonth = String.valueOf(CalendarUtil.selectedDate.getMonthValue());

        Log.d("UID",uid);

        dateList = new ArrayList<String>();
        timeList = new ArrayList<String>();
        countList = new ArrayList<String>();
        journalList = new ArrayList<String>();
        titleList = new ArrayList<String>();
        categoryList = new ArrayList<String>();
        Log.d("1111111111111111111","1111111111111111111111111");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject( response );
                    JSONArray result = jsonObject.getJSONArray("result");
                    Log.d("result",String.valueOf(result.length()));

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject dataObject = result.getJSONObject(i);

                        String date = dataObject.getString("date"); // "name"은 배열의 각 객체에서 정의한 키
                        String time = dataObject.getString("time");
                        String count = dataObject.getString("count");
                        String journal = dataObject.getString("journal");
                        String title = dataObject.getString("title");
                        String category = dataObject.getString("category");

                        if (date == null){
                            dateList.add("");
                        }else{
                            dateList.add(date);
                        }
                        if (time == null){
                            timeList.add("");
                        }else{
                            timeList.add(time);
                        }
                        if (count == null){
                            countList.add("");
                        }else{
                            countList.add(count);
                        }
                        if (journal == null){
                            journalList.add("");
                        }else{
                            journalList.add(journal);
                        }
                        if (title == null){
                            titleList.add("");
                        }else{
                            titleList.add(title);
                        }
                        if (category == null){
                            categoryList.add("");
                        }else{
                            categoryList.add(category);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Log.d("22222222222222222222","222222222222222222222");
        MonthlyRequest monthlyRequest = new MonthlyRequest(uid, dateOfYear, dateOfMonth, responseListener);
        RequestQueue queue = Volley.newRequestQueue( requireContext() );
        queue.add(monthlyRequest);


        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerViewLog = root.findViewById(R.id.recyclerViewLog);
        setLogView(titleList,countList,timeList,categoryList);
        setMonthView();

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1);
                String dateOfYear = String.valueOf(CalendarUtil.selectedDate.getYear());
                String dateOfMonth = String.valueOf(CalendarUtil.selectedDate.getMonthValue());
                String dateOfDay = String.valueOf(CalendarUtil.selectedDate.getDayOfMonth());

//                setLogView();
                setMonthView();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1);
//                setLogView();
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

    private void setLogView(ArrayList<String> titleArrayList, ArrayList<String> countArrayList, ArrayList<String> timeArrayList, ArrayList<String> categoryArrayList){
        titleList = titleArrayList;
        countList = countArrayList;
        timeList = timeArrayList;
        categoryList = categoryArrayList;

        LogAdapter logAdapter = new LogAdapter(titleList,countList,timeList,categoryList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerViewLog.setLayoutManager(manager);
        recyclerViewLog.setAdapter(logAdapter);
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
