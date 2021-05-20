package com.devgd.calanderapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class Eventviewmodel extends AndroidViewModel {

    private EventRepository eventRepository;
    private LiveData<List<event>> allevents;
    public Eventviewmodel(@NonNull Application application) {
        super(application);

        eventRepository = new EventRepository(application);
        allevents=eventRepository.getAllevents();
    }

    public void insert(event event){
        eventRepository.insert(event);
    }

    public void update(event event){
        eventRepository.update(event);
    }





    public void delete(event event){
        eventRepository.delete(event);
    }

    public LiveData<List<event>> getAllevents(){
        return allevents;
    }

}
