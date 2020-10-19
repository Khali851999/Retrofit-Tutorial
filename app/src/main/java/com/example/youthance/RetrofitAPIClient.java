package com.example.youthance;

import com.example.youthance.Networking.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIClient {

    public static final String BASE_URL = "https://www.google.com";

    private static RetrofitAPIClient retrofitAPIClient;
    private static Retrofit retrofit;

    private RetrofitAPIClient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitAPIClient getInstance(){
        if(retrofitAPIClient == null){
             retrofitAPIClient = new RetrofitAPIClient();

        }
        return retrofitAPIClient;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
