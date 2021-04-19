package com.worldvisionsoft.githubusersapp.ui.home

import androidx.lifecycle.*
import com.worldvisionsoft.githubusersapp.data.local.AppDatabase
import com.worldvisionsoft.githubusersapp.data.model.User
import com.worldvisionsoft.githubusersapp.data.remote.Resource
import com.worldvisionsoft.githubusersapp.data.repo.UserListRepository
import kotlinx.coroutines.launch

class UsersListFragmentViewModel: ViewModel() {

    private val userListRepository by lazy { UserListRepository() }
    private val db by  lazy { AppDatabase.getInstance() }

    private val _fromIndex: MutableLiveData<Int> = MutableLiveData()
    var searchResultList: MutableLiveData<List<User>>

    init {
        searchResultList = MutableLiveData()
    }

    //where map can NOT return LiveData object but switchMap can
    //map will take input as LiveData object and returns the type which is wrapped in LiveData<Type>
    //within map you cannot perform time-consuming operation but within switchMap you can do thread based operations

    val githubUsersList: LiveData<Resource<List<User>>> = Transformations
        .switchMap(_fromIndex) { fromIndex ->
            if(fromIndex == null) {
                //AbsentLiveData.create()
                userListRepository.loadUsers(0)
            } else {
                userListRepository.loadUsers(fromIndex)
            }
        }

    fun callUsersList(fromIndex: Int) {
        if (fromIndex == 0) {
            _fromIndex.value = null
        } else {
            viewModelScope.launch {
                db.userDao().getLastEntryId().let {
                    _fromIndex.value = it
                }
            }
        }
    }

    fun searchByKeyword(search: String) {
        viewModelScope.launch {
            db.userDao().findUserWithName("%"+search+"%").let {
                searchResultList.value = it
            }
        }
    }
}