package com.zyuniversita.domain.usecase.question

import com.zyuniversita.domain.model.userdata.GeneratedWordsStats
import javax.inject.Inject

interface UpdateAnswerSheetUseCase: (MutableMap<Int, GeneratedWordsStats>, Int, Boolean) -> Unit

class UpdateAnswerSheetUseCaseImpl @Inject constructor(): UpdateAnswerSheetUseCase {
    override fun invoke(wordsList: MutableMap<Int, GeneratedWordsStats>, wordId: Int, correctAnswer: Boolean) {
        val correctUpdate = if (correctAnswer) 1 else 0
        val incorrectUpdate = if (correctAnswer) 0 else 1

        wordsList[wordId] = wordsList[wordId]?.let { stats ->
            stats.copy(
                correct = stats.correct + correctUpdate,
                incorrect = stats.incorrect + incorrectUpdate,
            )
        } ?: GeneratedWordsStats(
            correct = correctUpdate,
            incorrect = incorrectUpdate
        )
    }
}