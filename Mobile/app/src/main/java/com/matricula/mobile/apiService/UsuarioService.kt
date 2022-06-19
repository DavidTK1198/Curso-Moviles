package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UsuarioService {
    @GET("usuarios")
    suspend  fun obtenerUsuarios(): Response<List<Usuario>>
    @PUT("usuarios")
    suspend  fun modificarUsuario(@Body usuario: Usuario): Response<Void>

    companion object {
        private var usuarioService : UsuarioService? = null
        fun getInstance() : UsuarioService {
            if (usuarioService == null) {
                usuarioService = ApiBuilder.buildService(UsuarioService::class.java)
            }
            return usuarioService!!
        }
    }
}