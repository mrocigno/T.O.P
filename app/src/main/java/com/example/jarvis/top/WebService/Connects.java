package com.example.jarvis.top.WebService;

import com.example.jarvis.top.WebService.Models.Chamados.ChamadosModel;
import com.example.jarvis.top.WebService.Models.Chamados.ComentariosChamadosModel;
import com.example.jarvis.top.WebService.Models.Default;
import com.example.jarvis.top.WebService.Models.Login.LoginModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Connects {
    @FormUrlEncoded
    @POST("wsLogin.php")
    Call<LoginModel> loginPOST(@Field("user") String first, @Field("pass") String last, @Field("token") String token);

    @FormUrlEncoded
    @POST("wsSalvarComentario.php")
    Call<Default> addComentarioPOST(@Field("id") String first, @Field("comentario") String last);

    @FormUrlEncoded
    @POST("getComentarios.php")
    Call<ComentariosChamadosModel> getComentarioPOST(@Field("id") String first);

    @GET("chamados.php")
    Call<ChamadosModel> getChamados();

    @GET("getChamados.php")
    Call<ChamadosModel> getChamadosExternos(@Query("id") String ID);

    @Multipart
    @POST("wsResult.php")
    Call<ChamadosModel> setResult(@Part("id_chamado") RequestBody id_chamado, @Part("detalhes") RequestBody detalhes, @Part MultipartBody.Part img1, @Part MultipartBody.Part img2, @Part MultipartBody.Part img3);

    @FormUrlEncoded
    @POST("wsChangeStatus.php")
    Call<ChamadosModel> setStatus(@Field("id_chamado") String id, @Field("status") String status);
}
