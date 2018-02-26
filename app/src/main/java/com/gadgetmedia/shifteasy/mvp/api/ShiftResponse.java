package com.gadgetmedia.shifteasy.mvp.api;

import com.gadgetmedia.shifteasy.mvp.data.Shift;

import java.util.List;

/**
 * POJO to hold News responses.
 */
public class ShiftResponse {

    private int id;
    private String endLatitude;
    private String startLongitude;
    private String startLatitude;
    private String start;
    private String image;
    private String endLongitude;
    private String end;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(final String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(final String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(final String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStart() {
        return start;
    }

    public void setStart(final String start) {
        this.start = start;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(final String endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(final String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "ShiftsListResponse [id = " + id + ", endLatitude = " + endLatitude + ", " +
                "startLongitude = " + startLongitude + ", startLatitude = " + startLatitude + ", " +
                "start = " + start + ", image = " + image + ", endLongitude = " + endLongitude + ", " +
                "end = " + end + "]";
    }
}
