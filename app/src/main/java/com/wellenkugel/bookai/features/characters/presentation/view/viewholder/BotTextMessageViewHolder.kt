package com.wellenkugel.bookai.features.characters.presentation.view.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wellenkugel.bookai.R
import com.wellenkugel.bookai.databinding.ViewHolderBotTextMessageBinding

class BotTextMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
        ): BotTextMessageViewHolder {
            return BotTextMessageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_bot_text_message, parent, false),
            )
        }
    }

    private val binding: ViewHolderBotTextMessageBinding =
        ViewHolderBotTextMessageBinding.bind(view)

    fun bind(text: String) {
        binding.botTextMessage.text = text
    }
}