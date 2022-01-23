package com.wellenkugel.bookai.features.characters.domain.repository

import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.core.functional.Either
import com.wellenkugel.bookai.features.characters.domain.model.BookDetails
import com.wellenkugel.bookai.features.characters.domain.model.BookSubject
import kotlinx.coroutines.flow.Flow

interface IBooksRapidRepository {

    suspend fun searchBooksBySubject(subject: String): Flow<Either<Failure, List<BookDetails>>>

    suspend fun getBookSubject(text: String): Flow<Either<Failure, BookSubject>>
}
