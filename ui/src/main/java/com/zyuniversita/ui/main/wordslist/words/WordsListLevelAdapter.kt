package com.zyuniversita.ui.main.wordslist.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zyuniversita.domain.model.Word
import com.zyuniversita.ui.databinding.ItemLevelsForWordsListBinding


class WordsListLevelAdapter(
    private val wordsList: MutableList<MutableList<Word>>,
    private val chooseWord: (Int) -> Unit
) : RecyclerView.Adapter<WordsListLevelAdapter.ViewHolder>() {


    inner class ViewHolder(private val itemLevelsForWordsListBinding: ItemLevelsForWordsListBinding) :
        RecyclerView.ViewHolder(itemLevelsForWordsListBinding.root) {

        private var levelData: MutableList<Word> = mutableListOf()
        private val wordsAdapter = WordsListWordAdapter(levelData, chooseWord)

        init {
            itemLevelsForWordsListBinding.recycleView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = wordsAdapter
                setHasFixedSize(true)
            }
        }


        fun bind(word: MutableList<Word>) {
            itemLevelsForWordsListBinding.levelName.text = word.first().levelCode

            wordsAdapter.updateList(word)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLevelsForWordsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val word: MutableList<Word> = wordsList[position]

        holder.bind(word)
    }

    fun updateList(newList: MutableList<MutableList<Word>>) {
        wordsList.clear()
        wordsList.addAll(newList)
        notifyDataSetChanged()
    }
}