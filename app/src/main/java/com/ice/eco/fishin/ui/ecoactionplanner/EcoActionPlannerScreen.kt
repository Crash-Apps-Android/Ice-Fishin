package com.ice.eco.fishin.ui.ecoactionplanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ice.eco.fishin.ui.theme.GreenColor

data class EcoChallenge(
    val title: String,
    val impactPoints: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoActionPlannerScreen(onBackClick: () -> Unit) {
    val challenges = remember {
        listOf(
            EcoChallenge("Use a reusable bottle", 10),
            EcoChallenge("Take public transport", 15),
            EcoChallenge("Skip single-use plastic", 12),
            EcoChallenge("Eat one plant-based meal", 8),
            EcoChallenge("Turn off unused lights", 6),
            EcoChallenge("Recycle paper and cans", 10)
        )
    }

    val completed =
        remember { mutableStateListOf<Boolean>().apply { repeat(challenges.size) { add(false) } } }
    val totalPoints = challenges.sumOf { it.impactPoints }
    val earnedPoints =
        challenges.indices.sumOf { index -> if (completed[index]) challenges[index].impactPoints else 0 }
    val progressPercent = ((earnedPoints.toFloat() / totalPoints.toFloat()) * 100).toInt()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Eco Action Planner") }, navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back")
                }
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Complete daily eco actions to increase your green impact.",
                style = MaterialTheme.typography.bodyLarge,
                color = LocalContentColor.current.copy(.75f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Today’s Progress",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Impact Score: $earnedPoints / $totalPoints")
                    Text("Progress: $progressPercent%")
                    if (progressPercent >= 80) {
                        Text(
                            "Amazing! You’re making a strong eco impact today 🌱",
                            color = GreenColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for ((index, challenge) in challenges.withIndex()) {
                    EcoChallengeRow(
                        challenge = challenge,
                        isCompleted = completed[index],
                        onCheckedChange = { checked -> completed[index] = checked }
                    )
                }
            }
        }
    }
}

@Composable
private fun EcoChallengeRow(
    challenge: EcoChallenge,
    isCompleted: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = {
        onCheckedChange(!isCompleted)
    }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    challenge.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "+${challenge.impactPoints} impact points",
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalContentColor.current.copy(0.75f)
                )
            }
            Checkbox(
                checked = isCompleted,
                onCheckedChange = onCheckedChange
            )
        }
    }
}
