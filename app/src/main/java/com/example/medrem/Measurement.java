package com.example.medrem;

import java.io.Serializable;

public class Measurement implements Serializable {
    private String measurementId;
    private String name;
    private String date;
    private String time;
    private String username;
    private boolean clicked;

    public Measurement(String measurementId, String name, String date, String time) {
        this.measurementId = measurementId;
        this.name = name;
        this.date = date;
        this.time = time;
        clicked = false;
    }

    public Measurement(String measurementId, String name, String date, String time, String username) {
        this.measurementId = measurementId;
        this.name = name;
        this.date = date;
        this.time = time;
        this.username = username;
        clicked = false;
    }

    public Measurement(){
        clicked = false;
    }

    public void setMeasurementId(String measurementId) {
        this.measurementId = measurementId;
    }

    public String getMeasurementId() {
        return measurementId;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
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
