package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Inscripcion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface InscripcionService {

    @GET("inscripciones/alumno")
    suspend  fun obtenerInscripcionesPorAlumno(@Query("ced") codigo:String): Response<List<Inscripcion>>
    @GET("inscripciones/grupo")
    suspend  fun obtenerInscripcionesPorGrupo(@Query("id") codigo:String):Response<List<Inscripcion>>
    @PUT("inscripciones")
    suspend  fun colocarNota(@Body inscripcion: Inscripcion):Response<Void>
    @POST("inscripciones")
    suspend  fun matricular(@Body ins: Inscripcion): Response<Void>
    @DELETE("inscripciones")
    suspend  fun desMatricular(@Query("id") codigo:String): Response<Void>


    companion object {
        private var inscripcionService : InscripcionService? = null
        fun getInstance() : InscripcionService {
            if (inscripcionService == null) {
                inscripcionService = ApiBuilder.buildService(InscripcionService::class.java)
            }
            return inscripcionService!!
        }
    }
}