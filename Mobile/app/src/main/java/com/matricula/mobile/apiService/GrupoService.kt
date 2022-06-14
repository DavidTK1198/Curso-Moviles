package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder

interface GrupoService {
    companion object {
        private var grupoService : GrupoService? = null
        fun getInstance() : GrupoService {
            if (grupoService == null) {
                grupoService = ApiBuilder.buildService(GrupoService::class.java)
            }
            return grupoService!!
        }
    }
}