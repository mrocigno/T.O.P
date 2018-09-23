package com.example.jarvis.top.WebService;

import com.example.jarvis.top.WebService.Models.Chamados.ChamadosModel;
import com.example.jarvis.top.WebService.Models.Login.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Connects {
    @FormUrlEncoded
    @POST("teste.php")
    Call<LoginModel> loginPOST(@Field("user") String first, @Field("pass") String last);

    @GET("chamados.php")
    Call<ChamadosModel> getChamados();
}
