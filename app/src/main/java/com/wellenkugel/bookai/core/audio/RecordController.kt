package com.wellenkugel.bookai.core.audio


import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException

class RecordController(private val context: Context) {

    private var audioRecorder: MediaRecorder? = null
    private var fileName: String? = context.externalCacheDir?.absolutePath ?: ""

    fun start() : String {
        Log.d(TAG, "Start")

        val audioPath = fileName + "/${System.currentTimeMillis()}.3gp"
        audioRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioPath)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(TAG, "prepare() failed: ${e.printStackTrace()}")
            }
            start()
        }
        return audioPath
    }


    fun stop() {
        audioRecorder?.let {
            Log.d(TAG, "Stop")
            it.stop()
            it.reset()
            it.release()
        }
        audioRecorder = null
    }

    fun isAudioRecording() = audioRecorder != null

    fun getVolume() = audioRecorder?.maxAmplitude ?: 0

    private companion object {
        private val TAG = RecordController::class.java.name
    }
}