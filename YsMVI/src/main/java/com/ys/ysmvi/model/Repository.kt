package com.ys.ysmvi.model

import android.content.Context

class Repository private constructor(context: Context) {
    val dataStore: DataStore = DataStore(context)

    companion object {
        private var instance: Repository? = null
        fun getInstance(context: Context): Repository {
            return instance ?: run {
                instance = Repository(context)
                instance!!
            }
        }
    }
}