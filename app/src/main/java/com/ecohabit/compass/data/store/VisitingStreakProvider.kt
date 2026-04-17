package com.ecohabit.compass.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class VisitingStreakProvider(
    private val dataStore: DataStore<Preferences>
) {

    val visitingStreak: Flow<Int> = dataStore.data.map { prefs ->
        prefs[EcoPreferences.VISITING_STREAK] ?: 0
    }

    val canCheckToday: Flow<Boolean> = dataStore.data.map { prefs ->
        val lastCheckedEpochDay = prefs[EcoPreferences.LAST_VISIT_EPOCH_DAY]
        lastCheckedEpochDay != currentEpochDay()
    }

    suspend fun checkVisitToday(): CheckResult {
        var result = CheckResult.ALREADY_CHECKED

        dataStore.edit { prefs ->
            val todayEpochDay = currentEpochDay()
            val lastCheckedEpochDay = prefs[EcoPreferences.LAST_VISIT_EPOCH_DAY]

            if (lastCheckedEpochDay == todayEpochDay) {
                result = CheckResult.ALREADY_CHECKED
                return@edit
            }

            val currentStreak = prefs[EcoPreferences.VISITING_STREAK] ?: 0
            val isConsecutiveDay = lastCheckedEpochDay == todayEpochDay - 1
            val newStreak = if (isConsecutiveDay) currentStreak + 1 else 1

            prefs[EcoPreferences.VISITING_STREAK] = newStreak
            prefs[EcoPreferences.LAST_VISIT_EPOCH_DAY] = todayEpochDay
            result = CheckResult.CHECKED
        }

        return result
    }

    private fun currentEpochDay(): Long = LocalDate.now().toEpochDay()
}

enum class CheckResult {
    CHECKED,
    ALREADY_CHECKED
}
