package com.wellenkugel.bookai.features.characters.domain.usecases

import com.wellenkugel.bookai.core.baseinteractor.BaseUseCase
import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.core.functional.Either
import com.wellenkugel.bookai.features.characters.data.local.model.MessageEntity
import com.wellenkugel.bookai.features.characters.domain.repository.IBooksRapidRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveMessageIntoDatabaseUseCase @Inject constructor(
    private val iBooksRapidRepository: IBooksRapidRepository
) : BaseUseCase<MessageEntity, Unit>() {

    override suspend fun run(params: MessageEntity): Flow<Either<Failure, Unit>> {
        return flow { iBooksRapidRepository.insertMessage(params) }
    }
}