package com.worldvisionsoft.githubusersapp.data.repo

import android.content.Context
import com.verapax.stops.route.data.remote.ApiClient
import com.worldvisionsoft.githubusersapp.data.local.AppDatabase

open class BaseRepository() {
    protected val db: AppDatabase = AppDatabase.getInstance()
    //protected val appPreferencesHelper: AppPreferencesHelper = AppPreferencesHelper(context)

    protected inline fun <reified T> createApiService(): T {
        //val accessToken = appPreferencesHelper.getAccessToken()
        //val refreshToken = appPreferencesHelper.getRefreshToken()
        return ApiClient.createNetworkCaller()
    }
}