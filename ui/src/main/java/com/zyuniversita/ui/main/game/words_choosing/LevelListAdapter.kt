package com.zyuniversita.ui.main.game.words_choosing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zyuniversita.domain.model.words.WordProgress
import com.zyuniversita.ui.databinding.ItemDifficultyLevelBinding

class LevelListAdapter(
    private val wordsList: MutableList<MutableList<WordProgress>>,
    private val changeWordSelection: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<LevelListAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemDifficultyLevelBinding: ItemDifficultyLevelBinding) :
        RecyclerView.ViewHolder(itemDifficultyLevelBinding.root) {

        private val wordsAdapter = WordListAdapter(changeWordSelection, ::allChildrenCheck)
        private var levelData: MutableList<WordProgress> = mutableListOf()

        init {
            itemDifficultyLevelBinding.recycleView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = wordsAdapter
                setHasFixedSize(true)
            }
        }

        private val levelListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                levelData.forEachIndexed { index, wordProgress ->
                    changeWordSelection(wordProgress.word.wordId, isChecked)
                    levelData[index] = wordProgress.copy(selected = isChecked)
                }

                wordsAdapter.toggleAll()
            }


        fun bind(position: Int, levelData: MutableList<WordProgress>) {
            this.levelData = levelData

            itemDifficultyLevelBinding.checkBoxLevel.setOnCheckedChangeListener(null)

            itemDifficultyLevelBinding.checkBoxLevel.text = levelData.first().word.levelCode
            itemDifficultyLevelBinding.checkBoxLevel.isChecked = levelData.all { it.selected }

            itemDifficultyLevelBinding.checkBoxLevel.setOnCheckedChangeListener(levelListener)

            wordsAdapter.submitList(position, levelData)

            itemDifficultyLevelBinding.recycleView.visibility = View.GONE

            itemDifficultyLevelBinding.expand.setOnClickListener {
                val recyclerView = itemDifficultyLevelBinding.recycleView

                recyclerView.visibility = if (recyclerView.isVisible) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }

        private fun allChildrenCheck(position: Int, checked: Boolean): Unit {
            // Is need to avoid when a children is unchecked, to update the list of all children to unchecked
            // At the same time, the parent checkbox will be put in unchecked state
            // The same thing work when all children will be put in checked mode
            itemDifficultyLevelBinding.checkBoxLevel.setOnCheckedChangeListener(null)
//            itemDifficultyLevelBinding.checkBoxLevel.isChecked = allChecked
            changeWordSelection(levelData[position].word.wordId, checked)
            levelData[position] = levelData[position].copy(selected = checked)
            itemDifficultyLevelBinding.checkBoxLevel.isChecked = levelData.all { it.selected }
            itemDifficultyLevelBinding.checkBoxLevel.setOnCheckedChangeListener(levelListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDifficultyLevelBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val levelData: MutableList<WordProgress> = wordsList[position]

        holder.bind(position, levelData)
    }

    fun updateList(newList: MutableList<MutableList<WordProgress>>) {
        wordsList.clear()
        wordsList.addAll(newList)
        notifyDataSetChanged()
    }
}