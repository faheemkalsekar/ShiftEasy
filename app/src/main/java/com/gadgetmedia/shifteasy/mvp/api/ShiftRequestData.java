package com.gadgetmedia.shifteasy.mvp.api;

public class ShiftRequestData {

    private String time;
    private String longitude;
    private String latitude;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "ShiftRequestData [time = " + time + ", longitude = " + longitude + ", latitude = " + latitude + "]";
    }
}

