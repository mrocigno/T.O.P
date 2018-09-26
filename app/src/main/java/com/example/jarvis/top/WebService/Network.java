package com.example.jarvis.top.WebService;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public static TextView createNoConection(Activity activity){
        TextView txt = new TextView(activity);
        txt.setText("SEM CONEXÃO");
        txt.setTypeface(null, Typeface.BOLD);
        txt.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        txt.setGravity(Gravity.CENTER);
        return txt;
    }
}
