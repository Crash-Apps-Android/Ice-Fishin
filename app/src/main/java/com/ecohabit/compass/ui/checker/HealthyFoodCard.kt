package com.ecohabit.compass.ui.checker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecohabit.compass.data.database.HealthyFood

@Composable
fun HealthyFoodCard(food: HealthyFood, modifier: Modifier = Modifier) {
    // Determine color based on your specific score ranges
    val scoreColor = when {
        food.healthyScore > 7 -> Color(0xFF2E7D32) // Green (Approved)
        food.healthyScore >= 3 -> Color(0xFFF57C00) // Amber (Okay)
        else -> Color(0xFFD32F2F) // Red (Unapproved)
    }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .weight(1f)
                )

                // Score Badge
                Surface(
                    color = scoreColor.copy(alpha = 0.1f),
                    contentColor = scoreColor,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "Score: ${food.healthyScore}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            // Display ingredients as small chips
            FlowRow(
                modifier = Modifier.padding(top = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                food.ingredients.forEach { ingredient ->
                    OutlinedCard(
                        shape = MaterialTheme.shapes.extraSmall
                    ) {
                        Text(
                            ingredient,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HealthyFoodCardPreview() {
    HealthyFoodCard(HealthyFood.Companion.fakeGoodFood)
}