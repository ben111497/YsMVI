package com.ys.ysmvi.model

import java.util.Date

sealed class YsResponse {
    object Init: YsResponse()
    data class Success<T>(val tag: String, val data: T, val hash: Long = Date().time): YsResponse()
    data class Failed(val tag: String, val error: String, val hash: Long = Date().time): YsResponse()
    data class TimeOut(val tag: String, val hash: Long = Date().time): YsResponse()
}