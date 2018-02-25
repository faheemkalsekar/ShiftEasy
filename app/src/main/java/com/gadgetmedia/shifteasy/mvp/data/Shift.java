package com.gadgetmedia.shifteasy.mvp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Immutable model class for a Shift.
 */
@Entity(tableName = "shifts")
public class Shift {
/*
"id": 1,
"start": "2018-02-25T22:21:31+11:00",
"end": "2018-02-25T22:21:31+11:00",
"startLatitude": "33.8688",
"startLongitude": "151.2093",
"endLatitude": "33.8688",
"endLongitude": "151.2093",
"image": "https://unsplash.it/500/500?random"
*/

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final int mId;

    @NonNull
    @ColumnInfo(name = "start")
    private final String mStartTime;

    @NonNull
    @ColumnInfo(name = "end")
    private final String mEndTime;

    @NonNull
    @ColumnInfo(name = "startLatitude")
    private final String mStartLatitude;

    @NonNull
    @ColumnInfo(name = "startLongitude")
    private final String mStartLongitude;

    @NonNull
    @ColumnInfo(name = "endLatitude")
    private final String mEndLatitude;

    @NonNull
    @ColumnInfo(name = "endLongitude")
    private final String mEndLongitude;

    @NonNull
    @ColumnInfo(name = "image")
    private final String mImage;


    public Shift(@NonNull final int mId, @NonNull final String mStartTime, @NonNull final String mEndTime,
                 @NonNull final String mStartLatitude, @NonNull final String mStartLongitude, @NonNull final String mEndLatitude,
                 @NonNull final String mEndLongitude, @NonNull final String mImage) {

        this.mId = mId;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mStartLatitude = mStartLatitude;
        this.mStartLongitude = mStartLongitude;
        this.mEndLatitude = mEndLatitude;
        this.mEndLongitude = mEndLongitude;
        this.mImage = mImage;
    }

    @NonNull
    public int getId() {
        return mId;
    }

    @NonNull
    public String getStartTime() {
        return mStartTime;
    }

    @NonNull
    public String getEndTime() {
        return mEndTime;
    }

    @NonNull
    public String getStartLatitude() {
        return mStartLatitude;
    }

    @NonNull
    public String getStartLongitude() {
        return mStartLongitude;
    }

    @NonNull
    public String getEndLatitude() {
        return mEndLatitude;
    }

    @NonNull
    public String getEndLongitude() {
        return mEndLongitude;
    }

    @NonNull
    public String getImage() {
        return mImage;
    }
}
