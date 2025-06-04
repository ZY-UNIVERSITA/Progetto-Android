package com.zyuniversita.ui.utils.mapper

import com.zyuniversita.ui.R
import javax.inject.Inject

class FlagMapper @Inject constructor() {
    fun flagBind(languageFlag: String): Int =
        when (languageFlag) {
            "ch" -> R.drawable.flag_china
            "jp" -> R.drawable.flag_japan
            "kr" -> R.drawable.flag_korea
            else -> throw IllegalArgumentException("Flag not found.")
        }
}