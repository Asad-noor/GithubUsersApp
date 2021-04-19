package com.worldvisionsoft.githubusersapp.ui.details

import androidx.lifecycle.*
import com.worldvisionsoft.githubusersapp.data.local.AppDatabase
import com.worldvisionsoft.githubusersapp.data.model.User
import com.worldvisionsoft.githubusersapp.data.remote.Resource
import com.worldvisionsoft.githubusersapp.data.repo.UserDetailsRepository
import kotlinx.coroutines.launch

class UserDetailsFragmentViewModel : ViewModel() {

    private val userDetailsRepository by lazy { UserDetailsRepository() }
    private val _userName: MutableLiveData<String> = MutableLiveData()
    private val db by  lazy { AppDatabase.getInstance() }

    val _note: MutableLiveData<String>

    init {
        _note = MutableLiveData()
    }

    val githubUserDetails: LiveData<Resource<User>> = Transformations
        .switchMap(_userName) { userName ->
            userDetailsRepository.loadUserDetails(userName)
        }

    fun callUserDetails(userName: String) {
        _userName.value = userName
    }

    fun saveNote(note: String, id: Int) {
        viewModelScope.launch {
            db.userDao().saveNote(note, id)
        }
    }

    fun getNote(id: Int) {
        viewModelScope.launch {
            _note.value = db.userDao().getUserNote(id)
        }
    }
}