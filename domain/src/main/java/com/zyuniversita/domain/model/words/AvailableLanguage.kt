package com.zyuniversita.domain.model.words

import com.squareup.moshi.Json

/**
 * Represents a language available in the application for selection,
 * typically used in features such as quizzes, translations, or app settings.
 *
 * This class is also designed for JSON serialization/deserialization,
 * with field mappings provided by [@Json] annotations to match expected key names
 * in a remote or local JSON payload.
 *
 * @property code The language code (e.g., "en" for English, "it" for Italian).
 * @property languageName The display name of the language (e.g., "English").
 * @property languageFlag An optional resource ID for the language's flag icon.
 *                        Defaults to 0 and can be set later after localization or UI binding.
 */
data class AvailableLanguage(
    @Json(name = "code") val code: String,
    @Json(name = "name") val languageName: String,
    @Json(name = "flag") var languageFlag: Int = 0
)
