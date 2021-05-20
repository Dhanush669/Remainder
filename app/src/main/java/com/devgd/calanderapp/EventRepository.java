package com.devgd.calanderapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {

    private static eventdao eventdao;
    public LiveData<List<event>> allevents;

    public EventRepository(Application application){
        Eventroom  eventroom = Eventroom.getInstance(application);
        eventdao= eventroom.taskDao();
        allevents = eventdao.getAlllist();

    }

    public void insert(event event){
        new insertAsync().execute(event);
    }



    public void update(event event){
        new updateAsync().execute(event);
    }



    public void delete(event event){
        new deleteAsync().execute(event);
    }



    public LiveData<List<event>> getAllevents(){
        return  allevents;
    }





    public static  class insertAsync extends AsyncTask<event,Void,Void> {


        @Override
        protected Void doInBackground(event... events) {
            eventdao.insert(events[0]);
            return null;
        }
    }





    public static  class updateAsync extends AsyncTask<event,Void,Void> {


        @Override
        protected Void doInBackground(event... events) {
            eventdao.update(events[0]);
            return null;
        }
    }

    public static  class deleteAsync extends AsyncTask<event,Void,Void> {


        @Override
        protected Void doInBackground(event... events) {
            eventdao.delete(events[0]);
            return null;
        }
    }
}
