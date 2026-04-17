package com.ecohabit.compass.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabit.compass.data.database.EcoDao
import com.ecohabit.compass.data.database.GoalEntity
import com.ecohabit.compass.data.database.HabitEntity
import com.ecohabit.compass.data.database.WasteEntity
import com.ecohabit.compass.data.store.EcoPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val dao: EcoDao,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    val habits = dao.getHabits().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val waste = dao.getWaste().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val goals = dao.getGoals().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val totalSavings = dataStore.data.map { it[EcoPreferences.TOTAL_SAVINGS] ?: 0f }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0f)

    val treesSaved = dataStore.data.map { it[EcoPreferences.TREES_SAVED] ?: 0 }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    init {
        // Prepopulate data if empty
        viewModelScope.launch {
            if (dao.getHabits().first().isEmpty()) {
                dao.insertHabit(
                    HabitEntity(
                        name = "No Meat Today"
                    )
                )
                dao.insertHabit(
                    HabitEntity(
                        name = "Zero Plastic Used"
                    )
                )
                dao.insertHabit(
                    HabitEntity(
                        name = "Biked to Work"
                    )
                )
                dao.insertHabit(
                    HabitEntity(
                        name = "Used Reusable Bags"
                    )
                )
                dao.insertHabit(
                    HabitEntity(
                        name = "Took a Short Shower"
                    )
                )
                dao.insertHabit(
                    HabitEntity(
                        name = "Turned Off Unused Lights"
                    )
                )
                dao.insertHabit(
                    HabitEntity(
                        name = "Composted Food Scraps"
                    )
                )
            }
            if (dao.getGoals().first().isEmpty()) {
                dao.insertGoal(
                    GoalEntity(
                        title = "Compost all organics",
                        priorityIndex = 0
                    )
                )
                dao.insertGoal(
                    GoalEntity(
                        title = "Buy local produce",
                        priorityIndex = 1
                    )
                )
                dao.insertGoal(
                    GoalEntity(
                        title = "Use reusable bottles and cups",
                        priorityIndex = 2
                    )
                )
                dao.insertGoal(
                    GoalEntity(
                        title = "Reduce home energy consumption",
                        priorityIndex = 3
                    )
                )
                dao.insertGoal(
                    GoalEntity(
                        title = "Choose public transport twice a week",
                        priorityIndex = 4
                    )
                )
                dao.insertGoal(
                    GoalEntity(
                        title = "Avoid single-use plastics",
                        priorityIndex = 5
                    )
                )
                dao.insertGoal(
                    GoalEntity(
                        title = "Start a small home recycling station",
                        priorityIndex = 6
                    )
                )
            }
        }
    }

    fun toggleHabit(habit: HabitEntity) = viewModelScope.launch {
        dao.insertHabit(habit.copy(isCompleted = !habit.isCompleted))
    }

    fun toggleFavourite(habit: HabitEntity) = viewModelScope.launch {
        dao.insertHabit(habit.copy(isFavourite = !habit.isFavourite))
    }

    fun updateWaste(category: String, increment: Boolean) = viewModelScope.launch {
        val current = dao.getWaste().first().find { it.category == category }?.count ?: 0
        val newCount = if (increment) current + 1 else maxOf(0, current - 1)
        dao.updateWaste(
            WasteEntity(
                category,
                newCount
            )
        )
    }

    fun addSavings(amount: Float) = viewModelScope.launch {
        dataStore.edit { prefs ->
            val current = prefs[EcoPreferences.TOTAL_SAVINGS] ?: 0f
            val newTotal = current + amount
            prefs[EcoPreferences.TOTAL_SAVINGS] = newTotal
            // Every $50 saved equals 1 tree planted/saved virtually
            prefs[EcoPreferences.TREES_SAVED] = (newTotal / 50).toInt()
        }
    }

    fun swapGoals(g1: GoalEntity, g2: GoalEntity) = viewModelScope.launch {
        dao.insertGoal(g1.copy(priorityIndex = g2.priorityIndex))
        dao.insertGoal(g2.copy(priorityIndex = g1.priorityIndex))
    }
}