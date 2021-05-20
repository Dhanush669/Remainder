package com.devgd.calanderapp;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;

@Database(entities = {event.class},version =1)
public abstract class Eventroom extends RoomDatabase {
    private static Eventroom instance;
    public abstract eventdao taskDao();


    public static synchronized Eventroom getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),Eventroom.class,"eventRoom")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback roomcallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AddAsync(instance).execute();
        }
    };

    private static class AddAsync extends AsyncTask<Void,Void,Void> {
        eventdao taskDao;
        public AddAsync(Eventroom db)
        {
            taskDao=db.taskDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            taskDao.insert(new event("event","high","birthday","default",2000,10,21));
            return null;
        }
    }

}
