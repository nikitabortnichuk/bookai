package com.wellenkugel.bookai.features.characters.data.repository

import android.util.Log
import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.core.functional.Either
import com.wellenkugel.bookai.features.characters.data.remote.api.BooksRapidApi
import com.wellenkugel.bookai.features.characters.data.remote.request.BookBySubjectRequestBody
import com.wellenkugel.bookai.features.characters.domain.model.BookDetails
import com.wellenkugel.bookai.features.characters.domain.repository.IBooksRapidRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRapidRepository @Inject constructor(
    private val apiService: BooksRapidApi,
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
}
