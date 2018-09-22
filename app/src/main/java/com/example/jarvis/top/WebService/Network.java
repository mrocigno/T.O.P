package com.example.jarvis.top.WebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static String baseURL = "http://listmobile.esy.es/";

    public static Retrofit teste(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
