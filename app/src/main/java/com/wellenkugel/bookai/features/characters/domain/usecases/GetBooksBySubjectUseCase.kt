package com.wellenkugel.bookai.features.characters.domain.usecases

import com.wellenkugel.bookai.core.baseinteractor.BaseUseCase
import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.core.functional.Either
import com.wellenkugel.bookai.features.characters.domain.model.BookDetails
import com.wellenkugel.bookai.features.characters.domain.repository.IBooksRapidRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksBySubjectUseCase @Inject constructor(private val characterRepository: IBooksRapidRepository) :
    BaseUseCase<String, List<BookDetails>>() {

    override suspend fun run(params: String): Flow<Either<Failure, List<BookDetails>>> {
        return characterRepository.searchBooksBySubject(params)
    }
}
