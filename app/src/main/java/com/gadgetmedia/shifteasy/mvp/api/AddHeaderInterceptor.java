package com.gadgetmedia.shifteasy.mvp.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Authorization", "Deputy 495c6b80688fb7f08f15ed357d270cab276849b4  -");

        return chain.proceed(builder.build());
    }
}
