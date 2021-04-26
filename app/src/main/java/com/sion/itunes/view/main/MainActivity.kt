package com.sion.itunes.view.main

import android.os.Bundle
import com.sion.itunes.R
import com.sion.itunes.view.base.BaseActivity
import com.sion.itunes.view.users.UsersFragment
import org.koin.core.component.KoinApiExtension

class MainActivity : BaseActivity() {
    @KoinApiExtension
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(UsersFragment())
    }
}