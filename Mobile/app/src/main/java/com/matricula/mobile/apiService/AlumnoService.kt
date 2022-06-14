package com.matricula.mobile.apiService

import com.matricula.mobile.apiUtils.ApiBuilder

interface AlumnoService {
    companion object {
        private var loginService : LoginService? = null
        fun getInstance() : LoginService {
            if (loginService == null) {
                loginService = ApiBuilder.buildService(LoginService::class.java)
            }
            return loginService!!
        }
    }
}