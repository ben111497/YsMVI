package com.ys.ysmvi.extention

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJsonArray(json: String): T = this.fromJson(json, object : TypeToken<T>() {}.type)