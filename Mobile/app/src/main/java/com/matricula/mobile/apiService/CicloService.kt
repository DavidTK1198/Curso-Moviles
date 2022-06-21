package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Ciclo
import retrofit2.Response
import retrofit2.http.*
interface CicloService {
    @GET("ciclos/listar")
    suspend fun obtenerCiclos(): Response<List<Ciclo>>
    @POST("ciclos")
    suspend fun ingresarCiclo(@Body ciclo: Ciclo): Response<Void>

    @DELETE("ciclos")
    suspend fun eliminarCiclo(@Query("id") codigo:String): Response<Void>
    @PUT("ciclos")
    suspend fun modificarCiclo(@Body ciclo: Ciclo): Response<Void>

    @PUT("ciclos/cicloDesActivar")
    suspend fun desactivarCiclo(@Body ciclo: Ciclo): Response<Void>

    @PUT("ciclos/cicloActivar")
    suspend fun activar(@Body ciclo: Ciclo): Response<Void>

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