package com.zyuniversita.domain.model.settings

/**
 * Represents a set of user-defined settings that can be saved to persistent storage.
 *
 * This data class is typically used to encapsulate relevant information to persist,
 * such as the user's name and their preferences related to word repetition.
 *
 * @property username The name or identifier of the user.
 * @property repetition Indicates whether word repetition is enabled in the quiz.
 */
data class SettingsToSave(
    val username: String,
    val repetition: Boolean
)
