package com.sion.itunes.view.audio

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.sion.itunes.R
import com.sion.itunes.model.vo.Music
import com.sion.itunes.view.base.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_audio.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.schedule

class AudioDialogFragment(val music: Music) : BaseDialogFragment() {
    override fun isFullLayout() = true
    override fun getLayoutId() = R.layout.fragment_audio

    lateinit var mediaPlayer: MediaPlayer

    private val timer by lazy {
        fixedRateTimer("timer", false, 0, 1000) {
            Timber.d("@@Timber")
            seek_bar.progress = mediaPlayer.currentPosition / mediaPlayer.duration
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(music.artworkUrl100).placeholder(R.drawable.record)
            .into(iv_cover)
        tv_artist_name.text = music.artistName
        tv_track_name.text = music.trackName

        iv_close.setOnClickListener { dismiss() }

        ib_control.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                ib_control.setImageResource(R.drawable.play)
                mediaPlayer.pause()
            } else {
                ib_control.setImageResource(R.drawable.pause)
                mediaPlayer.start()
            }
        }

        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(music.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            Timber.d("@@setOnPreparedListener: ${mediaPlayer.duration}")
            progress_bar.visibility = View.GONE
            seek_bar.max = mediaPlayer.duration
            it.start()
            ib_control.setImageResource(R.drawable.pause)
        }
        mediaPlayer.setOnCompletionListener {
            Timber.d("@@setOnCompletionListener")
            ib_control.setImageResource(R.drawable.play)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}