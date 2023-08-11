package com.ys.ysmvi.data.room

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.ys.ysmvi.base.YsBaseDao
import com.ys.ysmvi.base.YsBaseEntity
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "API_TABLE")
data class Api(
    @PrimaryKey
    val name: String,
    val json: String
) : YsBaseEntity()

@Dao
interface ApiDao: YsBaseDao<Api> {
    @Query("SELECT * FROM API_TABLE WHERE name = :name")
    fun getByName(name: String): Flow<Api>?
}