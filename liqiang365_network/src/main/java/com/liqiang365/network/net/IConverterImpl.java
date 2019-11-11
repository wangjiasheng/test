package com.liqiang365.network.net;

import com.liqiang365.network.bean.IData;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class IConverterImpl<T> implements Converter<ResponseBody, IData<T>> {
    @Override
    public IData convert(ResponseBody value) throws IOException {
        IData<T> data=new IData<T>();
        data.setResult("result:"+value.string());
        return data;
    }
}
