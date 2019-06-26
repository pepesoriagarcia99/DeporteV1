package com.pepe.deporte.retrofit.service;

import com.pepe.deporte.model.response.Deporte;
import com.pepe.deporte.model.response.GrupoResponse;
import com.pepe.deporte.model.response.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DeporteService {
    @GET("/deportes")
    Call<ResponseContainer<Deporte>> listaDeportes();
}
