package com.gadgetmedia.shifteasy.mvp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * REST API access points
 */
public interface ShiftsService {

    @GET("shifts")
    Call<ShiftResponse[]> getShiftsList();

    @GET("business")
    Call<BusinessResponse> getBusiness();

    @POST("shift/start")
    Call<String> startAShift();

    @POST("shift/end")
    Call<String> endAShift();

}
