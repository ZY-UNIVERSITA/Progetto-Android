package com.zyuniversita.ui.main.game.words_choosing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zyuniversita.domain.model.words.WordProgress
import com.zyuniversita.ui.databinding.ItemWordsListBinding

class WordListAdapter(
    private val changeWordSelection: (Int, Boolean) -> Unit,
    private val allChildrenChecked: (Int, Boolean) -> Unit,
) : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {
    private var position: Int = 0
    private var wordList: MutableList<WordProgress> = mutableListOf()

    inner class ViewHolder(private val itemWordsListBinding: ItemWordsListBinding) :
        RecyclerView.ViewHolder(itemWordsListBinding.root) {

        fun bind(word: WordProgress, position: Int) {
            itemWordsListBinding.cbWord.setOnCheckedChangeListener(null)

            itemWordsListBinding.cbWord.text = word.word.word

            itemWordsListBinding.cbWord.isChecked = word.selected

            itemWordsListBinding.cbWord.setOnCheckedChangeListener { buttonView, isChecked ->
                allChildrenChecked(position, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWordsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val word: WordProgress = wordList[position]

        holder.bind(word, position)
    }

    fun submitList(position: Int, newList: MutableList<WordProgress>) {
        this.position = position
        wordList = newList
        notifyDataSetChanged()
    }

    fun toggleAll() {
        notifyDataSetChanged()

    }
}