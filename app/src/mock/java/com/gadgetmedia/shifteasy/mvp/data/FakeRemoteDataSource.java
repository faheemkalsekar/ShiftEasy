package com.gadgetmedia.shifteasy.mvp.data;

import android.support.annotation.NonNull;

import com.gadgetmedia.shifteasy.mvp.api.ShiftRequestData;
import com.gadgetmedia.shifteasy.mvp.data.source.ShiftsDataSource;

import javax.inject.Inject;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
public class FakeRemoteDataSource implements ShiftsDataSource {

    @Inject
    public FakeRemoteDataSource() {
    }

    @Override
    public void getBusinessInfo() {

    }

    @Override
    public void getShifts(@NonNull final LoadShiftsCallback callback) {

    }

    @Override
    public void getShift(@NonNull final String shiftId, @NonNull final GetShiftCallback callback) {

    }

    @Override
    public void startShift(@NonNull final ShiftRequestData shift) {

    }

    @Override
    public void endShift(@NonNull final ShiftRequestData shift) {

    }


    @Override
    public void refreshShifts() {

    }

    @Override
    public void deleteAllShifts() {

    }
}
