package com.wellenkugel.bookai.core.di

import com.wellenkugel.bookai.BuildConfig
import com.wellenkugel.bookai.features.characters.data.remote.api.BooksRapidApi
import com.wellenkugel.bookai.features.characters.data.repository.BooksRapidRepository
import com.wellenkugel.bookai.features.characters.domain.repository.IBooksRapidRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://books17.p.rapidapi.com/")
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideStarWarService(retrofit: Retrofit): BooksRapidApi {
        return retrofit.create(BooksRapidApi::class.java)
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindCharacterRepository(repo: BooksRapidRepository): IBooksRapidRepository
}
