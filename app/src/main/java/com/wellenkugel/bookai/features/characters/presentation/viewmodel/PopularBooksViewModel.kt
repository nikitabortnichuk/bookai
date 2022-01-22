package com.wellenkugel.bookai.features.characters.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.features.characters.domain.model.BookDetails
import com.wellenkugel.bookai.features.characters.domain.usecases.GetBooksBySubjectUseCase
import com.wellenkugel.bookai.features.characters.presentation.model.state.CharacterSearchView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class PopularBooksViewModel @Inject constructor(
    // todo replace with another use case for popular books
    private val popularBooksUseCase: GetBooksBySubjectUseCase,
) : ViewModel() {

    private val job = Job()

    private val _charactersSearchView = MutableLiveData<CharacterSearchView>()
    val charactersSearchView: LiveData<CharacterSearchView>
        get() = _charactersSearchView

    fun searchPopularBooks() {
        popularBooksUseCase(job, "procrastination") {
            it.fold(
                ::handleFailure,
                ::handleSuccess
            )
        }

    }

    private fun handleSuccess(characters: List<BookDetails>) {
        Log.d("TAGGERR", characters.toString())
        if (characters.isNotEmpty()) {
            _charactersSearchView.value = CharacterSearchView(isEmpty = true)
//                CharacterSearchView(
//                    characters = characters.map { it.toPresentation() },
//                    isRecentList = _charactersSearchView.value?.isRecentList ?: false
//                )
        } else
            _charactersSearchView.value = CharacterSearchView(isEmpty = true)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun handleFailure(failure: Failure) {
        Log.d("TAGGERR", "error, ${failure.toString()}")
        // Todo error message should be based on failure type
        _charactersSearchView.value = CharacterSearchView(errorMessage = "An error occurred.")
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
