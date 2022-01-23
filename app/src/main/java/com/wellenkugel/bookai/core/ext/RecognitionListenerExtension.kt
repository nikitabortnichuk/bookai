package com.wellenkugel.bookai.core.ext

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer

fun SpeechRecognizer.onResults(onResult: (text: String) -> Unit) {
    setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
        }

        override fun onBeginningOfSpeech() {
        }

        override fun onRmsChanged(rmsdB: Float) {
        }

        override fun onBufferReceived(buffer: ByteArray?) {
        }

        override fun onEndOfSpeech() {
        }

        override fun onError(error: Int) {
        }

        override fun onResults(results: Bundle?) {
            val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            onResult.invoke(data?.first() ?: throw Exception())
        }

        override fun onPartialResults(partialResults: Bundle?) {
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
        }
    })
}