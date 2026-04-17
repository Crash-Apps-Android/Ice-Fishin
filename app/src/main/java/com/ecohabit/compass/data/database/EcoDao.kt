package com.ecohabit.compass.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EcoDao {
    @Query("SELECT * FROM habits")
    fun getHabits(): Flow<List<HabitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Query("SELECT * FROM waste")
    fun getWaste(): Flow<List<WasteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWaste(waste: WasteEntity)

    @Query("SELECT * FROM goals ORDER BY priorityIndex ASC")
    fun getGoals(): Flow<List<GoalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity)
}