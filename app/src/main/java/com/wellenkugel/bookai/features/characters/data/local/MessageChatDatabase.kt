package com.wellenkugel.bookai.features.characters.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wellenkugel.bookai.features.characters.data.local.dao.MessageChatDao
import com.wellenkugel.bookai.features.characters.data.local.model.MessageEntity

@Database(
    entities = [MessageEntity::class],
    version = 1, exportSchema = false
)
abstract class MessageChatDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "message_chat_database"
    }

    abstract fun charactersDao(): MessageChatDao
}
