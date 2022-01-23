package com.wellenkugel.bookai.core.di

import android.content.Context
import androidx.room.Room
import com.wellenkugel.bookai.BuildConfig
import com.wellenkugel.bookai.core.audio.NotificationSound
import com.wellenkugel.bookai.features.characters.data.local.MessageChatDatabase
import com.wellenkugel.bookai.features.characters.data.remote.api.BooksRapidApi
import com.wellenkugel.bookai.features.characters.data.remote.api.WitAiApi
import com.wellenkugel.bookai.features.characters.data.repository.BooksRapidRepository
import com.wellenkugel.bookai.features.characters.domain.repository.IBooksRapidRepository
import com.wellenkugel.bookai.features.characters.presentation.adapter.ChatMessagesAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("booksList")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://books17.p.rapidapi.com/")
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("witAi")
    fun provideRetrofitForWitAi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.wit.ai/")
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
    fun provideBooksRapidService(@Named("booksList") retrofit: Retrofit): BooksRapidApi {
        return retrofit.create(BooksRapidApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWitAiService(@Named("witAi") retrofit: Retrofit): WitAiApi {
        return retrofit.create(WitAiApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNotificationSound(@ApplicationContext appContext: Context): NotificationSound =
        NotificationSound(appContext)

    @Singleton
    @Provides
    fun provideMessageAdapter() = ChatMessagesAdapter()
}


@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindCharacterRepository(repo: BooksRapidRepository): IBooksRapidRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MessageChatDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MessageChatDatabase::class.java,
            MessageChatDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesCharacterDao(db: MessageChatDatabase) = db.charactersDao()
}
