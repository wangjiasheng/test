package com.liqiang365.network.net;

import com.liqiang365.network.bean.IData;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class IConverterFactory extends Converter.Factory {

    public static IConverterFactory create() {
        return new IConverterFactory();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if(getRawType(type) == IData.class){
            return new IConverterImpl<Type>();
        }
        return new IConverterImpl<Type>();
    }
}
