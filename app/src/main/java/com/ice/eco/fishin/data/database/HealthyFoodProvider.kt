package com.ice.eco.fishin.data.database

import android.content.Context
import kotlinx.serialization.json.Json

class HealthyFoodProvider(context: Context) {
    private val foods = context.assets.open("foods.json").bufferedReader().use { it.readText() }.let {
        Json.decodeFromString<List<HealthyFood>>(it)
    }

    fun searchFoods(query: String) = foods.filter { food ->
        food.name.contains(query, ignoreCase = true) || food.ingredients.any {
            it.contains(query, ignoreCase = true)
        } || food.healthyScore.toString().replace(".", "").contains(
            query.filter { it.isDigit() }.ifEmpty { "none" },
            ignoreCase = true
        )
    }
    @Suppress("unused")
    fun searchFirstFood(query: String) = searchFoods(query).first()
}