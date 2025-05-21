package com.zyuniversita.data.di

import com.zyuniversita.data.repository.ImageRecognitionRepositoryImpl
import com.zyuniversita.data.repository.LanguageRepositoryImpl
import com.zyuniversita.data.repository.PreferencesRepositoryImpl
import com.zyuniversita.data.repository.QuizWordRepositoryImpl
import com.zyuniversita.data.repository.UserDataRepositoryImpl
import com.zyuniversita.domain.repository.ImageRecognitionRepository
import com.zyuniversita.domain.repository.LanguageRepository
import com.zyuniversita.domain.repository.PreferencesRepository
import com.zyuniversita.domain.repository.QuizWordRepository
import com.zyuniversita.domain.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides dependency injection bindings for all repository interfaces
 * used in the application.
 *
 * This module is installed in the [SingletonComponent], meaning that all bound instances
 * will have a singleton lifecycle and be available throughout the entire application.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryProviderModule {

    /**
     * Binds [QuizWordRepositoryImpl] to its interface [QuizWordRepository].
     *
     * @param quizWordRepositoryImpl The implementation to bind.
     * @return The bound interface used for quiz word operations.
     */
    @Binds
    @Singleton
    abstract fun bindQuizRepository(
        quizWordRepositoryImpl: QuizWordRepositoryImpl
    ): QuizWordRepository

    /**
     * Binds [LanguageRepositoryImpl] to [LanguageRepository].
     *
     * @param languageRepositoryImpl The implementation to bind.
     * @return The bound interface used for language-related data.
     */
    @Binds
    @Singleton
    abstract fun bindLanguageRepository(
        languageRepositoryImpl: LanguageRepositoryImpl
    ): LanguageRepository

    /**
     * Binds [ImageRecognitionRepositoryImpl] to [ImageRecognitionRepository].
     *
     * @param imageRecognitionRepositoryImpl The implementation to bind.
     * @return The bound interface for image recognition features.
     */
    @Binds
    @Singleton
    abstract fun bindImageRecognitionRepository(
        imageRecognitionRepositoryImpl: ImageRecognitionRepositoryImpl
    ): ImageRecognitionRepository

    /**
     * Binds [PreferencesRepositoryImpl] to [PreferencesRepository].
     *
     * @param preferencesRepositoryImpl The implementation to bind.
     * @return The bound interface for app settings and preferences.
     */
    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository

    /**
     * Binds [UserDataRepositoryImpl] to [UserDataRepository].
     *
     * @param userDataRepositoryImpl The implementation to bind.
     * @return The bound interface for managing user-related data and stats.
     */
    @Binds
    @Singleton
    abstract fun bindUserDataRepository(
        userDataRepositoryImpl: UserDataRepositoryImpl
    ): UserDataRepository
}
