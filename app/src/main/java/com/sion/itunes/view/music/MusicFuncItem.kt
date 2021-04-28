package com.sion.itunes.view.music

import com.sion.itunes.model.vo.Music

data class MusicFuncItem (
    val onMusicItemClick: ((Music) -> Unit) = { _ -> },
)