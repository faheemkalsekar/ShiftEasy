package com.gadgetmedia.shifteasy.mvp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Immutable model class for a Shift.
 */
@Entity(tableName = "business")
public class Business {

    /*
    {
        "name": "Deputy",
         "logo": "https://www.myob.com/au/addons/media/logos/deputy_logo_1.png"
    }
    */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @NonNull
    @ColumnInfo(name = "logo")
    private String mLogo;

    public Business(final int mId, @NonNull final String mName, @NonNull final String mLogo) {
        this.mId = mId;
        this.mName = mName;
        this.mLogo = mLogo;
    }
    @Ignore
    public Business(@NonNull final String logo, @NonNull final String name) {
        this.mName = name;
        this.mLogo = logo;
    }

    @NonNull
    public int getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getLogo() {
        return mLogo;
    }
}
