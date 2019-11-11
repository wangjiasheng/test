package com.liqiang365.network.net;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class HTTP {
    /**
     * 网络超时时间
     */
    public static <T> T httpHande(Class<T> services){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(IConverterFactory.create())
                .baseUrl("http://www.baidu.com")
                .client(HTTPClient.getInstance())
                .build()
                .create(services);
    }
}