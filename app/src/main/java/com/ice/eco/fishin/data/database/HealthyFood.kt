package com.ice.eco.fishin.data.database

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HealthyFood(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    @SerialName("healthy_score")
    val healthyScore: Double
) {
    companion object {
        @Suppress("unused")
        val fakeGoodFood = HealthyFood(
            -1,
            "Good Food Name",
            listOf("Ingredient 1", "Ingredient 2", "Ingredient 3"),
            healthyScore = 10.0
        )
        @Suppress("unused")
        val fakeOkayFood = HealthyFood(
            -1,
            "Okay Food Name",
            listOf("Ingredient 1", "Ingredient 2", "Ingredient 3"),
            healthyScore = 5.0
        )
        @Suppress("unused")
        val fakeBadFood = HealthyFood(
            -1,
            "Bad Food Name",
            listOf("Ingredient 1", "Ingredient 2", "Ingredient 3"),
            healthyScore = 1.0
        )
    }
}