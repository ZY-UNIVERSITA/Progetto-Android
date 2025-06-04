package com.zyuniversita.ui.games.results

import androidx.lifecycle.ViewModel
import com.zyuniversita.domain.model.userdata.GeneratedWordsStats
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor() : ViewModel() {
    private val _wordsStats: MutableMap<Int, GeneratedWordsStats> = mutableMapOf()
    val totalQuestions: Int get() = calculateTotalQuestions()
    val correctQuestions: Int get() = calculateCorrectQuestions()
    val incorrectQuestions: Int get() = calculateIncorrectQuestions()

    private fun calculateTotalQuestions(): Int {
        return _wordsStats.values.sumOf { it.correct + it.incorrect }
    }

    private fun calculateCorrectQuestions(): Int {
        return _wordsStats.values.sumOf { it.correct }
    }

    private fun calculateIncorrectQuestions(): Int {
        return _wordsStats.values.sumOf { it.incorrect }
    }

    fun updateWordsStats(wordsStats: MutableMap<Int, GeneratedWordsStats>) {
        this._wordsStats.clear()
        this._wordsStats.putAll(wordsStats)

        println(_wordsStats.values)
        println(_wordsStats.values.sumOf { it.correct + it.incorrect })
        println(_wordsStats.values.sumOf { it.correct })
        println(_wordsStats.values.sumOf { it.incorrect })
    }
}