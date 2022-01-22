package com.wellenkugel.bookai.features.characters.domain.model

data class BookDetails(
    val id: String,
    val title: String,
    val description: String,
    val subjects: List<String>
)
