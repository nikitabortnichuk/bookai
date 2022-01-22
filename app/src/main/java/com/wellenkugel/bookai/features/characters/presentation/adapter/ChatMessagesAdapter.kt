package com.wellenkugel.bookai.features.characters.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wellenkugel.bookai.features.characters.presentation.model.messages.BotTextMessageItem
import com.wellenkugel.bookai.features.characters.presentation.model.messages.MessageListItem
import com.wellenkugel.bookai.features.characters.presentation.model.messages.MessageListItem.MessageListItemViewType
import com.wellenkugel.bookai.features.characters.presentation.model.messages.UserTextMessageItem
import com.wellenkugel.bookai.features.characters.presentation.view.viewholder.BotTextMessageViewHolder
import com.wellenkugel.bookai.features.characters.presentation.view.viewholder.UserTextMessageViewHolder
import java.util.*

class ChatMessagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages = LinkedList<MessageListItem>()

    fun addMessages(messages: List<MessageListItem>) {
        this.messages.clear()
        this.messages.addAll(messages)
        notifyDataSetChanged()
    }

    fun addLastMessage(message: MessageListItem, canScroll: Boolean) {
        this.messages.addFirst(message)
        if (canScroll) {
            notifyDataSetChanged()
        } else {
            notifyItemInserted(0)
        }
    }

    // todo add one by one

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MessageListItemViewType.USER_TEXT_MESSAGE.ordinal ->
                UserTextMessageViewHolder.create(parent)
            MessageListItemViewType.BOT_TEXT_MESSAGE.ordinal ->
                BotTextMessageViewHolder.create(parent)
            else -> throw Exception("No such view holder item")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BotTextMessageViewHolder -> holder.bind(messages[position] as BotTextMessageItem)
            is UserTextMessageViewHolder -> holder.bind(messages[position] as UserTextMessageItem)
        }
    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int) = messages[position].itemViewType.ordinal
}