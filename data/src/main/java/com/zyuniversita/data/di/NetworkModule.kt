package com.zyuniversita.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zyuniversita.data.remote.imagerecognition.api.ImageRecognitionApi
import com.zyuniversita.data.remote.wordDatabase.api.WordDatabaseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Hilt module that wires up all networking-related dependencies.
 *
 * • Installed in the [SingletonComponent], so everything provided here lives
 *   as long as the application process.
 * • Exposes providers for Base URL, OkHttp, Moshi, Retrofit, and the two
 *   REST API interfaces.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Supplies the base URL used by Retrofit for every HTTP request.
     *
     * @return A String representing the root endpoint of the backend.
     */
    @Provides
    fun provideBaseUrl(): String = "https://url/"

    /**
     * Builds and returns an [OkHttpClient] instance.
     * TODO IF NEEDED: Add interceptors, timeouts, or certificate pinning here.
     *
     * @return Configured [OkHttpClient].
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            // .addInterceptor(HttpLoggingInterceptor().apply { level = BODY })
            .build()
    }

    /**
     * Returns a [Moshi] instance with support for Kotlin data classes.
     *
     * @return Configured [Moshi] object.
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * Creates and supplies the main [Retrofit] instance used by the app.
     *
     * @param baseUrl      The base URL produced by [provideBaseUrl].
     * @param okHttpClient The HTTP client produced by [provideOkHttpClient].
     * @param moshi        The JSON serializer produced by [provideMoshi].
     *
     * @return Fully configured [Retrofit] instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /**
     * Provides the Retrofit implementation of [WordDatabaseApi].
     *
     * @param retrofit The Retrofit instance injected from [provideRetrofit].
     *
     * @return Runtime implementation of [WordDatabaseApi].
     */
    @Provides
    @Singleton
    fun provideWordDatabaseApi(
        retrofit: Retrofit
    ): WordDatabaseApi {
        return retrofit.create(WordDatabaseApi::class.java)
    }

    /**
     * Provides the Retrofit implementation of [ImageRecognitionApi].
     *
     * @param retrofit The Retrofit instance injected from [provideRetrofit].
     *
     * @return Runtime implementation of [ImageRecognitionApi].
     */
    @Provides
    @Singleton
    fun provideImageRecognitionApi(
        retrofit: Retrofit
    ): ImageRecognitionApi {
        return retrofit.create(ImageRecognitionApi::class.java)
    }
}
