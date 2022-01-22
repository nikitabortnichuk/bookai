package com.wellenkugel.bookai.features.characters.presentation.model.state

import com.wellenkugel.bookai.features.characters.presentation.model.FilmPresentation

data class FilmView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val films: List<FilmPresentation>? = null
)
