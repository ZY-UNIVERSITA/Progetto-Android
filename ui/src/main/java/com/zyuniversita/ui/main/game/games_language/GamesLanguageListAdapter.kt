package com.zyuniversita.ui.main.game.games_language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zyuniversita.domain.model.AvailableLanguage
import com.zyuniversita.ui.databinding.LanguageListCardViewBinding
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import com.zyuniversita.ui.utils.mapper.FlagMapper

class GamesLanguageListAdapter(
    private val availableLanguage: MutableList<AvailableLanguage>,
    private val selectLanguage: (Page, String) -> Unit,
    private val flagMapper: FlagMapper
) : RecyclerView.Adapter<GamesLanguageListAdapter.ViewHolder>() {

    inner class ViewHolder(private val languageListCardViewBinding: LanguageListCardViewBinding) :
        RecyclerView.ViewHolder(languageListCardViewBinding.root) {

        fun bind(language: AvailableLanguage) {
            languageListCardViewBinding.nameFlag.text = language.languageName
            languageListCardViewBinding.imageFlag.setImageResource(flagMapper.flagBind(language.code))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LanguageListCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return availableLanguage.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val language: AvailableLanguage = availableLanguage[position]

        holder.itemView.setOnClickListener {
            selectLanguage(Page.CHOOSE_WORDS, language.code)
        }

        holder.bind(language)
    }

    fun updateList(newList: MutableList<AvailableLanguage>) {
        availableLanguage.clear()
        availableLanguage.addAll(newList)
        notifyDataSetChanged()
    }
}