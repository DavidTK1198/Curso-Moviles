package com.matricula.mobile.apiService
import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface CarreraService {
    @GET("carreras/listar")
    suspend  fun obtenerCarreras(): Response<List<Carrera>>
    @GET("carreras/carreraCodigo")
    suspend  fun obtenerCarreraPorCodigo(): Response<Carrera>
    @POST("carreras")
    suspend fun ingresarCarrera(@Body carrera: Carrera): Response<Void>
    @PUT
    suspend fun modificarCarrera(carrera: Carrera): Response<Void>

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