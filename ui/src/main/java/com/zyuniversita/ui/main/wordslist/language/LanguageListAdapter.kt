package com.zyuniversita.ui.main.wordslist.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zyuniversita.domain.model.AvailableLanguage
import com.zyuniversita.ui.R
import com.zyuniversita.ui.databinding.LanguageListCardViewBinding


class LanguageListAdapter(
    private val availableLanguage: MutableList<AvailableLanguage>,
    private val selectLanguage: (String) -> Unit
) : RecyclerView.Adapter<LanguageListAdapter.ViewHolder>() {

    inner class ViewHolder(private val languageListCardViewBinding: LanguageListCardViewBinding) :
        RecyclerView.ViewHolder(languageListCardViewBinding.root) {

        fun bind(language: AvailableLanguage) {
            with(languageListCardViewBinding) {
                nameFlag.text = language.languageName
                imageFlag.setImageResource(flagBind(language.code))

                root.setOnClickListener(null)
                root.setOnClickListener {
                    selectLanguage(language.code)
                }
            }
        }

        private fun flagBind(languageFlag: String): Int =
            when (languageFlag) {
                "ch" -> R.drawable.flag_china
                "jp" -> R.drawable.flag_japan
                "kr" -> R.drawable.flag_korea
                else -> R.drawable.flag_china
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LanguageListCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return availableLanguage.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val language: AvailableLanguage = availableLanguage[position]

        holder.bind(language)
    }

    fun updateList(newList: MutableList<AvailableLanguage>) {
        availableLanguage.clear()
        availableLanguage.addAll(newList)
        notifyDataSetChanged()
    }
}