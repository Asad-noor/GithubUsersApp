package com.worldvisionsoft.githubusersapp.data.remote

import androidx.lifecycle.LiveData
import com.worldvisionsoft.githubusersapp.data.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsers(@Query("since") since: Int, @Query("per_page") itemsPerPage: Int): LiveData<ApiResponse<List<User>>>
}