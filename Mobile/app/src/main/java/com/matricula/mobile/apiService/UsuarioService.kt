package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder

interface UsuarioService {
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