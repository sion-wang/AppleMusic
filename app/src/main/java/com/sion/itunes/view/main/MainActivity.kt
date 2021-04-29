package com.sion.itunes.view.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.sion.itunes.R
import com.sion.itunes.view.base.BaseActivity
import com.sion.itunes.view.music.MusicFragment
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(MusicFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchView = (menu?.findItem(R.id.search)?.actionView as SearchView)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.run { navigateTo(MusicFragment(this)) }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

            setOnCloseListener {
                navigateTo(MusicFragment())
                false
            }
        }
        return true
    }

}