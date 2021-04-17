package com.worldvisionsoft.githubusersapp.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.worldvisionsoft.githubusersapp.data.model.User
import com.worldvisionsoft.githubusersapp.data.remote.*
import com.worldvisionsoft.githubusersapp.utils.Constants

class UserListRepository : BaseRepository() {

    private val apiService = createApiService<ApiService>()
    private val appExecutors = AppExecutors()

    fun loadUsers(fromIndex: Int): LiveData<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, List<User>>(appExecutors) {

            override fun saveCallResult(item: List<User>) {
                db.userDao().insertList(item)
            }

            override fun shouldFetch(data: List<User>?): Boolean {
                return data == null || data.size >= 0
            }

            override fun loadFromDb() = db.userDao().getUsersFromIndex(fromIndex)

            override fun createCall() = apiService.getUsers(fromIndex, Constants.DATA_LOAD_PER_PAGE)
        }.asLiveData()
    }
}