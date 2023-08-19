package com.ys.ysmvi.model.retrofit

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit


object DynamicRetrofit {
    val ApiResponse = MutableStateFlow<YsResponse>(YsResponse.Init)
    private var BASE_URL = "https://www.base"
    private var lastInterface: Class<*>? = null
    private var timeOut: Long = 10 * 1000
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    private val client = OkHttpClient.Builder()
        .connectTimeout(timeOut, TimeUnit.SECONDS)
        .readTimeout(timeOut, TimeUnit.SECONDS)
        .writeTimeout(timeOut, TimeUnit.SECONDS)
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

    private var service = retrofit.create(RetrofitInterface::class.java)

    fun <T : RetrofitInterface> getService(url: String, interfaceClass: Class<T>): RetrofitInterface {
        if (url != BASE_URL) BASE_URL = url
        if (interfaceClass != lastInterface) service = retrofit.create(interfaceClass)
        return service
    }

    fun <T> res(tag: String, hash: Long) = object: Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                Log.e("API Success", response.body().toString())
                ApiResponse.value = YsResponse.Success(tag, response.body(), hash)
            } else {
                Log.e("API Failed", response.errorBody()?.charStream()?.readText().toString())
                ApiResponse.value = YsResponse.Failed(tag, response.errorBody()?.charStream()?.readText().toString(), hash)
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.e("API Failure", t.message.toString())
            if (t is SocketTimeoutException) {
                ApiResponse.value = YsResponse.TimeOut(tag, hash)
            } else {
                ApiResponse.value = YsResponse.Failed(tag, t.message.toString(), hash)
            }
        }
    }
}


