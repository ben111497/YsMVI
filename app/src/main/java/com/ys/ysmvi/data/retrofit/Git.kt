package com.ys.ysmvi.data.retrofit

import com.google.gson.annotations.SerializedName

data class Repo(val id: Int, val name: String, val owner: User)
data class User(val id: Int, @SerializedName("avatar_url") val avatarUrl: String)