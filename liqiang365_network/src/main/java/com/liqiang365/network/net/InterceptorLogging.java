package com.liqiang365.network.net;

import android.util.Log;

import com.liqiang365.network.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class InterceptorLogging implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (BuildConfig.DEBUG) {
            return interceptDebug(chain);
        } else {
            return chain.proceed(chain.request());
        }
    }

    public Response interceptDebug(Chain chain) throws IOException{
        Request request = chain.request();
        log(String.format("请求的url:%s", request.url()));
        log(String.format("请求方式:%s", request.method()));
        RequestBody requestBody=request.body();
        if(requestBody!=null){
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            log(String.format("请求的参数:%s", buffer.readUtf8()));
        }
        log(String.format("Token:%s", request.headers().get("X-Auth-Token")));
        Response response = chain.proceed(request);
        if(MediaType.get("application/x-msdownload").equals(response.body().contentType())){
            log("文件下载:");
        }else{
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            InputStream inputStream=null;
            InputStreamReader inputStreamReader=null;
            BufferedReader bufferedReader=null;
            try{
                inputStream = responseBody.byteStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                String value = null;
                log("响应的信息:");
                while ((value = bufferedReader.readLine()) != null) {
                    log(value);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(inputStream!=null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(inputStreamReader!=null){
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(bufferedReader!=null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }

    public void log(String str) {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.equals("Linux")) {
            Log.d("myNetworkRequest", str);
        } else {
            System.out.println(str);
        }
    }
}