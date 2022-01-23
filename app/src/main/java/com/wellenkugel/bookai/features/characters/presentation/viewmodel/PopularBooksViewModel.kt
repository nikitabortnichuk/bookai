package com.wellenkugel.bookai.features.characters.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.features.characters.domain.model.BookDetails
import com.wellenkugel.bookai.features.characters.domain.model.BookSubject
import com.wellenkugel.bookai.features.characters.domain.model.Message
import com.wellenkugel.bookai.features.characters.domain.model.MessageListItem
import com.wellenkugel.bookai.features.characters.domain.model.MessageListItem.MessageListItemViewType
import com.wellenkugel.bookai.features.characters.domain.usecases.GetBookSubjectFromQuestionUseCase
import com.wellenkugel.bookai.features.characters.domain.usecases.GetBooksBySubjectUseCase
import com.wellenkugel.bookai.features.characters.domain.usecases.GetSavedMessagesUseCase
import com.wellenkugel.bookai.features.characters.presentation.model.messages.BotTextMessageItem
import com.wellenkugel.bookai.features.characters.presentation.model.messages.UserTextMessageItem
import com.wellenkugel.bookai.features.characters.presentation.model.state.CharacterSearchView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PopularBooksViewModel @Inject constructor(
    private val popularBooksUseCase: GetBooksBySubjectUseCase,
    private val getBookSubjectFromQuestionUseCase: GetBookSubjectFromQuestionUseCase,
    private val getSavedMessagesUseCase: GetSavedMessagesUseCase
) : ViewModel() {

    private val job = Job()

    // todo delete
    private val _charactersSearchView = MutableLiveData<CharacterSearchView>()
    val charactersSearchView: LiveData<CharacterSearchView>
        get() = _charactersSearchView

    private val _messageView = MutableLiveData<List<MessageListItem>>()
    val messageView: LiveData<List<MessageListItem>>
        get() = _messageView
    ////////

//    private val _messageState =
//        MutableStateFlow<Message>(
//            Message(
//                "Hello, my name is Ostape, tell me what care you?",
//                MessageListItemViewType.BOT_TEXT_MESSAGE.ordinal
//            )
//        )
//    val messageState: StateFlow<Message>
//        get() = _messageState


    fun searchPopularBooks() {
        popularBooksUseCase(job, "procrastination") {
            it.fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    fun getAllMessages() {
        getSavedMessagesUseCase(job, "") {
            it.fold(
                ::handleFailure,
                ::handleSuccessForGetSavedMessages
            )
        }
    }

    fun getBookSubjectByQuestion(text: String) {
        viewModelScope.launch {
            getBookSubjectFromQuestionUseCase.run(text)
        }
        getBookSubjectFromQuestionUseCase(job, text) {
            it.fold(
                ::handleFailure,
                ::handleSuccessForBookSubject
            )
        }
    }

    private fun handleSuccessForGetSavedMessages(messages: List<Message>) {
        if (messages.isNotEmpty()) {
            _messageView.value = messages.map {
                when (it.ownerId) {
                    MessageListItemViewType.BOT_TEXT_MESSAGE.ordinal -> BotTextMessageItem(it.text)
                    MessageListItemViewType.USER_TEXT_MESSAGE.ordinal -> UserTextMessageItem(it.text)
                    else -> throw Exception("Not supported type")
                }
            }
        }
    }

    private fun handleSuccessForBookSubject(bookSubject: BookSubject) {
        Log.d("TAGGERR", "${bookSubject.subject} subject")
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
