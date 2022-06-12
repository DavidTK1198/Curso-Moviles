package com.matricula.mobile

import com.matricula.mobile.models.Carrera
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getCarreras(@Url url:String): Response<List<Carrera>>
}