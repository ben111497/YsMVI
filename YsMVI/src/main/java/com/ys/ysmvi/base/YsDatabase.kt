package com.ys.ysmvi.base

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.ys.ysmvi.helper.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class YsDatabase private constructor(val context: Context, val name: String, val version: Int) {
    private var tag = "YsDB"
    companion object {
        var dbHelper: DatabaseHelper? = null
        var db: SQLiteDatabase? = null

        @SuppressLint("StaticFieldLeak")
        private var instance: YsDatabase? = null

        @Synchronized
        fun getInstance(context: Context, name: String, version: Int): YsDatabase {
            if (instance == null) instance = YsDatabase(context.applicationContext, name, version)
            return instance!!
        }
    }

    fun createDB(): YsDatabase {
        dbHelper = DatabaseHelper(context, name, version)
        dbHelper?.openDataBase()
        db = dbHelper?.db
        log("createDB -> name: $name, version: $version")
        return this
    }

    fun deleteDB(): Boolean {
        return try {
            val path = context.getDatabasePath(name)
            log("deleteDB: name: $name")
            if (path.exists()) path.delete() else true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            log("deleteDB failed: ${e.message}")
            false
        }
    }

    fun execSQL(SQL: String) {
        try {
            log("execSQL: $SQL")
            db?.execSQL(SQL)
        } catch (e: Exception) {
            log("execSQL error: ${e.message}")
        }
    }

    suspend fun query(sql: String): Flow<Cursor> = flow {
        log("query: $sql")
        withContext(Dispatchers.IO) { db?.rawQuery(sql, null) }
            ?.use { cursor -> if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    log("cursor: $cursor")
                    emit(cursor)
                } }
            }
    }

    fun close() {
        dbHelper?.close()
    }

    private fun log(str: String) {
        Log.e(tag, str)
    }
}
