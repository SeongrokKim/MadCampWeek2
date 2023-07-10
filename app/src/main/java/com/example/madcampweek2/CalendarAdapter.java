package com.example.madcampweek2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    ArrayList<LocalDate> dayList;
    Fragment2 fragment2;
    int selectedPosition = -1;
    public CalendarAdapter(ArrayList<LocalDate> dayList, Fragment2 fragment2){
        this.dayList = dayList;
        this.fragment2 = fragment2;
    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent,  false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position){
        LocalDate day = dayList.get(position);
        if(day == null){
            holder.dayText.setText("");
        }else{
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));
            if (day.getMonthValue() == CalendarUtil.today.getMonthValue()){
                if (day.getDayOfMonth() == CalendarUtil.today.getDayOfMonth()){
                    holder.parentView.setBackground(new ColorDrawable(Color.LTGRAY));
                }
            }
            if (isPublicHoliday(day)){
                holder.dayText.setTextColor(Color.RED);
            }

        }

        if (position == selectedPosition) {
            holder.itemView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.edge));
        } else {
            holder.itemView.setForeground(null);
        }

        if ((position + 1) % 7 == 0){
            holder.dayText.setTextColor(Color.BLUE);
        } else if (position == 0 || position % 7 == 0) {
            holder.dayText.setTextColor(Color.RED);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int previousPosition = selectedPosition;
                selectedPosition = position;

                if (previousPosition != -1) {
                    notifyItemChanged(previousPosition);
                }
                notifyItemChanged(selectedPosition);

                int year = day.getYear();
                String clickedDate = year + "-";
                int month = day.getMonthValue();
                int il = day.getDayOfMonth();
                if (month<10){
                    clickedDate += "0"+month;
                }else{
                    clickedDate += String.valueOf(month);
                }
                clickedDate += "-";
                if (il<10){
                    clickedDate += "0"+il;
                }else{
                    clickedDate += String.valueOf(il);
                }

                ArrayList<String> newDateList = new ArrayList<String>();
                ArrayList<String> newTitleList = new ArrayList<String>();
                ArrayList<String> newTimeList = new ArrayList<String>();
                ArrayList<String> newCountList = new ArrayList<String>();
                ArrayList<String> newJournalList = new ArrayList<String>();
                ArrayList<String> newCategoryList = new ArrayList<String>();


                if (fragment2.dateList.contains(clickedDate)) {
                    for (int i =0; i<fragment2.dateList.size(); i++){
                        if (fragment2.dateList.get(i).equals(clickedDate)){
                            newDateList.add(fragment2.dateList.get(i));
                            newTitleList.add(fragment2.titleList.get(i));
                            newCountList.add(fragment2.countList.get(i));
                            newJournalList.add(fragment2.journalList.get(i));
                            newTimeList.add(fragment2.timeList.get(i));
                            newCategoryList.add(fragment2.categoryList.get(i));
                        }
                    }
                }
                LogAdapter logAdapter = new LogAdapter(newDateList,newTitleList,newCountList,newJournalList,newTimeList,newCategoryList);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(fragment2.getContext());
                fragment2.recyclerViewLog.setLayoutManager(manager);
                fragment2.recyclerViewLog.setAdapter(logAdapter);
            }
        });
    }

    @Override
    public  int getItemCount(){
        return dayList.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder{
        TextView dayText;
        View parentView;
        public CalendarViewHolder(@NonNull View itemView){
            super(itemView);
            dayText = itemView.findViewById(R.id.dayText);
            parentView = itemView.findViewById(R.id.parentView);
        }
    }

    private boolean isPublicHoliday(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        return ((month == 1 && day == 1)||(month == 3 && day == 1)||(month == 5 && day == 5)||(month == 6 && day == 6)||(month == 8 && day == 15)||(month == 10 && day == 3)||(month == 10 && day == 9)||(month == 12 && day == 25));
    }

}
