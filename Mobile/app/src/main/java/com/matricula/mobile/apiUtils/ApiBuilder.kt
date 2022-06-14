package com.matricula.mobile.apiUtils
import com.matricula.mobile.WEB_PATH
import com.matricula.mobile.WEB_PORT
import com.matricula.mobile.WEB_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object  ApiBuilder {

    private val client =
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://$WEB_URL:$WEB_PORT/$WEB_PATH/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}