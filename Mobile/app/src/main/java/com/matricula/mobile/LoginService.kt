package com.matricula.mobile

import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface LoginService {
    @Headers("Accept: application/json")
    @POST("login")
    suspend  fun login(@Body usuario: Usuario): Response<Usuario>

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