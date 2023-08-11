package com.ys.ysmvi.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DB_Version = 1
@Database(entities = [Api::class], version = DB_Version)
abstract class RoomSample : RoomDatabase() {
    abstract fun ApiDao(): ApiDao

    companion object {
        @Volatile
        private var instance: RoomSample? = null

        fun instance(context: Context): RoomSample {
            return instance ?: Room
                .databaseBuilder(context, RoomSample::class.java, "RoomSample")
                .fallbackToDestructiveMigration().build()
                .also { instance = it }
        }
    }
}
