package com.ecohabit.compass.ui.waste

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecohabit.compass.ui.plus
import com.ecohabit.compass.ui.MainViewModel
import com.ecohabit.compass.ui.plus
import org.koin.androidx.compose.koinViewModel

private enum class WasteCupTier(
    val minScore: Int,
    val cup: String,
    val title: String,
    val congratsText: String
) {
    GOLD(
        minScore = 40,
        cup = "🏆 Gold Cup",
        title = "Gold level reached!",
        congratsText = "Amazing effort! You are doing a top-level recycling job."
    ),
    SILVER(
        minScore = 24,
        cup = "🥈 Silver Cup",
        title = "Silver level reached!",
        congratsText = "Great work! Your recycling consistency is paying off."
    ),
    BRONZE(
        minScore = 12,
        cup = "🥉 Bronze Cup",
        title = "Bronze level reached!",
        congratsText = "Nice start! Keep recycling to climb the next level."
    )
}

private fun calculateWasteScore(counts: Map<String, Int>): Int {
    val plastic = counts["Plastic"] ?: 0
    val glass = counts["Glass"] ?: 0
    val paper = counts["Paper"] ?: 0
    val metal = counts["Metal"] ?: 0

    return (plastic) + (glass) + (paper) + (metal)
}

private fun resolveTier(score: Int): WasteCupTier? {
    return WasteCupTier.entries.firstOrNull { score >= it.minScore }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WasteScreen(onBackClick: () -> Unit, vm: MainViewModel = koinViewModel()) {
    val wasteList by vm.waste.collectAsState()
    val categories = listOf("Plastic", "Glass", "Paper", "Metal")

    val countsByCategory = categories.associateWith { category ->
        wasteList.find { it.category == category }?.count ?: 0
    }

    val totalInput = countsByCategory.values.sum()
    val minimumInputToShowResult = 4
    val shouldShowResult = totalInput >= minimumInputToShowResult

    val score = calculateWasteScore(countsByCategory)
    val tier = if (shouldShowResult) resolveTier(score) else null

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Recycling Waste Counter") }, navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        "Back"
                    )
                }
            })
        },
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp) + it,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                val count = countsByCategory[category] ?: 0
                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(category, modifier = Modifier.weight(1f), fontSize = 18.sp)
                        IconButton(onClick = {
                            vm.updateWaste(
                                category,
                                false
                            )
                        }) { Icon(Icons.Default.Remove, "Decrease") }
                        Text(count.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = {
                            vm.updateWaste(
                                category,
                                true
                            )
                        }) { Icon(Icons.Default.Add, "Increase") }
                    }
                }
            }

            if (tier != null) {
                item {
                    Card {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(tier.cup, fontSize = 28.sp)
                            Text(tier.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("Score: $score", fontSize = 16.sp)
                            Text(tier.congratsText, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}
