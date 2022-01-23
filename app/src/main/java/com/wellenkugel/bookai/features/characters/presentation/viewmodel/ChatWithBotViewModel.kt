package com.wellenkugel.bookai.features.characters.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject


@HiltViewModel
class ChatWithBotViewModel @Inject constructor(
    private val popularBooksUseCase: GetBooksBySubjectUseCase,
    private val getBookSubjectFromQuestionUseCase: GetBookSubjectFromQuestionUseCase,
    private val getSavedMessagesUseCase: GetSavedMessagesUseCase
) : ViewModel() {

    private val job = Job()

    private val _initialMessageView = MutableLiveData<List<MessageListItem>>()
    val initialMessageView: LiveData<List<MessageListItem>>
        get() = _initialMessageView

    private val _messageView = MutableLiveData<MessageListItem>()
    val messageView: LiveData<MessageListItem>
        get() = _messageView


    fun getAllMessages() {
        getSavedMessagesUseCase(job, "") {
            it.fold(
                ::handleFailure,
                ::handleSuccessForGetSavedMessages
            )
        }
    }

    fun getBookSubjectByQuestion(text: String) {
        getBookSubjectFromQuestionUseCase(job, text) {
            it.fold(
                ::handleFailure,
                ::handleSuccessGetBookSubject
            )
        }
    }

    private fun handleSuccessForGetSavedMessages(messages: List<Message>) {
        if (messages.isNotEmpty()) {
            _initialMessageView.value = messages.map {
                when (it.ownerId) {
                    MessageListItemViewType.BOT_TEXT_MESSAGE.ordinal -> BotTextMessageItem(it.text)
                    MessageListItemViewType.USER_TEXT_MESSAGE.ordinal -> UserTextMessageItem(it.text)
                    else -> throw Exception("Not supported type")
                }
            }
        }
    }

    private fun handleSuccessGetBookSubject(bookSubject: BookSubject) {
        if (bookSubject.subject.isNotEmpty()) {
            searchBooksBySubject(bookSubject.subject)
        } else {
            _messageView.value = BotTextMessageItem(
                "Sorry, I don`t understand you. I can only" +
                        " help you with book recommendation, try to " +
                        "explain for me what are you worried about?"
            )
        }
    }

    private fun searchBooksBySubject(subject: String) {
        popularBooksUseCase(job, subject) {
            it.fold(
                ::handleFailure,
                ::handleSuccessBySearchBooksBySubject
            )
        }
    }

    private fun handleSuccessBySearchBooksBySubject(suggestedBooks: List<BookDetails>) {
        Log.d("TAGGERR", suggestedBooks.toString())
        if (suggestedBooks.isNotEmpty()) {
            var combinedMessage = ""
            suggestedBooks.forEachIndexed { index, bookDetails ->
                if (index > 3) return@forEachIndexed
                combinedMessage += DIVIDER + bookDetails.title + "\n"
            }
            _messageView.value = BotTextMessageItem(combinedMessage)
        } else {
            _messageView.value =
                BotTextMessageItem("Can`t find anything :( Please try explain more detailed")
        }
    }

    private fun handleFailure(failure: Failure) {
        // todo lock hole screen with error message like "Smth went wrong"
        Log.d("TAGGERR", "error, ${failure.toString()}")
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    private companion object {
        const val DIVIDER = "-----------\n"
    }
}
