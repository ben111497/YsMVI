package com.ys.ysmvi.model.retrofit

import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DynamicRetrofit {
    private var BASE_URL = "https://www.base"
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    private val client = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val request: Request = chain.request()
            val builder: Request.Builder = request.newBuilder()
            val oldHttpUrl: HttpUrl = request.url
            val newBaseUrl = BASE_URL.toHttpUrl()
            val newFullUrl = oldHttpUrl
                .newBuilder()
                .scheme(newBaseUrl.scheme)
                .host(newBaseUrl.host)
                .port(newBaseUrl.port)
                .build()

            chain.proceed(builder.url(newFullUrl).build())
        })
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    private val service = retrofit.create(RetrofitInterface::class.java)

    fun getService(url: String): RetrofitInterface {
        BASE_URL = url
        return service
    }
}


