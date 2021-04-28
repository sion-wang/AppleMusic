package com.sion.itunes.view.music

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sion.itunes.R
import com.sion.itunes.model.vo.Music
import kotlinx.android.synthetic.main.item_music.view.*
import timber.log.Timber

class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val clRoot: ConstraintLayout = view.cl_root
    private val ivCover: ImageView = view.iv_cover
    private val tvArtistName: TextView = view.tv_artist_name
    private val tvTrackName: TextView = view.tv_track_name

    fun onBind(item: Music, musicFuncItem: MusicFuncItem) {
        clRoot.setOnClickListener { musicFuncItem.onMusicItemClick.invoke(item) }
        Glide.with(ivCover.context).load(item.artworkUrl100).placeholder(R.drawable.record)
            .into(ivCover)
        tvArtistName.text = item.artistName
        tvTrackName.text = item.trackName
    }
}