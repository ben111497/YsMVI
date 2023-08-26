package com.ys.ysmvi.ui.fragment.sample


import com.ys.ysmvi.data.retrofit.RequestInterface
import com.ys.ysmvi.data.room.RoomSample
import com.ys.ysmvi.model.OkHttp
import com.ys.ysmvi.model.Repository
import java.util.Date

class FmSampleRepo(private val repo: Repository) {
    val okHttpRes = repo.okHttp.okHttpRes
    val retrofitRes = repo.retrofit.retrofitRes
    val room = repo.room as RoomSample
    val dataStore = repo.dataStore
    fun getGitData(tag: String, name: String, hash: Long = Date().time) {
        repo.retrofit.getService("https://api.github.com", RequestInterface::class.java).also {
            it as RequestInterface
            it.getListRepos(name).enqueue(repo.retrofit.res(tag, hash))
        }
    }
    fun getGitDataOkHttp(tag: String, url: String, header: ArrayList<OkHttp.Header>? = null, hash: Long = Date().time) {
        repo.okHttp.get(tag, url, header, hash)
    }
}