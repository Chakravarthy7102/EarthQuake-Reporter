package com.example.earthquakereporter;

public class DataModel {
    private String place;
    private String date;
    private String time;
    private String magnitude;
    private String felt;

    public DataModel(String place, String date, String magnitude,String time,String felt) {
        this.place = place;
        this.date = date;
        this.magnitude = magnitude;
        this.time=time;
        this.felt=felt;
    }

    public String getFelt() {
        return felt;
    }

    public void setFelt(String felt) {
        this.felt = felt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }
}
