package com.example.madcampweek2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {
    private ArrayList<String> dateArrayList;
    private ArrayList<String> titleArrayList;
    private ArrayList<String> countArrayList;
    private ArrayList<String> journalArrayList;
    private ArrayList<String> timeArrayList;
    private ArrayList<String> categoryArrayList;

    public LogAdapter(ArrayList<String> dateArrayList ,ArrayList<String> titleArrayList, ArrayList<String> countArrayList, ArrayList<String> journalArrayList, ArrayList<String> timeArrayList, ArrayList<String> categoryArrayList){
        this.dateArrayList = dateArrayList;
        this.titleArrayList = titleArrayList;
        this.countArrayList = countArrayList;
        this.journalArrayList = journalArrayList;
        this.timeArrayList = timeArrayList;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.log_cell, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        String date = dateArrayList.get(position);
        String title = titleArrayList.get(position);
        String count = countArrayList.get(position);
        String journal = journalArrayList.get(position);
        String category = categoryArrayList.get(position);
        String time = timeArrayList.get(position);
        if (title != null && title != "null"){
            holder.title.setText(title);
        }
        if (count != null && count != "null"){
            holder.count.setText(String.valueOf(count));
        }
        if (time != null && time != "null"){
            holder.time.setText(time);
        }
        if (category != null && category != "null"){
            holder.category.setText(category);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent detailActivity = new Intent(context, LogDetail.class);
                detailActivity.putExtra("date", date);
                detailActivity.putExtra("category", category);
                detailActivity.putExtra("title", title);
                detailActivity.putExtra("count", count);
                detailActivity.putExtra("time", time);
                detailActivity.putExtra("journal", journal);
                context.startActivity(detailActivity);

            }
        });

    }

    @Override
    public int getItemCount() {
        return timeArrayList.size();
    }

    public class LogViewHolder extends RecyclerView.ViewHolder{
        TextView title, count, time, category;
        public LogViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            count = itemView.findViewById(R.id.item_count);
            time = itemView.findViewById(R.id.item_time);
            category = itemView.findViewById(R.id.item_category);
        }
    }
}
