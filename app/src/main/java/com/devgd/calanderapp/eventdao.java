package com.devgd.calanderapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Calendar;
import java.util.List;

@Dao
public interface eventdao {

    @Insert
    public void insert(event event);

    @Update
    public void update(event event);

    @Delete
    public void delete(event event);

    @Query("SELECT * FROM event")
    LiveData<List<event>> getAlllist();

    @Query("SELECT * FROM event ORDER BY RANDOM() LIMIT 1")
    List<event> getRandom();







}
