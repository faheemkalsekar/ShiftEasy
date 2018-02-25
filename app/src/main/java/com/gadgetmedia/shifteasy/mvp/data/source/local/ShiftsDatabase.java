package com.gadgetmedia.shifteasy.mvp.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;

/**
 * The Room Database that contains the Shifts table.
 */
@Database(entities = {Shift.class, Business.class}, version = 1, exportSchema = false)
public abstract class ShiftsDatabase extends RoomDatabase {

    public abstract ShiftsDao shiftsDao();
    public abstract BusinessDao businessDao();
}
