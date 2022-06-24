package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Grupo
import retrofit2.Response
import retrofit2.http.*

interface GrupoService {

    @GET("grupos/listar")
    suspend fun obtenerGrupos(@Query("ciclo") ciclo:String,@Query("codigo") curso:String): Response<List<Grupo>>
    @DELETE("grupos")
    suspend fun eliminarGrupo(@Query("id") id:String): Response<Void>
    @POST("grupos/agregar")
    suspend fun ingresarGrupo(@Body grupo: Grupo): Response<Void>
    @PUT("grupos")
    suspend fun modificarGrupo(@Body grupo: Grupo): Response<Void>
    @GET("grupos/profesor")
    suspend fun obtenerGrupoPorProfesor(@Query("ced") cedula:String): Response<List<Grupo>>
    @GET("grupos/ciclo")
    suspend fun obtenerGruposPorCiclo(@Query("id") id:String): Response<List<Grupo>>

    companion object {
        private var grupoService : GrupoService? = null
        fun getInstance() : GrupoService {
            if (grupoService == null) {
                grupoService = ApiBuilder.buildService(GrupoService::class.java)
            }
            return grupoService!!
        }
    }
}