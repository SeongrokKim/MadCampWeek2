package com.example.madcampweek2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    String title;
    int count;
    String time;

    public LogAdapter(String title, int count, String time){
        this.title = title;
        this.count = count;
        this.time = time;
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
        holder.title.setText(title);
        holder.count.setText(String.valueOf(count));
        holder.time.setText(time);

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class LogViewHolder extends RecyclerView.ViewHolder{
        TextView title, count, time;
        public LogViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            count = itemView.findViewById(R.id.item_count);
            time = itemView.findViewById(R.id.item_time);
        }
    }
}
