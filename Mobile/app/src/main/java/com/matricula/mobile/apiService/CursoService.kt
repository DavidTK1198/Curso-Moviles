package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder

interface CursoService {
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