package com.zyuniversita.domain.usecase.question

import com.zyuniversita.domain.model.words.WordProgress
import javax.inject.Inject

interface GenerateWritingQuestionUseCase: (MutableList<WordProgress>) -> WordProgress

class GenerateWritingQuestionUseCaseImpl @Inject constructor(): GenerateWritingQuestionUseCase {
    override fun invoke(wordsList: MutableList<WordProgress>): WordProgress {
        return wordsList.random()
    }

}