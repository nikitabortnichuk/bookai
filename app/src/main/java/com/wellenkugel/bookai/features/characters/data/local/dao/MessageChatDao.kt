package com.wellenkugel.bookai.features.characters.data.local.dao

import androidx.room.*
import com.wellenkugel.bookai.features.characters.data.local.model.MessageEntity

@Dao
interface MessageChatDao {

    @Transaction
    @Query("SELECT * FROM message")
    suspend fun getAllMessages(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)
}