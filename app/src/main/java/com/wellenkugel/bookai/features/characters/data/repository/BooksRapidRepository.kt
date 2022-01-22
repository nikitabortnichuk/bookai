package com.wellenkugel.bookai.features.characters.data.repository

import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.core.functional.Either
import com.wellenkugel.bookai.features.characters.data.local.dao.MessageChatDao
import com.wellenkugel.bookai.features.characters.data.local.model.MessageEntity
import com.wellenkugel.bookai.features.characters.data.remote.api.BooksRapidApi
import com.wellenkugel.bookai.features.characters.data.remote.api.WitAiApi
import com.wellenkugel.bookai.features.characters.data.remote.request.BookBySubjectRequestBody
import com.wellenkugel.bookai.features.characters.domain.model.BookDetails
import com.wellenkugel.bookai.features.characters.domain.model.BookSubject
import com.wellenkugel.bookai.features.characters.domain.model.Message
import com.wellenkugel.bookai.features.characters.domain.model.MessageListItem
import com.wellenkugel.bookai.features.characters.domain.repository.IBooksRapidRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRapidRepository @Inject constructor(
    private val apiService: BooksRapidApi,
    private val apiAiApi: WitAiApi,
    private val messageChatDao: MessageChatDao
) : IBooksRapidRepository {

    override suspend fun searchBooksBySubject(subject: String): Flow<Either<Failure, List<BookDetails>>> =
        flow {
            val res = apiService.searchBooksBySubject(BookBySubjectRequestBody(subject = subject))
            emit(when (res.isSuccessful) {
                true -> {
                    res.body()?.let { it ->
                        Either.Right(it.data.map { a -> a.toDomainObject() })
                    } ?: Either.Left(Failure.DataError)
                }
                false -> {
                    Either.Left(Failure.ServerError)
                }
            })
        }

    override suspend fun getBookSubject(text: String): Flow<Either<Failure, BookSubject>> = flow {
        val res = apiAiApi.getBookSubjectSuggestion(text)
        emit(
            when (res.isSuccessful) {
                true -> {
                    res.body()?.let { it ->
                        Either.Right(it.toDomainObject())
                    } ?: Either.Left(Failure.DataError)
                }
                false -> {
                    Either.Left(Failure.ServerError)
                }
            }
        )
    }

    override suspend fun getAllMessages(): Flow<Either<Failure, List<Message>>> {
        val res = messageChatDao.getAllMessages()
        val textList = ArrayList<Message>().apply {
            addAll(res.map { it.toDomainObject() }.asReversed())
            add(
                Message(
                    "Hello, my name is Bookie, and I will help you to find the " +
                            "book you need. Type or tell me what you are worried about?",
                    MessageListItem.MessageListItemViewType.BOT_TEXT_MESSAGE.ordinal
                )
            )
        }
        return flow { emit(Either.Right(textList)) }
    }

    override suspend fun insertMessage(messageEntity: MessageEntity) {
        messageChatDao.insertMessage(messageEntity)
    }
}
