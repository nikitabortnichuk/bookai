package com.wellenkugel.bookai.features.characters.presentation.model.messages

import com.wellenkugel.bookai.features.characters.presentation.model.messages.MessageListItem.MessageListItemViewType

data class UserTextMessageItem(
    val text: String
) : MessageListItem {

    override val itemViewType: MessageListItemViewType = MessageListItemViewType.USER_TEXT_MESSAGE
}
