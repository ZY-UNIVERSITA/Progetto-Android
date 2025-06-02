package com.zyuniversita.domain.usecase.mapping

import com.zyuniversita.domain.model.words.WordProgress
import javax.inject.Inject

interface GroupWordsAndUserDataByLevelUseCase : (List<WordProgress>) -> MutableList<MutableList<WordProgress>>

class GroupWordsAndUserDataByLevelUseCaseImpl @Inject constructor() : GroupWordsAndUserDataByLevelUseCase {
    override fun invoke(wordList: List<WordProgress>): MutableList<MutableList<WordProgress>> {
        // create a map using levelCode as a key and sort the by levelCode
        // In each map, sort the list by wordId and return a mutableList
        // Create a mutable list from the map
        val list = wordList.groupBy { it.word.levelCode }
            .toSortedMap().values.map { it.sortedBy { data -> data.word.wordId }.toMutableList() }
            .toMutableList()

        return list
    }
}