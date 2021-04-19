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

    @Query("SELECT * from user where login = :userName")
    fun getUserDetails(userName: String): LiveData<User>

    @Query("SELECT * from user where id = :userId")
    fun getUser(userId: Int): User

    @Query("SELECT EXISTS(SELECT * FROM user WHERE id = :userId)")
    fun isUserExist(userId : Int) : Boolean

    @Query("UPDATE user SET note = :noteString where id = :userId")
    suspend fun saveNote(noteString: String, userId: Int)

    @Query("SELECT note from user where id = :userId")
    fun getUserNote(userId: Int): String

    @Query("SELECT id FROM user ORDER BY id DESC LIMIT 1")
    suspend fun getLastEntryId() : Int

    @Query("SELECT * FROM user WHERE login LIKE :search")
    suspend fun findUserWithName(search: String): List<User>
}