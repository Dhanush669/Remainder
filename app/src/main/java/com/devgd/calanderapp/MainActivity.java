package com.devgd.calanderapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static  Eventviewmodel taskViewModel;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int UPDATE_NOTE_REQUEST = 2;
    CompactCalendarView calendarView;
    SharedPreferences preferences;
    TextView month;
    Calendar calendar;
    RecyclerView recyclerView;
    int lisize=0;
    List<String> dates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView=findViewById(R.id.compactcalendar_view);
        calendar=Calendar.getInstance();
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        month=findViewById(R.id.month);
        final Adapter adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);


        if(preferences.getString("remainder_view",null)!=null){
            if(preferences.getString("remainder_view",null).equals("calendar_view")){
                calendarView.setVisibility(View.VISIBLE);
                month.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

            }
            else if(preferences.getString("remainder_view", null).equals("card_view")){
                recyclerView.setVisibility(View.VISIBLE);
                calendarView.setVisibility(View.GONE);
                month.setVisibility(View.GONE);
            }

        }




        taskViewModel = new ViewModelProvider(this).get(Eventviewmodel.class);

        taskViewModel.getAllevents().observe(this, new Observer<List<event>>() {
            @Override
            public void onChanged(List<event> events) {

                adapter.setTask(events);
                lisize = events.size();
                List<Event> cevents;
                cevents=new ArrayList<>();
                dates=new ArrayList<>();

                for(int i=0;i<events.size();i++){
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(events.get(i).getYear(),events.get(i).getMonth(),events.get(i).getDay());
                    String d=String.valueOf(events.get(i).getYear())+String.valueOf(events.get(i).getMonth())+String.valueOf(events.get(i).getDay());


                    dates.add(d);
                    if(events.get(i).getPriority().equals("high")){
                        Event event= null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            event = new Event(getApplicationContext().getColor(R.color.high),calendar.getTimeInMillis());
                        }
                        cevents.add(event);
                    }
                    else if(events.get(i).getPriority().equals("medium")){
                        Event event= null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            event = new Event(getApplicationContext().getColor(R.color.medium),calendar.getTimeInMillis());
                        }
                        cevents.add(event);
                    }
                    else {
                        Event event= null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            event = new Event(getApplicationContext().getColor(R.color.low),calendar.getTimeInMillis());
                        }
                        cevents.add(event);

                    }
                }
                calendarView.addEvents(cevents);
            }
        });

        adapter.onItemClicked(new Adapter.onItemClickListner() {
            @Override
            public void itemClick(event event) {

                Intent updateintent = new Intent(getApplicationContext(), Addevent.class);
                updateintent.putExtra("event", event.getEvent());
                updateintent.putExtra("date", "click to update");
                updateintent.putExtra("priority", event.getPriority());
                updateintent.putExtra("category", event.getCategory());
                updateintent.putExtra("sound", event.getRing());

                updateintent.putExtra("id", event.getId());
                updateintent.putExtra("year", event.getYear());
                updateintent.putExtra("month", event.getMonth());
                updateintent.putExtra("day", event.getDay());

                startActivityForResult(updateintent,UPDATE_NOTE_REQUEST);

            }


        });

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                //dateClicked.getYear() gives year as 121 so we convert it as 2021
                int year=(dateClicked.getYear()%100)+2000;
                String d=String.valueOf(year)+String.valueOf(dateClicked.getMonth())+
                        String.valueOf(dateClicked.getDate());


                if(dates.contains(d)){
                    int index = dates.indexOf(d);
                }
                else {
                    Intent intent = new Intent(MainActivity.this,Addevent.class);
                    startActivityForResult(intent,ADD_NOTE_REQUEST);
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });


    }

    public void openAdd(View view){
        Intent intent = new Intent(this,Addevent.class);
        startActivityForResult(intent,ADD_NOTE_REQUEST);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK){
            String event=data.getStringExtra("event");
            String priority=data.getStringExtra("priority");
            String category=data.getStringExtra("category");
            String sound=data.getStringExtra("sound");
            String date=data.getStringExtra("date");
            int year=data.getIntExtra("year",0);
            int month = data.getIntExtra("month",0);
            int day=data.getIntExtra("day",0);


            event task = new event(event,priority,category,sound,year,month,day);
            taskViewModel.insert(task);



            //getting id

            int id=lisize+1;


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 13);
            calendar.set(Calendar.MINUTE, 56);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

//
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            Alarmservice alarmservices = new Alarmservice(MainActivity.this);
            alarmservices.setalarm(calendar,id,event,date);


        }
        else if(requestCode==UPDATE_NOTE_REQUEST && resultCode==RESULT_OK){
            String event=data.getStringExtra("event");
            String priority=data.getStringExtra("priority");
            String category=data.getStringExtra("category");
            String sound=data.getStringExtra("sound");
            String date=data.getStringExtra("date");
            int year=data.getIntExtra("year",0);
            int month = data.getIntExtra("month",0);
            int day=data.getIntExtra("day",0);
            event task = new event(event,priority,category,sound,year,month,day);
            int id = data.getIntExtra("id",0);
            task.setId(id);
            taskViewModel.update(task);




        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }


    public void openSetting(MenuItem item) {
        Intent intent=new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(intent);
    }
}