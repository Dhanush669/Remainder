package com.devgd.calanderapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Adapter extends  RecyclerView.Adapter<Adapter.NoteHolder> {
    private List<event> allevent = new ArrayList<>();
    private onItemClickListner listner;
    Context context;
    Calendar calendar;
    public Adapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eventlayout, parent, false);
        return new NoteHolder(itemView);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        event event = allevent.get(position);
        calendar=Calendar.getInstance();
        calendar.set(event.getYear(),event.getMonth(),event.getDay());

        //Toast.makeText(context, "check", Toast.LENGTH_SHORT).show();

        holder.event.setText(event.getEvent());
        holder.category.setText(event.getCategory());

        holder.date.setText(String.valueOf(event.getDay()));

        String priority = event.getPriority();
        switch (priority){
            case "high":
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.high
                ));
                break;
            case "medium":

                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.medium));
                break;
            case "low":

                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.low));
                break;
            default:
                Log.i("tag","priority error");

        }



    }

    public void setTask(List<event> allevent) {
        this.allevent=allevent;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return allevent.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView event,category;
        private TextView date,month;
        private CardView cardView;
        public NoteHolder(View itemView) {
            super(itemView);
            event=itemView.findViewById(R.id.eventtitle);
            category=itemView.findViewById(R.id.eventcategory);

            date=itemView.findViewById(R.id.eventdate);
            month=itemView.findViewById(R.id.eventmonth);

            cardView=itemView.findViewById(R.id.eventcard);

            //  Toast.makeText(context, "holder", Toast.LENGTH_SHORT).show();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    event event = allevent.get(p);
                    listner.itemClick(event);

                }
            });
        }
    }

    public interface onItemClickListner{
        public void itemClick(event event);
    }

    public void onItemClicked(onItemClickListner listner){
        this.listner=listner;

    }
}