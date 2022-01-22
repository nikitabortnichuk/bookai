package com.wellenkugel.bookai.features.characters.presentation.view.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wellenkugel.bookai.R
import com.wellenkugel.bookai.databinding.ViewHolderUserTextMessageBinding
import com.wellenkugel.bookai.features.characters.presentation.model.messages.UserTextMessageItem

class UserTextMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(
            parent: ViewGroup,
        ): UserTextMessageViewHolder {
            return UserTextMessageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_user_text_message, parent, false),
            )
        }
    }

    private val binding: ViewHolderUserTextMessageBinding =
        ViewHolderUserTextMessageBinding.bind(view)

    fun bind(userTextMessageItem: UserTextMessageItem) {
        binding.userTextMessage.text = userTextMessageItem.text
    }
}
