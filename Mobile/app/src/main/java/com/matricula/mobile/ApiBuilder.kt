package com.matricula.mobile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object  ApiBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://$WEB_URL:$WEB_PORT/$WEB_PATH/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}