package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder

interface ProfesorService {
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