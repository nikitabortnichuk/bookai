package com.wellenkugel.bookai.features.characters.presentation.view.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wellenkugel.bookai.R
import com.wellenkugel.bookai.databinding.ViewHolderUserVoiceMessageBinding

class UserAudioMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
        ): UserAudioMessageViewHolder {
            return UserAudioMessageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_user_voice_message, parent, false),
            )
        }
    }

    private val binding: ViewHolderUserVoiceMessageBinding =
        ViewHolderUserVoiceMessageBinding.bind(view)

    fun bind(text: String) {

    }
}
