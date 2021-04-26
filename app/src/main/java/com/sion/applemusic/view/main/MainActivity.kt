package com.sion.applemusic.view.main

import android.os.Bundle
import com.sion.applemusic.R
import com.sion.applemusic.view.base.BaseActivity
import com.sion.applemusic.view.users.UsersFragment
import org.koin.core.component.KoinApiExtension

class MainActivity : BaseActivity() {
    @KoinApiExtension
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(UsersFragment())
    }
}