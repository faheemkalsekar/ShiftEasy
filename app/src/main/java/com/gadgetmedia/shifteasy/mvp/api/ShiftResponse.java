package com.gadgetmedia.shifteasy.mvp.api;

import com.google.gson.annotations.SerializedName;

/**
 * POJO to hold News responses.
 */
public class ShiftResponse {

    private int id;
    private String start;
    private String end;
    @SerializedName("startLatitude")
    private String startLatitude;
    @SerializedName("startLongitude")
    private String startLongitude;
    @SerializedName("endLatitude")
    private String endLatitude;
    @SerializedName("endLongitude")
    private String endLongitude;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ShiftsListResponse [id = " + id + ", endLatitude = " + endLatitude + ", " +
                "startLongitude = " + startLongitude + ", startLatitude = " + startLatitude + ", " +
                "start = " + start + ", image = " + image + ", endLongitude = " + endLongitude + ", " +
                "end = " + end + "]";
    }
}
