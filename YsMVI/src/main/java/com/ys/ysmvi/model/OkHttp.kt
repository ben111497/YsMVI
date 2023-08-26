package com.ys.ysmvi.model

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.util.Date

object OkHttp {
    private val client = OkHttpClient()
    private var url = "http://base"
    val okHttpRes = MutableStateFlow<YsResponse>(YsResponse.Init)

    class Header(val name: String, var value: String)

    fun get(tag: String, url: String, header: ArrayList<Header>?, hash: Long = Date().time) {
        val req = setRequest(url, header)
            .addHeader("Content-type", "application/json")
            .build()
        sendRequest(tag, req, hash)
    }

    fun post(tag: String, url: String, json: String, header: ArrayList<Header>, hash: Long = Date().time) {
        val req = setRequest(url, header)
            .addHeader("Content-type", "application/json")
            .post(json.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()
        sendRequest(tag, req, hash)
    }

    private fun sendRequest(tag: String, req: Request, hash: Long) {
        try {
            client.newCall(req).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val data = response.body?.string()
                        okHttpRes.value = YsResponse.Success(tag, data, hash)
                        Log.e("(OkHttp)API Success", "data: $data")
                    } else {
                        okHttpRes.value = YsResponse.Failed(tag, response.message, hash)
                        Log.e("(OkHttp)API UnSuccess", "Request failed with code: ${response.code}")
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    okHttpRes.value = YsResponse.Failed(tag, e.message.toString(), hash)
                    Log.e("(OkHttp)API Failed", "Request failed with code: ${e.message}")
                }
            })
        } catch (e: IOException) {
            okHttpRes.value = YsResponse.Failed(tag, e.message.toString(), hash)
            Log.e("(OkHttp)API catch", "error msg: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun setRequest(url: String, header: ArrayList<Header>?): Request.Builder {
        if (url != this.url) this.url = url
        val newRequest = Request.Builder().url(this.url)
        if (header != null) for (i in header) { newRequest.addHeader(i.name, i.value) }
        return newRequest
    }
}