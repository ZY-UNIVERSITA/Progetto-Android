package com.zyuniversita.domain.model.userdata

data class UserQuizPerformanceStats(
    val userId: Long,
    val languageCode: String,
    val completedQuiz: Int ,
    val correctAnswer: Int,
    val wrongAnswer: Int
)