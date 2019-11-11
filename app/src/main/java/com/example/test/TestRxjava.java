package com.example.test;

import android.util.Log;

import com.example.test.Test;
import com.example.test.Test1;
import com.example.test.Test2;
import com.example.test.Test3;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TestRxjava {
    public void reun(){
        Observable obj=Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onComplete();
            }
        });
        obj.compose(new Test<String>()).compose(new Test1()).compose(new Test2()).compose(new Test3()).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("wjs_wjs","onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Log.i("wjs_wjs","onNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("wjs_wjs","onError");
            }

            @Override
            public void onComplete() {
                Log.i("wjs_wjs","onComplete");
            }
        });
    }
}
