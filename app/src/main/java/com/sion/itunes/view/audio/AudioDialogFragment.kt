package com.sion.itunes.view.audio

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.sion.itunes.R
import com.sion.itunes.model.vo.Music
import com.sion.itunes.view.base.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_audio.*
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.ceil

class AudioDialogFragment(val music: Music) : BaseDialogFragment() {
    override fun isFullLayout() = true
    override fun getLayoutId() = R.layout.fragment_audio

    private lateinit var mediaPlayer: MediaPlayer

    private val mainHandler by lazy { Handler(Looper.getMainLooper())}
    private lateinit var runnable: Runnable

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

        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                takeIf { fromUser }?.run {
                    val ms = mediaPlayer.duration * (progress / 100.toDouble())
                    mediaPlayer.seekTo(ms.toInt())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        runnable = Runnable {
            val progress =
                ((mediaPlayer.currentPosition.toDouble() / mediaPlayer.duration) * 100).toInt()
            seek_bar.progress = progress
            tv_current_time.text = formatDuration(mediaPlayer.currentPosition)
            mainHandler.postDelayed(runnable, 1000)
        }

        mediaPlayer = MediaPlayer().also { mp ->
            mp.setDataSource(music.previewUrl)
            mp.prepareAsync()
            mp.setOnPreparedListener {
                progress_bar.visibility = View.GONE
                tv_duration.text = formatDuration(mediaPlayer.duration)
                it.start()
                ib_control.setImageResource(R.drawable.pause)
                mainHandler.post(runnable)
            }
            mp.setOnCompletionListener {
                ib_control.setImageResource(R.drawable.play)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        release()
    }

    private fun formatDuration(duration: Int): String {
        val absSeconds = abs(ceil(duration.toDouble() / 1000).toInt())
        val positive = String.format(
            "%02d:%02d",
            absSeconds / 60,
            absSeconds % 60
        )
        return if (duration < 0) "-$positive" else positive
    }

    private fun release() {
        stopAndRelease(mediaPlayer)
        mainHandler.removeCallbacks(runnable)
    }

    private fun stopAndRelease(mediaPlayer: MediaPlayer?) {
        mediaPlayer?.run {
            try {
                if (this.isPlaying) {
                    this.pause()
                    this.stop()
                }
                this.release()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}