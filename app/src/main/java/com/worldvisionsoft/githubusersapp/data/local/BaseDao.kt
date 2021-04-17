package com.worldvisionsoft.githubusersapp.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T)

    @Update
    suspend fun update(item: T)

    @Delete
    suspend fun delete(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<T>)
}