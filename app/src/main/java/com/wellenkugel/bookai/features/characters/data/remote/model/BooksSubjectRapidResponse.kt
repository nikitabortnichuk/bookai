package com.wellenkugel.bookai.features.characters.data.remote.model

import com.wellenkugel.bookai.features.characters.domain.model.BookDetails

data class BookResponse(
    private val id: String,
    private val title: String,
    private val subtitle: String?,
    private val links: List<String>?,
    private val subjects: List<String>?,
    private val description: String?
) {
    fun toDomainObject() = BookDetails(
        id = id,
        title = title,
        description = description ?: "",
        subjects = subjects ?: emptyList()
    )
}

data class BooksSubjectRapidResponse(
    val data: List<BookResponse>
)
