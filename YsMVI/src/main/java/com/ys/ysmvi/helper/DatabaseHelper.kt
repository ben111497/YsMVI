package com.ys.ysmvi.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

class DatabaseHelper (context: Context, name :String, version: Int)
    : SQLiteOpenHelper(context, name, null, version) {
    lateinit var db: SQLiteDatabase
    override fun onCreate(db: SQLiteDatabase) {
        this.db = db
        Log.e("YsDB","create")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        this.db = db
        Log.e("YsDB","delete")
    }

    @Throws(SQLException::class)
    fun openDataBase(): Boolean {
        db = writableDatabase
        return db.isOpen
    }

    @Synchronized
    override fun close() {
        db.close()
        SQLiteDatabase.releaseMemory()
        super.close()
    }
}