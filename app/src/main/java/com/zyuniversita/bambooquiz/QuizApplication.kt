package com.zyuniversita.bambooquiz

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom [Application] class for the Quiz app.
 *
 * Annotated with [HiltAndroidApp], this class triggers Hilt's code generation,
 * including a base application class that can be used to inject dependencies
 * into other Android components such as Activities, Fragments, Services, etc.
 *
 * This is the entry point of the application, and [onCreate] can be used for
 * global initialization logic.
 */
@HiltAndroidApp
class QuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // TODO: delete later
        // UseCaseProvider.setup(
        //     repositoryProvider = RepositoryProviderImpl(context = this.applicationContext)
        // )
    }
}
