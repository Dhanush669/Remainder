package com.devgd.calanderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class Addevent extends AppCompatActivity{
    EditText eventname;
    Spinner priority,category,sound;
    int pre_due_day,pre_due_month,pre_due_year;
    TextView date;
    int id,year_intent,month_intent,day_intent;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);

        eventname=findViewById(R.id.etitle);
        priority=findViewById(R.id.spriority);
        category=findViewById(R.id.scategory);
        sound=findViewById(R.id.ssound);
        date=findViewById(R.id.date);

        //spinner for priority
        String[] prioritylist = {"high","medium","low"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.textspinnerlayout, prioritylist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        priority.setAdapter(dataAdapter);

        //spinner for category
        String[] categorylist = {"birthday","event","work","meeting","others"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.textspinnerlayout, categorylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        category.setAdapter(categoryAdapter);

        //spinner for sound
        String[] soundlist = {"default","sound1","sound2"};
        ArrayAdapter<String> soundAdapter = new ArrayAdapter<String>(this, R.layout.textspinnerlayout, soundlist);
        soundAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        sound.setAdapter(soundAdapter);


        //get intent
        Intent intent=getIntent();
        if(intent.getStringExtra("event")!=null){
            eventname.setText(intent.getStringExtra("event"));

            id=intent.getIntExtra("id",0);

            //priority
            int indexpri;
            String spriority = intent.getStringExtra("priority");
            if(spriority.equals("high")){
                indexpri=0;
            }
            else if(spriority.equals("medium")){
                indexpri=1;
            }
            else {
                indexpri=2;
            }
            priority.setSelection(indexpri);


           //category

            int indexcategory=0;
            String scategory = intent.getStringExtra("category");
            if(scategory.equals("birthday")){
                indexcategory=0;
            }
            else if(scategory.equals("event")){
                indexcategory=1;
            }

            else if (scategory.equals("work")){
                indexcategory=2;
            }
            else if (scategory.equals("meeting")){
                indexcategory=3;
            }
            else if (scategory.equals("others")){
                indexcategory=4;
            }
            category.setSelection(indexcategory);


            //date
            date.setText(intent.getStringExtra("date"));

        }

        //date
         datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.YEAR,i);

                String date1=DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                day_intent=i2;
                month_intent=i1;
                year_intent=i;
                date.setText(date1);



            }
        },Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONDAY),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

    }

    public void back(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void save(View view){
        Intent data = new Intent();
        data.putExtra("event",eventname.getText().toString());
        data.putExtra("date",date.getText());
        data.putExtra("priority",priority.getSelectedItem().toString());
        data.putExtra("category",category.getSelectedItem().toString());
        data.putExtra("sound",sound.getSelectedItem().toString());

        data.putExtra("id",id);
        data.putExtra("year",year_intent);
        data.putExtra("month",month_intent);
        data.putExtra("day",day_intent);


        setResult(RESULT_OK,data);
        finish();
    }

    public void showdate(View view){
//        DialogFragment datePicker = new DatePickerFragment();
//        datePicker.show(getSupportFragmentManager(), "date picke
        datePickerDialog.show();

    }


//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.YEAR, year);
//        c.set(Calendar.MONTH, month);
//        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
//        year_intent=year;
//        month_intent=month;
//        day_intent=dayOfMonth;
//        date.setText(currentDateString);
//
//    }
}