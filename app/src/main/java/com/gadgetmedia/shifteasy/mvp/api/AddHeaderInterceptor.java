package com.gadgetmedia.shifteasy.mvp.api;

import com.gadgetmedia.shifteasy.mvp.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Authorization", BuildConfig.sha1);

        return chain.proceed(builder.build());
    }
}
