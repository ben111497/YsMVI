package com.ys.ysmvi.model.retrofit

import android.content.Context
import androidx.room.RoomDatabase
import com.ys.ysmvi.model.DataStore

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