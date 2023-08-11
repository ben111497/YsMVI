package com.ys.ysmvi.data.retrofit


import com.ys.ysmvi.model.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestInterface: RetrofitInterface {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("users/{user}/repos")
    fun getListRepos(@Path("user") user: String, @Query("type") type: String? = null, @Query("sort") sort: String? = null): Call<List<Repo>>
}