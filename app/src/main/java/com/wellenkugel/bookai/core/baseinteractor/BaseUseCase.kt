package com.wellenkugel.bookai.core.baseinteractor

import com.wellenkugel.bookai.core.exception.Failure
import com.wellenkugel.bookai.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseUseCase<in Params, out Type> where Type : Any {

    abstract suspend fun run(params: Params): Flow<Either<Failure, Type>>

    open operator fun invoke(
        job: Job,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val backgroundJob = CoroutineScope(job + Dispatchers.IO).async { run(params) }
        CoroutineScope(job + Dispatchers.Main).launch {
            val await = backgroundJob.await()
//            await.catch {
//                onResult(Either.Left(Failure.ServerError))
//            }.collect { d -> onResult(d) }
            await.collect { d -> onResult(d) }
        }
    }
}
