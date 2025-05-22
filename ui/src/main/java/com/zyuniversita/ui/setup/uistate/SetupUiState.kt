package com.zyuniversita.ui.setup.uistate

/**
 * Represents the UI state for the setup process.
 *
 * @property username The username provided during setup. It can be null if not set.
 * @property isDatabaseUpdating Indicates whether the database is currently updating.
 * @property isUsernameMissing Indicates whether the username is missing.
 * @property isUserIdMissing Indicates whether the user ID is missing.
 * @property isLanguageLoading Indicates whether the language resources are still loading.
 *
 * The setup is considered complete when the database is not updating, and none of the username,
 * user ID, or language fields are missing/loading.
 */
data class SetupUiState(
    val username: String? = null,
    val isDatabaseUpdating: Boolean = true,
    val isUsernameMissing: Boolean = true,
    val isUserIdMissing: Boolean = true,
    val isLanguageLoading: Boolean = true,
) {
    /**
     * Returns true if the setup process is complete, meaning that:
     * - The database is not updating.
     * - The username exists.
     * - The user ID exists.
     * - The language has finished loading.
     */
    val isSetupCompleted: Boolean
        get() = !isDatabaseUpdating && !isUsernameMissing && !isUserIdMissing && !isLanguageLoading

    /**
     * Calculates the completion percentage of the setup.
     *
     * It counts how many of the required steps (database updating, username, user ID,
     * and language loading) are complete (i.e., the corresponding condition is false)
     * and returns the progress percentage based on a scale of 0 to 100.
     *
     * @return An integer representing the completion percentage of the setup.
     */
    val percentage: Int
        get() {
            // Create a list representing the state of each condition.
            val listBoolean: List<Boolean> = listOf(isDatabaseUpdating, isUsernameMissing, isUserIdMissing, isLanguageLoading)
            // Count the conditions that are completed (i.e., false).
            val numberOfTrue = listBoolean.count { !it }
            // Calculate the percentage of completed steps.
            return numberOfTrue * 100 / listBoolean.size
        }
}
