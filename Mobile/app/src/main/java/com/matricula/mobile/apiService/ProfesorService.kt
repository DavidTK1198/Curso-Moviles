package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Profesor
import retrofit2.Response
import retrofit2.http.*

interface ProfesorService {
    @GET("profesores/listar")
    suspend  fun obtenerProfesores(): Response<List<Profesor>>
    @GET("profesores/profesorCedula")
    suspend  fun obtenerProfesorPorID(@Query("ced") codigo:String): Response<Profesor>
    @POST("profesores")
    suspend fun ingresarProfesor(@Body profesor: Profesor): Response<Void>
    @PUT("profesores")
    suspend fun modificarProfesor(@Body profesor: Profesor): Response<Void>
    @DELETE("profesores")
    suspend fun eliminarProfesor(@Query("ced") codigo:String): Response<Void>


    companion object {
        private var profesorService : ProfesorService? = null
        fun getInstance() : ProfesorService {
            if (profesorService == null) {
                profesorService = ApiBuilder.buildService(ProfesorService::class.java)
            }
            return profesorService!!
        }
    }
}