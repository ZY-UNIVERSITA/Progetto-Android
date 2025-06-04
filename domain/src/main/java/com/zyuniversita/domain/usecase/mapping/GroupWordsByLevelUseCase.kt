package com.zyuniversita.domain.usecase.mapping

import com.zyuniversita.domain.model.words.Word
import javax.inject.Inject

interface GroupWordsByLevelUseCase : (List<Word>) -> MutableList<MutableList<Word>>

class GroupWordsByLevelUseCaseImpl @Inject constructor() : GroupWordsByLevelUseCase {
    override fun invoke(wordsList: List<Word>): MutableList<MutableList<Word>> {
        val list = wordsList.groupBy { it.levelCode }
            .toSortedMap().values.map { it.sortedBy { data -> data.wordId }.toMutableList() }
            .toMutableList()

        return list
    }

}