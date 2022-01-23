package com.wellenkugel.bookai.features.characters.domain.model

interface MessageListItem {

    enum class MessageListItemViewType {
        BOT_TEXT_MESSAGE,
        USER_TEXT_MESSAGE,
        USER_VOICE_MESSAGE
    }

    val itemViewType: MessageListItemViewType
}