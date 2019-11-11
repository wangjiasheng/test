package com.liqiang365.network.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HTTPClient {
    public static final long NET_TIME_OUT = 36000;
    private static OkHttpClient okHttpClient=new OkHttpClient()
            .newBuilder()
            .readTimeout(NET_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(NET_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(NET_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(new InterceptorHeader())
            .addInterceptor(new InterceptorLogging())
            .build();
    public static OkHttpClient getInstance(){
        return okHttpClient;
    }
}
