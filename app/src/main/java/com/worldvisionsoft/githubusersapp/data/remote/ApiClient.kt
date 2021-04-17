package com.verapax.stops.route.data.remote

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.worldvisionsoft.githubusersapp.data.remote.ApiService
import com.worldvisionsoft.githubusersapp.data.remote.LiveDataCallAdapterFactory
import com.worldvisionsoft.githubusersapp.utils.Constants
import com.worldvisionsoft.githubusersapp.utils.Constants.OKHTTP_CONNECT_TIMEOUT
import com.worldvisionsoft.githubusersapp.utils.Constants.OKHTTP_READ_TIMEOUT
import com.worldvisionsoft.githubusersapp.utils.Constants.OKHTTP_WRITE_TIMEOUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val builder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val httpClient by lazy { OkHttpClient.Builder() }

    private var retrofit: Retrofit = builder.build()

    fun <T> createService(serviceClass: Class<T>): T {
        httpClient.connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
        httpClient.readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)

        //httpClient.addNetworkInterceptor(StethoInterceptor())
        //httpClient.addInterceptor(interceptor)

        builder.client(httpClient.build())
        retrofit = builder.build()
        return retrofit.create(serviceClass)
    }

    inline fun <reified T> createNetworkCaller(): T {
        return createService(T::class.java)
    }
}