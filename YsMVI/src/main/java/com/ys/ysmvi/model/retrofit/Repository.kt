package com.ys.ysmvi.model.retrofit

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import com.ys.ysmvi.model.DataStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class Repository private constructor(context: Context, dataBase: RoomDatabase?) {
    val dataStore: DataStore = DataStore(context)
    val room: RoomDatabase? = dataBase
    val retrofit: DynamicRetrofit = DynamicRetrofit

    companion object {
        private var instance: Repository? = null
        fun getInstance(context: Context, room: RoomDatabase? = null): Repository {
            return instance ?: run {
                instance = Repository(context, room)
                instance!!
            }
        }
    }
}