package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Ciclo
import retrofit2.Response
import retrofit2.http.*
interface CicloService {
    @GET("ciclos/listar")
    suspend fun obtenerCiclos(): Response<List<Ciclo>>
    companion object {
        private var cicloService : CicloService? = null
        fun getInstance() : CicloService {
            if (cicloService == null) {
                cicloService = ApiBuilder.buildService(CicloService::class.java)
            }
            return cicloService!!
        }
    }
}