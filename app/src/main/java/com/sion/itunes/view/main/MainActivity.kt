package com.sion.itunes.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.sion.itunes.R
import com.sion.itunes.view.base.BaseActivity
import com.sion.itunes.view.music.MusicFragment
import com.sion.itunes.view.search.SearchDialogFragment
import org.koin.core.component.KoinApiExtension

class MainActivity : BaseActivity() {
    @KoinApiExtension
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(MusicFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                SearchDialogFragment().show(
                    supportFragmentManager,
                    SearchDialogFragment::class.java.simpleName
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


}