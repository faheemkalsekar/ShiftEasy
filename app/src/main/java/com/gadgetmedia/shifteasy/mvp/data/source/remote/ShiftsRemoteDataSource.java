package com.gadgetmedia.shifteasy.mvp.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.gadgetmedia.shifteasy.mvp.api.BusinessResponse;
import com.gadgetmedia.shifteasy.mvp.api.ShiftRequestData;
import com.gadgetmedia.shifteasy.mvp.api.ShiftResponse;
import com.gadgetmedia.shifteasy.mvp.api.ShiftsService;
import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.data.source.ShiftsDataSource;
import com.gadgetmedia.shifteasy.mvp.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of the Shifts Remote Data Source.
 */
@Singleton
public class ShiftsRemoteDataSource implements ShiftsDataSource {

    private final ShiftsService mShiftsService;
    private final AppExecutors mAppExecutors;

    @Inject
    public ShiftsRemoteDataSource(@NonNull final AppExecutors executors,
                                  @NonNull final ShiftsService shiftsService) {
        mAppExecutors = executors;
        mShiftsService = shiftsService;
    }

    @Override
    public void getBusinessInfo(@NonNull final LoadBusinessInfoCallback callback) {

        mShiftsService.getBusiness().enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(@NonNull final Call<BusinessResponse> call,
                                   @NonNull final Response<BusinessResponse> response) {

                final BusinessResponse body = response.body();
                final List<Business> businessList = new ArrayList<>();

                if (body != null) {
                    final Business business = new Business(body.getLogo(), body.getName());
                    businessList.add(business);
                }

                callback.onBusinessInfoLoaded(businessList);
            }

            @Override
            public void onFailure(@NonNull Call<BusinessResponse> call,
                                  @NonNull Throwable t) {

                callback.onDataNotAvailable("Error while fetching from server");
            }
        });
    }

    @Override
    public void getShifts(@NonNull final LoadShiftsCallback callback) {

        mShiftsService.getShiftsList().enqueue(new Callback<ShiftResponse[]>() {
            @Override
            public void onResponse(@NonNull Call<ShiftResponse[]> call, @NonNull Response<ShiftResponse[]> response) {

                final ShiftResponse[] shiftResponse = response.body();
                final List<Shift> shiftList = new ArrayList<>();

                if (shiftResponse != null) {

                    for (ShiftResponse shiftRes : shiftResponse) {
                        Log.d("onResponse", shiftRes.toString());
                        final Shift shift = new Shift(shiftRes.getId(), shiftRes.getStart(), shiftRes.getEnd(),
                                shiftRes.getStartLatitude(), shiftRes.getStartLongitude(), shiftRes.getEndLatitude(),
                                shiftRes.getEndLongitude(), shiftRes.getImage());
                        shiftList.add(shift);
                    }


                }
                callback.onShiftsLoaded(shiftList);
            }

            @Override
            public void onFailure(@NonNull Call<ShiftResponse[]> call, @NonNull Throwable t) {
                callback.onDataNotAvailable("Error while fetching from server");
            }
        });
    }


    @Override
    public void startShift(@NonNull final ShiftRequestData shiftData, @NonNull final ShiftStartStopCallback callback) {
        mShiftsService.startAShift(shiftData).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                final String message = response.body();
                callback.onSuccess(message);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                callback.onFailure("Error starting shift");
            }
        });

    }

    @Override
    public void endShift(@NonNull ShiftRequestData shiftData, @NonNull final ShiftStartStopCallback callback) {
        mShiftsService.endAShift(shiftData).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                final String message = response.body();
                callback.onSuccess(message);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                callback.onFailure("Error ending shift");
            }
        });
    }

    @Override
    public void refreshShifts() {

    }

    @Override
    public void refreshBusinessInfo() {

    }

    @Override
    public void deleteAllShifts() {

    }


}
