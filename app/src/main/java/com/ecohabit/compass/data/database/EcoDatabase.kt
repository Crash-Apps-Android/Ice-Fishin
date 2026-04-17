package com.ecohabit.compass.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HabitEntity::class, WasteEntity::class, GoalEntity::class], version = 2)
abstract class EcoDatabase : RoomDatabase() {
    abstract fun ecoDao(): EcoDao
}