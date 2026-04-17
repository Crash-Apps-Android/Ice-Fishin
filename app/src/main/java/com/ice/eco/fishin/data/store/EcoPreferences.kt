package com.ice.eco.fishin.data.store

import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

object EcoPreferences {
    val TOTAL_SAVINGS = floatPreferencesKey("total_savings")
    val TREES_SAVED = intPreferencesKey("trees_saved")
    val VISITING_STREAK = intPreferencesKey("visiting_streak")
    val LAST_VISIT_EPOCH_DAY = longPreferencesKey("last_visit_epoch_day")
}