package com.zyuniversita.ui.games.multiplechoice

import com.zyuniversita.domain.model.MultipleChoiceQuestion
import com.zyuniversita.domain.usecase.preferences.FetchUserIdUseCase
import com.zyuniversita.domain.usecase.preferences.FetchWordRepetitionUseCase
import com.zyuniversita.domain.usecase.question.GenerateMultipleChoiceQuestionUseCase
import com.zyuniversita.domain.usecase.question.UpdateAnswerSheetUseCase
import com.zyuniversita.domain.usecase.question.UpdateRemainingWordUseCase
import com.zyuniversita.domain.usecase.userdata.UpdateUserDataUseCase
import com.zyuniversita.domain.usecase.userdata.UpdateUserPerformanceUseCase
import com.zyuniversita.domain.usecase.words.FetchWordsForQuizUseCase
import com.zyuniversita.ui.games.abstracts.AbstractGameFragmentViewModel
import com.zyuniversita.ui.games.writing.uistate.GameEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MultipleChoiceGameViewModel @Inject constructor(
    private val fetchWordsForQuizUseCase: FetchWordsForQuizUseCase,

    private val updateUserDataUseCase: UpdateUserDataUseCase,

    private val fetchWordRepetitionUseCase: FetchWordRepetitionUseCase,

    private val generateMultipleChoiceQuestionUseCase: GenerateMultipleChoiceQuestionUseCase,
    private val updateAnswerSheetUseCase: UpdateAnswerSheetUseCase,
    private val updateRemainingWordUseCase: UpdateRemainingWordUseCase,

    private val fetchUserIdUseCase: FetchUserIdUseCase,
    private val updateUserPerformanceUseCase: UpdateUserPerformanceUseCase,
) : AbstractGameFragmentViewModel(
    fetchWordsForQuizUseCase,
    updateUserDataUseCase,
    fetchWordRepetitionUseCase,
    updateAnswerSheetUseCase,
    updateRemainingWordUseCase,
    fetchUserIdUseCase,
    updateUserPerformanceUseCase
) {
    var word: MultipleChoiceQuestion? = null

    override suspend fun generateQuestion() {
        word = if (quizWordsList.size > 0) {
            generateMultipleChoiceQuestionUseCase(quizWordsList)
        } else {
            null
        }

        _uiEvent.send(GameEvents.NewWord)
    }

    override fun tempUpdate(correct: Boolean) {
        updateAnswerSheetUseCase(generatedWordsStat, word!!.question.word.wordId, correct)
        updateRemainingWordUseCase(wordRepetition, quizWordsList, word!!.question.word.wordId)
    }


}