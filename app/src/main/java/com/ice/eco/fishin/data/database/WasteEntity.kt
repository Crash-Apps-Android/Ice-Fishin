package com.ice.eco.fishin.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waste")
data class WasteEntity(
    @PrimaryKey val category: String, // e.g., "Plastic", "Glass"
    val count: Int = 0
)