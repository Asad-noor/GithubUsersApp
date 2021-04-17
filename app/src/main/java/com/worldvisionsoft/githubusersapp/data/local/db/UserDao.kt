package com.worldvisionsoft.githubusersapp.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.worldvisionsoft.githubusersapp.data.local.BaseDao
import com.worldvisionsoft.githubusersapp.data.model.User

@Dao
interface UserDao: BaseDao<User> {

    @Query("SELECT * from user")
    fun getUsersFromDb() : LiveData<List<User>>

    @Query("SELECT * from user where id > :fromIndex")
    fun getUsersFromIndex(fromIndex: Int) : LiveData<List<User>>

    @Query("SELECT id FROM user ORDER BY id DESC LIMIT 1")
    suspend fun getLastEntryId() : Int
}