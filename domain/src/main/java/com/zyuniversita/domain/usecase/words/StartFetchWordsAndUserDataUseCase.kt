package com.zyuniversita.domain.usecase.words

import com.zyuniversita.domain.repository.QuizWordRepository

interface StartFetchWordsAndUserDataUseCase: (String, String) -> Unit

class StartFetchWordsAndUserDataUseCaseImpl(private val quizWordRepository: QuizWordRepository): StartFetchWordsAndUserDataUseCase {
    override fun invoke(lang: String, level: String) {

    }

}