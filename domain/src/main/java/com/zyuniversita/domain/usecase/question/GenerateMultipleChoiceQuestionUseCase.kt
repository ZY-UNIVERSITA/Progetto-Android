package com.zyuniversita.domain.usecase.question

import com.zyuniversita.domain.model.MultipleChoiceQuestion
import com.zyuniversita.domain.model.WordProgress
import com.zyuniversita.domain.repository.QuizWordRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.random.Random

interface GenerateMultipleChoiceQuestionUseCase {
    suspend operator fun invoke(quizWordsList: MutableList<WordProgress>): MultipleChoiceQuestion
}

class GenerateMultipleChoiceQuestionUseCaseImpl @Inject constructor(private val quizWordRepository: QuizWordRepository) :
    GenerateMultipleChoiceQuestionUseCase {
    override suspend fun invoke(quizWordsList: MutableList<WordProgress>): MultipleChoiceQuestion {
        val allWordsList = getAllWords()

        val word: WordProgress = quizWordsList.random()

        val allAnswers: MutableList<WordProgress> = mutableListOf()

        while (allAnswers.size < 3) {
            var wrongAnswer: WordProgress = allWordsList.random()

            while (allAnswers.contains(wrongAnswer) || wrongAnswer == word) {
                wrongAnswer = allWordsList.random()
            }

            allAnswers.add(wrongAnswer)
        }

        val correctAnswerPosition: Int = Random.nextInt(0, 4)
        allAnswers.add(correctAnswerPosition, word)

        println(allAnswers.size)

        return MultipleChoiceQuestion(word, allAnswers, correctAnswerPosition)
    }

    private suspend fun getAllWords(): List<WordProgress> {
        return quizWordRepository.wordsListByLanguage.first()
    }

}