package com.wellenkugel.bookai.features.characters.data.remote.api

import com.wellenkugel.bookai.features.characters.data.remote.model.WitAiSubjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WitAiApi {

    @GET("message?v=20220123/")
    @Headers("")
    suspend fun getBookSubjectSuggestion(@Query("q") text: String): Response<WitAiSubjectResponse>
}