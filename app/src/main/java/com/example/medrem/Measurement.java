package com.example.medrem;

import java.io.Serializable;

public class Measurement implements Serializable {
    private String name;
    private String date;
    private String time;

    public Measurement(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Measurement(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
