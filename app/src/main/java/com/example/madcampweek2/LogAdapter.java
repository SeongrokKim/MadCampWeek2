package com.example.madcampweek2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {
    private ArrayList<String> titleArrayList;
    private ArrayList<String> countArrayList;
    private ArrayList<String> timeArrayList;
    private ArrayList<String> categoryArrayList;

    public LogAdapter(ArrayList<String> titleArrayList, ArrayList<String> countArrayList, ArrayList<String> timeArrayList, ArrayList<String> categoryArrayList){
        this.titleArrayList = titleArrayList;
        this.countArrayList = countArrayList;
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
        String title = titleArrayList.get(position);
        String count = countArrayList.get(position);
        String category = categoryArrayList.get(position);
        String time = timeArrayList.get(position);
        holder.title.setText(title);
        holder.count.setText(String.valueOf(count));
        holder.time.setText(time);
        holder.category.setText(category);

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
