package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Usuario
import retrofit2.Response
import retrofit2.http.GET

interface UsuarioService {
    @GET("usuarios")
    suspend  fun obtenerUsuarios(): Response<List<Usuario>>

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