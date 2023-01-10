package com.example.medrem;

import java.io.Serializable;

public class Measurement implements Serializable {
    private String name;
    private String date;
    private String time;
    private String username;

    public Measurement(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Measurement(String name, String date, String time, String username) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
