package com.example.medrem;

public class Medicine {
    private String name;
    private String dose;
    private DoseType doseType;
    private String date;
    private String time;

    public Medicine(String name, String dose, DoseType doseType, String date, String time) {
        this.name = name;
        this.dose = dose;
        this.doseType = doseType;
        this.date = date;
        this.time = time;
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
}
