package com.example.jarvis.top.WebService;

import com.example.jarvis.top.WebService.Models.LoginModel;
import com.example.jarvis.top.WebService.Models.Teste;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Connects {
    @GET("teste.php")
    Call<ArrayList<LoginModel>> teesstee();

    @GET("teste.php")
    Call<Teste> login();

    @FormUrlEncoded
    @POST("teste.php")
    Call<Teste> loginPOST(@Field("user") String first, @Field("pass") String last);
    //@Field("first_name") String first, @Field("last_name") String last
}
