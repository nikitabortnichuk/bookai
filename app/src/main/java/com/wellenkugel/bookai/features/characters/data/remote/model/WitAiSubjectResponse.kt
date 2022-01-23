package com.wellenkugel.bookai.features.characters.data.remote.model

import com.squareup.moshi.Json
import com.wellenkugel.bookai.features.characters.domain.model.BookSubject

data class WitAiSubjectResponse(
    val text: String,
    @field:Json(name = "entities")
    val entity: Entity?
) {

    data class Entity(
        @field:Json(name = "book_keywords:book_keywords")
        val bookKeywords: List<BookKeywords>?
    ) {
        data class BookKeywords(
            val body: String
        )
    }

    fun toDomainObject() = BookSubject(
        text = text,
        subject = if (entity?.bookKeywords == null || entity.bookKeywords.isEmpty()) ""
        else entity.bookKeywords[0].body
    )
}