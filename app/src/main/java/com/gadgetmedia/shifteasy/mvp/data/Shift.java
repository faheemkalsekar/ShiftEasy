package com.gadgetmedia.shifteasy.mvp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    @ColumnInfo(name = "id")
    private final int mId;

    @NonNull
    @ColumnInfo(name = "start")
    private final String mStartTime;

    @NonNull
    @ColumnInfo(name = "end")
    private final String mEndTime;

    @Nullable
    @ColumnInfo(name = "startLatitude")
    private final String mStartLatitude;

    @Nullable
    @ColumnInfo(name = "startLongitude")
    private final String mStartLongitude;

    @Nullable
    @ColumnInfo(name = "endLatitude")
    private final String mEndLatitude;

    @Nullable
    @ColumnInfo(name = "endLongitude")
    private final String mEndLongitude;

    @Nullable
    @ColumnInfo(name = "image")
    private final String mImage;


    public Shift(final int mId, @NonNull final String mStartTime, @NonNull final String mEndTime,
                 @Nullable final String mStartLatitude, @Nullable final String mStartLongitude, @Nullable final String mEndLatitude,
                 @Nullable final String mEndLongitude, @Nullable final String mImage) {

        this.mId = mId;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mStartLatitude = mStartLatitude;
        this.mStartLongitude = mStartLongitude;
        this.mEndLatitude = mEndLatitude;
        this.mEndLongitude = mEndLongitude;
        this.mImage = mImage;
    }

    public int getId() {
        return mId;
    }

    @Nullable
    public String getStartTime() {
        return mStartTime;
    }

    @Nullable
    public String getEndTime() {
        return mEndTime;
    }

    @Nullable
    public String getStartLatitude() {
        return mStartLatitude;
    }

    @Nullable
    public String getStartLongitude() {
        return mStartLongitude;
    }

    @Nullable
    public String getEndLatitude() {
        return mEndLatitude;
    }

    @Nullable
    public String getEndLongitude() {
        return mEndLongitude;
    }

    @Nullable
    public String getImage() {
        return mImage;
    }
}
