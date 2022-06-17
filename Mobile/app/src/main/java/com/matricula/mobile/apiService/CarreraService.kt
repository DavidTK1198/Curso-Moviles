package com.matricula.mobile.apiService
import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Usuario
import retrofit2.Response
import retrofit2.http.*

interface CarreraService {
    @GET("carreras/listar")
    suspend  fun obtenerCarreras(): Response<List<Carrera>>
    @GET("carreras/carreraCodigo")
    suspend  fun obtenerCarreraPorCodigo(): Response<Carrera>
    @POST("carreras")
    suspend fun ingresarCarrera(@Body carrera: Carrera): Response<Void>
    @PUT("carreras")
    suspend fun modificarCarrera(@Body carrera: Carrera): Response<Void>

    @DELETE("carreras")
    suspend fun eliminarCarrera(@Query("cod") codigo:String): Response<Void>

    companion object {
        private var carreraService : CarreraService? = null
        fun getInstance() : CarreraService {
            if (carreraService == null) {
                carreraService = ApiBuilder.buildService(CarreraService::class.java)
            }
            return carreraService!!
        }
    }
}