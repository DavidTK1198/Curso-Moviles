package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Inscripcion
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface InscripcionService {

    @GET("inscripciones/alumno")
    suspend  fun obtenerInscripcionesPorAlumno(@Query("ced") codigo:String): Response<List<Inscripcion>>

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