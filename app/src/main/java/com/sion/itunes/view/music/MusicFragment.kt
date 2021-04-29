package com.sion.itunes.view.music

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.sion.itunes.R
import com.sion.itunes.view.audio.AudioDialogFragment
import com.sion.itunes.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_music.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
@ExperimentalPagingApi
class MusicFragment(private val keyword: String = "pop") : BaseFragment() {
    private val viewModel: MusicViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_music

    private val musicFuncItem = MusicFuncItem(
        onMusicItemClick = { music ->
            AudioDialogFragment(music).show(
                requireActivity().supportFragmentManager,
                AudioDialogFragment::class.java.simpleName
            )
        }
    )

    private val loadStateListener = { loadStatus: CombinedLoadStates ->
        when (loadStatus.refresh) {
            is LoadState.Error -> {
                showDialog()
                progress_bar.visibility = View.GONE
            }
            is LoadState.Loading -> progress_bar.visibility = View.VISIBLE
            is LoadState.NotLoading -> {
                progress_bar.visibility = View.GONE
            }
        }
        when (loadStatus.append) {
            is LoadState.Error -> {
            }
            is LoadState.Loading -> {
            }
            is LoadState.NotLoading -> {
            }
        }
    }

    private val musicAdapter by lazy {
        val adapter = MusicAdapter(musicFuncItem)
        adapter.addLoadStateListener(loadStateListener)
        adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_music.takeIf { it.adapter == null }?.let {
            it.adapter = musicAdapter
            search()
        }
    }

    private fun search(key: String = keyword) {
        lifecycleScope.launch {
            viewModel.search(key, true).collectLatest {
                musicAdapter.submitData(it)
            }
        }
    }
}