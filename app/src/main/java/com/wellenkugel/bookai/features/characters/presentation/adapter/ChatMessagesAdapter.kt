package com.wellenkugel.bookai.features.characters.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wellenkugel.bookai.features.characters.presentation.view.viewholder.BotTextMessageViewHolder

class ChatMessagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // todo change type, because we will have audio
    private val messages = ArrayList<String>()

    fun addMessages(messages: List<String>) {
        this.messages.clear()
        this.messages.addAll(messages)
        notifyDataSetChanged()
    }

    // todo add one by one

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BotTextMessageViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BotTextMessageViewHolder).bind(messages[position])
    }

    override fun getItemCount() = messages.size
}