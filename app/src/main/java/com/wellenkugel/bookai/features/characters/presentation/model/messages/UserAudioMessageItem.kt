package com.wellenkugel.bookai.features.characters.presentation.model.messages

import com.wellenkugel.bookai.features.characters.domain.model.MessageListItem
import com.wellenkugel.bookai.features.characters.domain.model.MessageListItem.MessageListItemViewType

class UserAudioMessageItem(
    val audioPath: String,
    val text: String
) : MessageListItem {

    override val itemViewType: MessageListItemViewType = MessageListItemViewType.USER_VOICE_MESSAGE
}