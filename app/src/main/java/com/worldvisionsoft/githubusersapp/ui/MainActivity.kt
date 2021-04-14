package com.worldvisionsoft.githubusersapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.worldvisionsoft.githubusersapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
    }
}