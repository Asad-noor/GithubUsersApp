package com.worldvisionsoft.githubusersapp.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.worldvisionsoft.githubusersapp.data.model.User
import com.worldvisionsoft.githubusersapp.data.remote.ApiService
import com.worldvisionsoft.githubusersapp.data.remote.AppExecutors
import com.worldvisionsoft.githubusersapp.data.remote.NetworkBoundResource
import com.worldvisionsoft.githubusersapp.data.remote.Resource
import com.worldvisionsoft.githubusersapp.utils.Constants

class UserDetailsRepository: BaseRepository() {

    private val apiService = createApiService<ApiService>()
    private val appExecutors = AppExecutors()

    fun loadUserDetails(userName: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {

            override fun saveCallResult(item: User) {
                val note = db.userDao().getUserNote(item.id)
                item.note = note
                db.userDao().update(item)
            }

            override fun shouldFetch(data: User?): Boolean {
                return data?.name == null
            }

            override fun loadFromDb() = db.userDao().getUserDetails(userName)

            override fun createCall() = apiService.getUserDetails(userName)
        }.asLiveData()
    }
}