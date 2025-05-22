package com.zyuniversita.domain.usecase.words

import com.zyuniversita.domain.repository.QuizWordRepository

interface StartFetchWordsByLanguageAndLevelUseCase: (String, String) -> Unit

class StartFetchWordsByLanguageAndLevelUseCaseImpl(private val quizWordRepository: QuizWordRepository): StartFetchWordsByLanguageAndLevelUseCase {
    override fun invoke(lang: String, level: String): Unit {
        quizWordRepository.wordsByLanguageAndLevel(lang, level)
    }
}