package com.sion.itunes.view.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sion.itunes.R
import com.sion.itunes.model.vo.Music

class MusicAdapter(private val musicFuncItem: MusicFuncItem) :
    PagingDataAdapter<Music, MusicViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Music>() {
            override fun areItemsTheSame(
                oldItem: Music,
                newItem: Music
            ): Boolean {
                return oldItem.trackId == newItem.trackId
            }

            override fun areContentsTheSame(
                oldItem: Music,
                newItem: Music
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        getItem(position)?.run { holder.onBind(this, musicFuncItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_music, parent, false)
        )
    }
}