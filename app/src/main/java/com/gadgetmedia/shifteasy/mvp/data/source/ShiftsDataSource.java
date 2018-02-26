package com.gadgetmedia.shifteasy.mvp.data.source;

import android.support.annotation.NonNull;

import com.gadgetmedia.shifteasy.mvp.api.ShiftRequestData;
import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;

import java.util.List;

/**
 * Main entry point for accessing shifts data.
 * <p>
 * getShifts() and getShift() have callbacks to inform the user of network/database errors
 * or successful operations.
 */
public interface ShiftsDataSource {

    void getBusinessInfo(@NonNull final LoadBusinessInfoCallback businessInfoCallback);

    void getShifts(@NonNull final LoadShiftsCallback callback);

    void getShift(@NonNull final String shiftId, @NonNull final GetShiftCallback callback);

    void startShift(@NonNull final ShiftRequestData shift);

    void endShift(@NonNull final ShiftRequestData shift);

    void refreshShifts();

    void refreshBusinessInfo();


    interface LoadBusinessInfoCallback {
        void onBusinessInfoLoaded(final List<Business> businessInfo);

        void onDataNotAvailable(String message);
    }

    interface LoadShiftsCallback {

        void onShiftsLoaded(final List<Shift> shifts);

        void onDataNotAvailable(String errorMessage);
    }

    interface GetShiftCallback {

        void onShiftLoaded(final Shift shift);

        void onDataNotAvailable();
    }
}
