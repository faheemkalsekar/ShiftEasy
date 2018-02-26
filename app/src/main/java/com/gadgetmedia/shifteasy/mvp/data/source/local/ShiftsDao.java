package com.gadgetmedia.shifteasy.mvp.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gadgetmedia.shifteasy.mvp.data.Shift;

import java.util.List;

/**
 * Data Access Object for the shifts table.
 */
@Dao
public interface ShiftsDao {

    /**
     * Select all shifts from the shifts table.
     *
     * @return all shifts.
     */
    @Query("SELECT * FROM SHIFTS")
    List<Shift> getShifts();

    /**
     * Select a shift by id.
     *
     * @param shiftID the shift id.
     * @return the shift with taskId.
     */
    @Query("SELECT * FROM SHIFTS WHERE id = :shiftID")
    Shift getShiftById(final String shiftID);

    /**
     * Insert a shift in the database. If the shift already exists, replace it.
     *
     * @param shift the shift to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShift(final List<Shift> shift);

    /**
     * Update a shift.
     *
     * @param shift shift to be updated
     * @return the number of shifts updated. This should always be 1.
     */
    @Update
    int updateShift(final Shift shift);

    /**
     * Update the complete status of a shift
     *
     * @param shiftId   id of the shift
     * @param completed status to be updated

    @Query("UPDATE shifts SET completed = :completed WHERE id = :shiftId")
    void updateCompleted(final String shiftId, final boolean completed);

     */

    /**
     * Delete a shift by id.
     *
     * @return the number of shifts deleted. This should always be 1.
     */
    @Query("DELETE FROM SHIFTS WHERE id = :shiftId")
    int deleteShiftById(final String shiftId);

    /**
     * Delete all shifts.
     */
    @Query("DELETE FROM SHIFTS")
    void deleteShifts();

    /**
     * Delete all completed shifts from the table.
     *
     * @return the number of shifts deleted.

    @Query("DELETE FROM SHIFTS WHERE completed = 1")
    int deleteCompletedShifts();
     */
}
