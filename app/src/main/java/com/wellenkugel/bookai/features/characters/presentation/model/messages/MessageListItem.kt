package com.wellenkugel.bookai.features.characters.presentation.model.messages

interface MessageListItem {

    enum class MessageListItemViewType {
        BOT_TEXT_MESSAGE,
        USER_TEXT_MESSAGE,
        USER_VOICE_MESSAGE
    }

    val itemViewType: MessageListItemViewType
}