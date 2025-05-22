package com.zyuniversita.domain.usecase.question

import com.zyuniversita.domain.model.WordProgress
import javax.inject.Inject

interface UpdateRemainingWordUseCase: (Boolean, MutableList<WordProgress>, Int) -> Unit

class UpdateRemainingWordUseCaseImpl @Inject constructor(): UpdateRemainingWordUseCase {
    override fun invoke(wordRepetition: Boolean, wordsList: MutableList<WordProgress>, wordId: Int) {
        if (!wordRepetition) {
            wordsList.removeIf { it -> it.word.wordId == wordId }
        }
    }

}