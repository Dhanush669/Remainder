package com.devgd.calanderapp;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.devgd.calanderapp.Notification_Channel.Notification_ID;



public class TaskReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id=intent.getIntExtra("priority",R.drawable.ic_launcher_background);
        int notiid=intent.getIntExtra("noti_id",1);
        String taskdesc=intent.getStringExtra("task_desc");


        Notification notification=new NotificationCompat.Builder(context,Notification_ID)
                .setContentText(taskdesc)
                .setSmallIcon(id)
                .build();
        NotificationManagerCompat compat=NotificationManagerCompat.from(context);
        compat.notify(notiid,notification);
    }
}
