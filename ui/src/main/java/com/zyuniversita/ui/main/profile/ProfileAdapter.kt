package com.zyuniversita.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zyuniversita.domain.model.userdata.GeneralUserStats
import com.zyuniversita.ui.R
import com.zyuniversita.ui.databinding.FragmentProfileRecycleViewBinding

class ProfileAdapter(
    private val generalUserStats: MutableList<GeneralUserStats>,
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    inner class ViewHolder(private val fragmentProfileRecycleViewBinding: FragmentProfileRecycleViewBinding) :
        RecyclerView.ViewHolder(fragmentProfileRecycleViewBinding.root) {

        fun bind(stat: GeneralUserStats) {
            with(fragmentProfileRecycleViewBinding) {
                languageText.text = stat.languageName
                imageFlag.setImageResource(flagBind(stat.languageCode))

                completedQuizText.text = "Completed quiz: ${stat.completedQuiz}"
                correctText.text = "Correct answers: ${stat.correctAnswer}"
                incorrectText.text = "Incorrect answers: ${stat.wrongAnswer}"

                val totalAnswers: Int = (stat.correctAnswer + stat.wrongAnswer)
                val percentage: Double = if (totalAnswers == 0) {
                    0.0
                } else {
                    (stat.correctAnswer.toDouble() / totalAnswers * 100)
                }

                percentageText.text = "Correctness: $percentage%"
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
            FragmentProfileRecycleViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return generalUserStats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stat: GeneralUserStats = generalUserStats[position]

        holder.bind(stat)
    }

    fun updateList(newList: List<GeneralUserStats>) {
        generalUserStats.clear()
        generalUserStats.addAll(newList)
        notifyDataSetChanged()
    }

}