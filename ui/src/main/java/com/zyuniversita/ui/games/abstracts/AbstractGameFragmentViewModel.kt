package com.zyuniversita.ui.games.abstracts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyuniversita.domain.model.userdata.GeneratedWordsStats
import com.zyuniversita.domain.model.words.WordProgress
import com.zyuniversita.domain.usecase.preferences.FetchUserIdUseCase
import com.zyuniversita.domain.usecase.preferences.FetchWordRepetitionUseCase
import com.zyuniversita.domain.usecase.question.UpdateAnswerSheetUseCase
import com.zyuniversita.domain.usecase.question.UpdateRemainingWordUseCase
import com.zyuniversita.domain.usecase.userdata.UpdateUserDataUseCase
import com.zyuniversita.domain.usecase.userdata.UpdateUserPerformanceUseCase
import com.zyuniversita.domain.usecase.words.FetchWordsForQuizUseCase
import com.zyuniversita.ui.games.uistate.GameEvents
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class AbstractGameFragmentViewModel(
    private val fetchWordsForQuizUseCase: FetchWordsForQuizUseCase,

    private val updateUserDataUseCase: UpdateUserDataUseCase,

    private val fetchWordRepetitionUseCase: FetchWordRepetitionUseCase,

    private val updateAnswerSheetUseCase: UpdateAnswerSheetUseCase,
    private val updateRemainingWordUseCase: UpdateRemainingWordUseCase,

    private val fetchUserIdUseCase: FetchUserIdUseCase,
    private val updateUserPerformanceUseCase: UpdateUserPerformanceUseCase,
) : ViewModel() {
    protected lateinit var quizWordsList: MutableList<WordProgress>
    protected var generatedWordsStat: MutableMap<Int, GeneratedWordsStats> = mutableMapOf()
    val wordsStats: Map<Int, GeneratedWordsStats> get() = generatedWordsStat.toMap()

    private lateinit var _languageCode: String
    protected var wordRepetition: Boolean = false

    protected val _uiEvent: Channel<GameEvents> =
        Channel<GameEvents>(Channel.BUFFERED)
    val uiEvent: Flow<GameEvents> = _uiEvent.receiveAsFlow()

    fun fetchData() {
        viewModelScope.launch {
            val quizListDeferred = async { fetchWordsForQuizUseCase().first().toMutableList() }
            val repetitionDeferred = async { fetchWordRepetitionUseCase().first() }

            quizWordsList = quizListDeferred.await()
            wordRepetition = repetitionDeferred.await()

            _languageCode = quizWordsList.first().word.languageCode

            _uiEvent.send(GameEvents.DataLoaded)
        }
    }

    fun saveSession() {
        viewModelScope.launch {
            updateUserDataUseCase(generatedWordsStat)

            val userId: Long? = fetchUserIdUseCase().first()

            userId?.let {

                val correctAnswers = generatedWordsStat.values.sumOf { it.correct }
                val incorrectAnswers = generatedWordsStat.values.sumOf { it.incorrect }

                updateUserPerformanceUseCase(
                    userId,
                    _languageCode,
                    correctAnswers,
                    incorrectAnswers
                )
            }

            _uiEvent.send(GameEvents.GameEnded)
        }

    }

    abstract suspend fun generateQuestion()
    abstract fun tempUpdate(correct: Boolean)
}