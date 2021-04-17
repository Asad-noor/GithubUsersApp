package com.worldvisionsoft.githubusersapp

import android.app.Application

class GUApplication: Application() {

    companion object {
        private lateinit var mInstance: GUApplication

        fun getInstance(): GUApplication {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this
    }
}