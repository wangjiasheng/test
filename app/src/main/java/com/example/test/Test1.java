package com.example.test;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Test1 implements ObservableTransformer {

    @Override
    public ObservableSource apply(Observable upstream) {
        return upstream.observeOn(Schedulers.newThread()).map(new Function(){
            @Override
            public Object apply(Object o) throws Exception {
                Log.i("wjs_wjs","RequestNetwork_"+Thread.currentThread().getName());
                return o;
            }
        });
    }
}