package com.example.jarvis.top.WebService;

import com.example.jarvis.top.WebService.Models.Chamados.ChamadosModel;
import com.example.jarvis.top.WebService.Models.Chamados.ComentariosChamadosModel;
import com.example.jarvis.top.WebService.Models.Default;
import com.example.jarvis.top.WebService.Models.Login.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Connects {
    @FormUrlEncoded
    @POST("teste.php")
    Call<LoginModel> loginPOST(@Field("user") String first, @Field("pass") String last, @Field("token") String token);

    @FormUrlEncoded
    @POST("salvarcomentario.php")
    Call<Default> addComentarioPOST(@Field("id") String first, @Field("comentario") String last);

    @FormUrlEncoded
    @POST("comentariochamado.php")
    Call<ComentariosChamadosModel> getComentarioPOST(@Field("id") String first);

    @GET("chamados.php")
    Call<ChamadosModel> getChamados();
}
