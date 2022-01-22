package com.wellenkugel.bookai.features.characters.presentation.model.state

import com.wellenkugel.bookai.features.characters.presentation.model.SpeciePresentation

data class SpecieView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val species: List<SpeciePresentation>? = null
)
