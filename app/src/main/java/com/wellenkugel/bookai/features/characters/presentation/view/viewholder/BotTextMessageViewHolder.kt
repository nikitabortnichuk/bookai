package com.wellenkugel.bookai.features.characters.presentation.view.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wellenkugel.bookai.R
import com.wellenkugel.bookai.databinding.ViewHolderBotTextMessageBinding
import com.wellenkugel.bookai.features.characters.presentation.model.messages.BotTextMessageItem

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

    fun bind(botTextMessageItem: BotTextMessageItem) {
        binding.botTextMessage.text = botTextMessageItem.text
    }
}