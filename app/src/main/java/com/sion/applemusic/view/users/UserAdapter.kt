package com.sion.applemusic.view.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sion.applemusic.R
import com.sion.applemusic.model.vo.GithubUser

class UserAdapter(private val userFuncItem: UserFuncItem) :
    PagingDataAdapter<GithubUser, UserViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(
                oldItem: GithubUser,
                newItem: GithubUser
            ): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(
                oldItem: GithubUser,
                newItem: GithubUser
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.run { holder.onBind(this, userFuncItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        )
    }
}