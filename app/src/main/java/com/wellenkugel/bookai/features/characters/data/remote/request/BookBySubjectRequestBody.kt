package com.wellenkugel.bookai.features.characters.data.remote.request

data class BookBySubjectRequestBody(
    private val cursor: Int = 1,
    private val subject: String,
)
