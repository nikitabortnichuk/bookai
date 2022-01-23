package com.wellenkugel.bookai.features.characters.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wellenkugel.bookai.features.characters.domain.model.Message

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val ownerId: Int
) {
    fun toDomainObject() = Message(
        text,
        ownerId
    )
}