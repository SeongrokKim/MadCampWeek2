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
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    ArrayList<LocalDate> dayList;
    public CalendarAdapter(ArrayList<LocalDate> dayList){
        this.dayList = dayList;
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

        if ((position + 1) % 7 == 0){
            holder.dayText.setTextColor(Color.BLUE);
        } else if (position == 0 || position % 7 == 0) {
            holder.dayText.setTextColor(Color.RED);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = day.getYear();
                int month = day.getMonthValue();
                int il = day.getDayOfMonth();

                String yearMonIl = year + "년" + month + "월" + il + "일";
                Toast.makeText(holder.itemView.getContext(), yearMonIl, Toast.LENGTH_SHORT).show();
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
