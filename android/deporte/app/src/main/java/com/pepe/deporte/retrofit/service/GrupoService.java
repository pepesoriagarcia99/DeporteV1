package com.pepe.deporte.retrofit.service;

import com.pepe.deporte.model.GrupoCreate;
import com.pepe.deporte.model.response.GrupoResponse;
import com.pepe.deporte.model.response.OneGrupoResponse;
import com.pepe.deporte.model.response.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GrupoService {
    @GET("/grupos")
    Call<ResponseContainer<GrupoResponse>> listaGrupos();

    @GET("/grupos")
    Call<ResponseContainer<GrupoResponse>> listaGruposQuery(@Query("titulo") String titulo);

    @GET("/grupos/me")
    Call<ResponseContainer<GrupoResponse>> myListaGrupos();

    @POST("/grupos")
    Call<String> GrupoCreate(@Body GrupoCreate grupo);

    @DELETE("/grupos/{id}")
    Call<String> deleteDeporte(@Path("id") String id);

    @GET("/grupos/{id}")
    Call<OneGrupoResponse> OneGrupo(@Path("id") String id);

    @PUT("/grupos/{id}")
    Call<OneGrupoResponse> editarGrupo(@Path("id") String id, @Body GrupoCreate grupo);
}
