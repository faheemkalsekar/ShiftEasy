package com.gadgetmedia.shifteasy.mvp.data.source.local;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gadgetmedia.shifteasy.mvp.data.Business;

import java.util.List;

/**
 * Data Access Object for the shifts table.
 */
@Dao
public interface BusinessDao {

    /**
     * Select all shifts from the shifts table.
     *
     * @return all shifts.
     */
    @Query("SELECT * FROM BUSINESS")
    List<Business> getBusiness();

    /**
     * Insert a business in the database. If the business already exists, replace it.
     *
     * @param business the shift to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBusiness(final Business business);

    /**
     * Delete all business.
     */
    @Query("DELETE FROM BUSINESS")
    void deleteBusiness();
}
