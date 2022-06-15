package com.matricula.mobile.apiService

import com.matricula.mobile.models.Usuario
import com.matricula.mobile.apiUtils.ApiBuilder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend  fun login(@Body usuario: Usuario): Response<Usuario>
    @DELETE("login")
    suspend  fun logout(): Response<Void>

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