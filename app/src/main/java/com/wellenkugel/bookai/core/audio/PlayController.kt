package com.wellenkugel.bookai.core.audio

import android.media.MediaPlayer
import android.util.Log

class PlayController {

    private var mediaPlayer: MediaPlayer? = null

    fun start(filePath: String) {
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer?.run {
                setOnCompletionListener {
                    completePlaying()
                }
                setDataSource(filePath)
                prepare()
                start()
            }
        } catch (e: Exception) {
            Log.e("TAGGERR", "prepare failed: ${e.localizedMessage}")
        }
    }

    fun stop() {
        completePlaying()
    }

    fun isAudioPlaying() = mediaPlayer != null

    private fun completePlaying() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}