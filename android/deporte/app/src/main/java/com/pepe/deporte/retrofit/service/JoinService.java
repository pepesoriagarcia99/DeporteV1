package com.pepe.deporte.retrofit.service;

import com.pepe.deporte.model.GrupoCreate;
import com.pepe.deporte.model.response.OneGrupoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JoinService {

    @POST("/grupos/join/{grupoId}")
    Call<String> Join(@Path("grupoId") String grupoId);

    @DELETE("/grupos/exit/{grupoId}")
    Call<String> Exit(@Path("grupoId") String grupoId);

}
