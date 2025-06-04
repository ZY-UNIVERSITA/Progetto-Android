package com.zyuniversita.ui.main.wordslist.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zyuniversita.domain.model.words.Word
import com.zyuniversita.ui.databinding.ItemWordsForWordsListBinding

class WordsListWordAdapter(
    private val wordsList: MutableList<Word>,
    private val chooseWord: (Int) -> Unit
) : RecyclerView.Adapter<WordsListWordAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemWordsForWordsListBinding: ItemWordsForWordsListBinding) :
        RecyclerView.ViewHolder(itemWordsForWordsListBinding.root) {

        fun bind(word: Word) {
            itemWordsForWordsListBinding.root.setOnClickListener(null)

            itemWordsForWordsListBinding.textView.text = word.word
            itemWordsForWordsListBinding.root.setOnClickListener {
                chooseWord(word.wordId)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWordsForWordsListBinding.inflate(
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
        val word: Word = wordsList[position]

        holder.itemView.setOnClickListener {
//            selectLanguage("choose_words", language.code)
        }

        holder.bind(word)
    }

    fun updateList(newList: MutableList<Word>) {
        wordsList.clear()
        wordsList.addAll(newList)
        notifyDataSetChanged()
    }
}