package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Curso
import retrofit2.Response
import retrofit2.http.*

interface CursoService {
    @GET("cursos/listar")
    suspend fun obtenerCursos(): Response<List<Curso>>
    @GET("cursos/cursoCarrera")
    suspend fun obtenerCursosPorCarrera(@Query("codigo") codigo:String): Response<List<Curso>>
    @POST("cursos")
    suspend fun ingresarCurso(@Body curso: Curso): Response<Void>
    @PUT("cursos")
    suspend fun modificarCurso(@Body curso: Curso): Response<Void>
    @DELETE("cursos")
    suspend fun eliminarCurso(@Query("id") codigo:String): Response<Void>
    companion object {
        private var cursoService : CursoService? = null
        fun getInstance() : CursoService {
            if (cursoService == null) {
                cursoService = ApiBuilder.buildService(CursoService::class.java)
            }
            return cursoService!!
        }
    }
}