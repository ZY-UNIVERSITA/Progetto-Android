package com.zyuniversita.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zyuniversita.domain.model.userdata.GeneralUserStats
import com.zyuniversita.ui.databinding.FragmentProfileRecycleViewBinding
import com.zyuniversita.ui.utils.mapper.FlagMapper

class ProfileAdapter(
    private val generalUserStats: MutableList<GeneralUserStats>,
    private val flagMapper: FlagMapper
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    inner class ViewHolder(private val fragmentProfileRecycleViewBinding: FragmentProfileRecycleViewBinding) :
        RecyclerView.ViewHolder(fragmentProfileRecycleViewBinding.root) {

        fun bind(stat: GeneralUserStats) {
            with(fragmentProfileRecycleViewBinding) {
                languageText.text = stat.languageName
                imageFlag.setImageResource(flagMapper.flagBind(stat.languageCode))

                completedQuizText.text = "Completed quiz: ${stat.completedQuiz}"
                correctText.text = "Correct answers: ${stat.correctAnswer}"
                incorrectText.text = "Incorrect answers: ${stat.wrongAnswer}"

                val totalAnswers: Int = (stat.correctAnswer + stat.wrongAnswer)
                val percentage: Double = if (totalAnswers == 0) {
                    0.0
                } else {
                    (stat.correctAnswer.toDouble() / totalAnswers * 100)
                }

                percentageText.text = "Correctness: ${String.format("%.1f", percentage)}%"
            }
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