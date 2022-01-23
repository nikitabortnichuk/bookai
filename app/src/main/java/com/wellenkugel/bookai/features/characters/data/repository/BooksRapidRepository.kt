package com.wellenkugel.bookai.features.characters.data.repository

import android.util.Log
import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.core.functional.Either
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
    private val apiAiApi: WitAiApi
) : IBooksRapidRepository {

    override suspend fun searchBooksBySubject(subject: String): Flow<Either<Failure, List<BookDetails>>> =
        flow {
            val res =
                apiService.searchBooksBySubject(BookBySubjectRequestBody(subject = subject))
            emit(
                when (res.isSuccessful) {
                    true -> {
                        res.body()?.let { it ->
                            Either.Right(it.data.map { a -> a.toDomainObject() })
                        } ?: Either.Left(Failure.DataError)
                    }
                    false -> {
                        Log.d("TAGGERR", res.errorBody().toString())
                        Either.Left(Failure.ServerError)
                    }
                }
            )
        }

    override suspend fun getBookSubject(text: String): Flow<Either<Failure, BookSubject>> =
        flow {
            val res = apiAiApi.getBookSubjectSuggestion(text)
            emit(
                when (res.isSuccessful) {
                    true -> {
                        Log.d("TAGGERR", "inside success")
                        res.body()?.let { it ->
                            Log.d("TAGGERR", "inside body")

                            Either.Right(it.toDomainObject())
                        } ?: Either.Left(Failure.DataError)
                    }
                    false -> {
                        Log.d("TAGGERR", res.errorBody().toString())
                        Either.Left(Failure.ServerError)
                    }
                }
            )
        }

    override suspend fun getAllMessages(): Flow<Either<Failure, List<Message>>> {
        val textList = listOf<Message>(
            Message(
                "How are you doing?",
                MessageListItem.MessageListItemViewType.BOT_TEXT_MESSAGE.ordinal
            ),
            Message("Hello!", MessageListItem.MessageListItemViewType.BOT_TEXT_MESSAGE.ordinal)
        )
        return flow { emit(Either.Right(textList)) }
    }
}
