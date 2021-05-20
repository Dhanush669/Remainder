package com.devgd.calanderapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class event {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String event;
    private  String priority,category;

    private String ring;
    private int year,month,day;

    public event(String event, String priority, String category, String ring, int year, int month, int day) {


        this.event = event;
        this.priority = priority;
        this.category = category;
        this.ring = ring;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRing() {
        return ring;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
