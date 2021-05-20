package com.devgd.calanderapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class Alarmservice {
    Context context;
    AlarmManager alarmManager;

    Alarmservice(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setalarm(Calendar c, int id, String task_desc, String task_due) {
        Intent alarm = new Intent(context, TaskReciever.class);
        alarm.putExtra("task_desc", task_desc);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, alarm, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }
}


