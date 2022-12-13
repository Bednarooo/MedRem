package com.example.medrem;

public class Measurement {
    private String name;
    private String value;
    private String date;
    private String time;

    public Measurement(String name, String value, String date, String time) {
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
