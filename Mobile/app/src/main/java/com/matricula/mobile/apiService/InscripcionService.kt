package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder

interface InscripcionService {
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