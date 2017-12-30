package com.example.administrator.oneteam.Factory;

/**
 * Created by Administrator on 2017/12/30 0030.
 */

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory
{
    private static OkHttpClient createOkHttp() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }
    private static Retrofit createRetrofit(String paramString){
        return new Retrofit.Builder()
                .baseUrl(paramString)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttp())
                .build();
    }
    public static Retrofit getmRetrofit(String paramString) {
        return createRetrofit(paramString);
    }
}
