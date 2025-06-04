package com.zyuniversita.domain.model.userdata

data class WordsUserData(
    val wordId: Int,
    val selected: Boolean = false,
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val lastSeen: Long? = null
)
