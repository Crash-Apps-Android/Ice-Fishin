package com.ecohabit.compass.ui.habits

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecohabit.compass.ui.plus
import com.ecohabit.compass.data.database.HabitEntity
import com.ecohabit.compass.ui.MainViewModel
import com.ecohabit.compass.ui.plus
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(vm: MainViewModel = koinViewModel()) {
    val habits by vm.habits.collectAsState()

    // Sort habits: Favourites first, then alphabetical
    val sortedHabits = habits.sortedWith(compareBy({ !it.isFavourite }, { it.name }))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Daily Habit Tracker")
                },
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                16.dp
            ) + it
        ) {
            item {
                HorizontalDivider()
            }
            val onHabitClick: (HabitEntity) -> Unit = { vm.toggleHabit(it) }
            for (habit in sortedHabits) {
                item(key = habit.id) {
                    Row(
                        modifier = Modifier
                            .animateItem()
                            .fillMaxWidth()
                            .clickable(
                                null,
                                ripple(color = LocalContentColor.current),
                                onClick = {
                                    onHabitClick(habit)
                                }
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            IconButton(onClick = { vm.toggleFavourite(habit) }) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Favourite",
                                    tint = if (habit.isFavourite) Color(0xFFFFC107) else Color.LightGray
                                )
                            }
                            Text(
                                habit.name,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        Switch(
                            checked = habit.isCompleted,
                            onCheckedChange = {
                                onHabitClick(habit)
                            })
                    }
                }
                item(key = "${habit.id}_divider") {
                    HorizontalDivider(Modifier.animateItem())
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
        }
    }
}
