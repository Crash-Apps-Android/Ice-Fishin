package com.ice.eco.fishin.ui.goalsetter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ice.eco.fishin.ui.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalSetterScreen(onBackClick: () -> Unit, vm: MainViewModel = koinViewModel()) {
    val goals by vm.goals.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Prioritize Goals")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            "Back"
                        )
                    }
                },
            )
        },
    ) {
        Column(Modifier.padding(it)) {
            Text(
                "Move goals using the arrows!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LocalContentColor.current.copy(alpha = 0.75f)
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                items(goals.size, key = { goals[it].id }) { index ->
                    val goal = goals[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .animateItem()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (index > 0) {
                                IconButton(onClick = { vm.swapGoals(goal, goals[index - 1]) }) {
                                    Icon(Icons.Default.KeyboardArrowUp, "Move Up")
                                }
                            }
                            Text(goal.title, modifier = Modifier.weight(1f), fontSize = 18.sp)
                            if (index < goals.size - 1) {
                                IconButton(onClick = { vm.swapGoals(goal, goals[index + 1]) }) {
                                    Icon(Icons.Default.KeyboardArrowDown, "Move Down")
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}