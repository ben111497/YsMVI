package com.ys.ysmvi.model

import android.content.Context
import androidx.room.RoomDatabase

class Repository private constructor(context: Context, dataBase: RoomDatabase?) {
    val dataStore: DataStore = DataStore(context)
    val room: RoomDatabase? = dataBase

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