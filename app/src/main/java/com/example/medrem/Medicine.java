package com.example.medrem;

import java.io.Serializable;

public class Medicine implements Serializable {
    private String medicineId;
    private String name;
    private String dose;
    private DoseType doseType;
    private String date;
    private String time;
    private String username;
    private boolean clicked;

    public Medicine(String medicineId, String name, String dose, DoseType doseType, String date, String time, String username) {
        this.medicineId = medicineId;
        this.name = name;
        this.dose = dose;
        this.doseType = doseType;
        this.date = date;
        this.time = time;
        this.username = username;
        clicked = false;
    }


    public String getMedicineId() {
        return medicineId;
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

    public Medicine() {
        clicked = false;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public DoseType getDoseType() {
        return doseType;
    }

    public void setDoseType(DoseType doseType) {
        this.doseType = doseType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
