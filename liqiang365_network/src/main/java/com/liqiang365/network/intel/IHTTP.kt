package com.liqiang365.network.intel

import com.liqiang365.network.bean.IData
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.Url

interface IHTTP {
    @POST
    fun request(@Url url:String): Observable<IData<String>>
}