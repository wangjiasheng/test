package com.liqiang365.network.test;

import com.liqiang365.network.bean.IData;
import com.liqiang365.network.net.HTTP;
import com.liqiang365.network.intel.IHTTP;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Test {
    public static void main(String[] args){
        IHTTP ihttp= HTTP.httpHande(IHTTP.class);
        Observer<IData<String>> observer_1=new Observer<IData<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(IData<String> o) {
                //System.out.println(o.getResult());
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        };
        ihttp.request("http://malltest.liqiang365.com/mall/api/mall/v1/goodscar/goods").subscribe(observer_1);
    }
}
