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
                item.forEach { user ->
                    if (db.userDao().isUserExist(user.id)) {
                        user.id.let { userId ->
                            val userOld = db.userDao().getUser(userId)

                            user.note = userOld.note
                            user.name = userOld.name
                            user.company = userOld.company
                            user.blog = userOld.blog
                            user.location = userOld.location
                            user.email = userOld.email
                            user.hireable = userOld.hireable
                            user.bio = userOld.bio
                            user.twitterUsername = userOld.twitterUsername
                            user.publicRepos = userOld.publicRepos
                            user.publicGists = userOld.publicGists
                            user.followers = userOld.followers
                            user.following = userOld.following
                            user.updatedAt = userOld.updatedAt
                            user.createdAt = userOld.createdAt

                            db.userDao().update(user)
                        }
                    } else {
                        db.userDao().insert(user)
                    }
                }
            }

            override fun shouldFetch(data: List<User>?): Boolean {
                return data == null || data.size >= 0
            }

            override fun loadFromDb() = db.userDao().getUsersFromIndex(fromIndex)

            override fun createCall() = apiService.getUsers(fromIndex, Constants.DATA_LOAD_PER_PAGE)

            override fun onFetchFailed(throwable: Throwable) {
                super.onFetchFailed(throwable)

                Log.d("tttt", "msg >"+throwable.message)
            }
        }.asLiveData()
    }
}