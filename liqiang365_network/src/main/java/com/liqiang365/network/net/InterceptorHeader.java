package com.liqiang365.network.net;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class InterceptorHeader implements Interceptor {
    private String appVersion="2.1.6";
    private String model="model";
    private String sysVersion="sysVersion";
    private String token="171dba02f64e44e19534d08155d055ba";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Request.Builder builder=request.newBuilder();
        builder.addHeader("DEVICE-ID",getMid());
        builder.addHeader("PLATFORM", "2");
        builder.addHeader("APP-VERSION", appVersion);
        builder.addHeader("UNIT-TYPE", model);
        builder.addHeader("SYS-VERSION", sysVersion);
        builder.addHeader("X-Auth-Token",token);
        builder.addHeader("X-Invite-Code", "1");
        builder.addHeader("X-Product-type","0");
        builder.addHeader("Content-Type","application/json");

        return chain.proceed(builder.build());
    }
    public String getMid(){
        return "android-16";
    }
}
