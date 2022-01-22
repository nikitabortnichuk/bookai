package com.wellenkugel.bookai.features.characters.data.remote.api

import com.wellenkugel.bookai.features.characters.data.remote.model.BooksSubjectRapidResponse
import com.wellenkugel.bookai.features.characters.data.remote.request.BookBySubjectRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface BooksRapidApi {

    @POST("works/subject")
    @Headers("")
    suspend fun searchBooksBySubject(
        @Body bookBySubjectRequestBody: BookBySubjectRequestBody
    ): Response<BooksSubjectRapidResponse>
}
