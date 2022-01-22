package com.wellenkugel.bookai.features.characters.domain.usecases

import com.wellenkugel.bookai.core.baseinteractor.BaseUseCase
import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.core.functional.Either
import com.wellenkugel.bookai.features.characters.domain.model.BookSubject
import com.wellenkugel.bookai.features.characters.domain.repository.IBooksRapidRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookSubjectFromQuestionUseCase @Inject constructor(private val iBooksRapidRepository: IBooksRapidRepository) :
    BaseUseCase<String, BookSubject>() {

    override suspend fun run(params: String): Flow<Either<Failure, BookSubject>> {
        return iBooksRapidRepository.getBookSubject(params)
    }
}
