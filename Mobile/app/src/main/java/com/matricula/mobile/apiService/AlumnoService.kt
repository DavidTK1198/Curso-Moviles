package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Alumno
import retrofit2.Response
import retrofit2.http.*

interface AlumnoService {

    @GET("alumnos/listar")
    suspend  fun obtenerAlumnos(): Response<List<Alumno>>
    @GET("alumnos/carreraCodigo")
    suspend  fun obtenerAlumnoPorID(): Response<Alumno>
    @POST("alumnos")
    suspend fun ingresarAlumno(@Body alumno: Alumno): Response<Void>
    @PUT("alumnos")
    suspend fun modificarAlumno(@Body alumno: Alumno): Response<Void>
    @DELETE("alumnos")
    suspend fun eliminarAlumno(@Query("ced") codigo:String): Response<Void>

    companion object {
        private var alumnoService : AlumnoService? = null
        fun getInstance() : AlumnoService {
            if ( alumnoService == null) {
                alumnoService = ApiBuilder.buildService(AlumnoService::class.java)
            }
            return  alumnoService!!
        }
    }
}