package com.example.test;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

public class Test3<String> implements ObservableTransformer {

    @Override
    public ObservableSource apply(Observable upstream) {
        return upstream.observeOn(AndroidSchedulers.mainThread()).map(new Function<String,Integer>() {
            @Override
            public Integer apply(String o) throws Exception {
                Log.i("wjs_wjs","hideProgressBar_"+Thread.currentThread().getName());
                return 1;
            }
        });
    }
}