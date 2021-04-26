package com.sion.applemusic.view.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.sion.applemusic.R
import com.sion.applemusic.model.vo.GithubUser
import com.sion.applemusic.view.base.BaseFragment
import com.sion.applemusic.view.userdetail.UserDetailDialogFragment
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class UsersFragment : BaseFragment() {
    private val viewModel: UsersViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_users

    private val userFuncItem = UserFuncItem(
        onUserItemClick = { userItem -> goDetail(userItem) }
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
            is LoadState.Error -> {}
            is LoadState.Loading -> {}
            is LoadState.NotLoading -> {}
        }
    }

    private val userAdapter by lazy {
        val adapter = UserAdapter(userFuncItem)
        adapter.addLoadStateListener(loadStateListener)
        adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (rv_users.adapter == null) {
            rv_users.adapter = userAdapter
            getUsers()
        }
    }

    private fun getUsers() {
        lifecycleScope.launch {
            viewModel.getUsers().collectLatest {
                userAdapter.submitData(it)
            }
        }
    }

    private fun goDetail(item: GithubUser) {
        UserDetailDialogFragment(item.id.toString()).show(
            requireActivity().supportFragmentManager,
            UsersFragment::class.java.simpleName
        )
    }
}