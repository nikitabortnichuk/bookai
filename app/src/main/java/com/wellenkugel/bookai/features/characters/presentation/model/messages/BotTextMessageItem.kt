package com.wellenkugel.bookai.features.characters.presentation.model.messages

import com.wellenkugel.bookai.features.characters.domain.model.MessageListItem
import com.wellenkugel.bookai.features.characters.domain.model.MessageListItem.MessageListItemViewType

data class BotTextMessageItem(
    val text: String
) : MessageListItem {

    override val itemViewType: MessageListItemViewType = MessageListItemViewType.BOT_TEXT_MESSAGE
}
