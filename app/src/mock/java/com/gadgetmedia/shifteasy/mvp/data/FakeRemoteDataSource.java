package com.gadgetmedia.shifteasy.mvp.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.gadgetmedia.shifteasy.mvp.api.ShiftRequestData;
import com.gadgetmedia.shifteasy.mvp.data.source.ShiftsDataSource;
import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
public class FakeRemoteDataSource implements ShiftsDataSource {
    private static final Map<Integer, Shift> SHIFTS_SERVICE_DATA = new LinkedHashMap<>();
    private static final Map<Integer, Business> BUSINESS_DATA = new LinkedHashMap<>();

    @Inject
    public FakeRemoteDataSource() {
    }

    @Override
    public void getBusinessInfo(@NonNull LoadBusinessInfoCallback callback) {
        callback.onBusinessInfoLoaded(Lists.newArrayList(BUSINESS_DATA.values()));
    }

    @Override
    public void getShifts(@NonNull final LoadShiftsCallback callback) {
        callback.onShiftsLoaded(Lists.newArrayList(SHIFTS_SERVICE_DATA.values()));
    }

    @Override
    public void startShift(@NonNull ShiftRequestData shift, @NonNull ShiftStartStopCallback callback) {
        callback.onSuccess("Shift Started");
    }

    @Override
    public void endShift(@NonNull ShiftRequestData shift, @NonNull ShiftStartStopCallback callback) {
        callback.onSuccess("Shift Ended");
    }


    @Override
    public void refreshShifts() {
        SHIFTS_SERVICE_DATA.clear();
    }

    @Override
    public void refreshBusinessInfo() {
        BUSINESS_DATA.clear();
    }

    @Override
    public void deleteAllShifts() {
        SHIFTS_SERVICE_DATA.clear();
    }

    @VisibleForTesting
    public void addShifts(final Shift... shifts) {
        for (Shift shift : shifts) {
            SHIFTS_SERVICE_DATA.put(shift.getId(), shift);
        }
    }

    @VisibleForTesting
    public void addBusiness(final Business... businesses) {
        for (Business business : businesses) {
            BUSINESS_DATA.put(business.getId(), business);
        }
    }

}
