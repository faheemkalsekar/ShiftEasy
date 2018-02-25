package com.gadgetmedia.shifteasy.mvp.data.source.remote;

import android.support.annotation.NonNull;

import com.gadgetmedia.shifteasy.mvp.api.BusinessResponse;
import com.gadgetmedia.shifteasy.mvp.api.ShiftRequestData;
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

                if (body!=null) {
                    final List<Business> businessList = new ArrayList<>();

                    final Business business = new Business(body.getLogo(),body.getName());
                    businessList.add(business);
                    callback.onBusinessInfoLoaded(businessList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BusinessResponse> call,
                                  @NonNull Throwable t) {

                callback.onDataNotAvailable(t.getMessage());
            }
        });
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

    @Override
    public void refreshBusinessInfo() {

    }

    @Override
    public void deleteAllBiz() {

    }

    @Override
    public void saveBusinessList(List<Business> businessInfo) {

    }
}
