package com.ys.ysmvi.model

import android.content.Context
import androidx.room.RoomDatabase
import com.ys.ysmvi.model.retrofit.DynamicRetrofit

class Repository private constructor(context: Context, dataBase: RoomDatabase?) {
    val dataStore: DataStore = DataStore(context)
    val room: RoomDatabase? = dataBase
    val retrofit: DynamicRetrofit = DynamicRetrofit
    val okHttp: OkHttp = OkHttp

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