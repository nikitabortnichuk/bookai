package com.wellenkugel.bookai.features.characters.domain.usecases

import com.wellenkugel.bookai.core.baseinteractor.BaseUseCase
import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.core.functional.Either
import com.wellenkugel.bookai.features.characters.domain.model.Message
import com.wellenkugel.bookai.features.characters.domain.repository.IBooksRapidRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedMessagesUseCase @Inject constructor(private val iBooksRapidRepository: IBooksRapidRepository) :
    BaseUseCase<String, List<Message>>() {

    override suspend fun run(params: String): Flow<Either<Failure, List<Message>>> {
        return iBooksRapidRepository.getAllMessages()
    }
}
