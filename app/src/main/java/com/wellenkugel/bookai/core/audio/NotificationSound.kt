package com.wellenkugel.bookai.core.audio

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri

class NotificationSound(private val context: Context) {

    fun playSound() {
        try {
            val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(
                context,
                notification
            )
            ringtone.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}