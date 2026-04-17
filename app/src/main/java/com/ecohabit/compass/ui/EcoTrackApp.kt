package com.ecohabit.compass.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ecohabit.compass.ui.checker.CheckerScreen
import com.ecohabit.compass.ui.dashboard.HomeScreen
import com.ecohabit.compass.ui.goalsetter.GoalSetterScreen
import com.ecohabit.compass.ui.habits.HabitsScreen
import com.ecohabit.compass.ui.ecoactionplanner.EcoActionPlannerScreen
import com.ecohabit.compass.ui.quiz.QuizScreen
import com.ecohabit.compass.ui.savings.SavingsScreen
import com.ecohabit.compass.ui.waste.WasteScreen

const val DashboardRoute = "DashboardRoute"
const val CheckerRoute = "CheckerRoute"
const val HabitsRoute = "HabitsRoute"
const val WasteRoute = "WasteRoute"
const val SavingsTrackerRoute = "SavingsTrackerRoute"
const val EcoActionPlannerRoute = "EcoActionPlannerRoute"
const val QuizRoute = "QuizRoute"
const val GoalSetterRoute = "GoalSetterRoute"

@Composable
fun EcoTrackApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, "Dashboard") },
                    label = { Text("Home") },
                    selected = currentRoute != HabitsRoute && currentRoute != CheckerRoute,
                    onClick = {
                        navController.popBackStack(DashboardRoute, false)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Checklist, "Habits") },
                    label = { Text("Habits") },
                    selected = currentRoute == HabitsRoute,
                    onClick = {
                        navController.navigate(HabitsRoute) {
                            popUpTo(DashboardRoute) { saveState = true }
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, "Checker") },
                    label = { Text("Checker") },
                    selected = currentRoute == CheckerRoute,
                    onClick = {
                        navController.navigate(CheckerRoute) {
                            popUpTo(DashboardRoute) { saveState = true }
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(
                    ScaffoldDefaults.contentWindowInsets
                )
        ) {
            composable(DashboardRoute) {
                HomeScreen(
                    navController
                )
            }
            composable(CheckerRoute) { CheckerScreen() }
            composable(HabitsRoute) { HabitsScreen() }
            composable(WasteRoute) {
                WasteScreen(
                    onBackClick = navController::navigateUp
                )
            }
            composable(SavingsTrackerRoute) {
                SavingsScreen(
                    onBackClick = navController::navigateUp
                )
            }
            composable(EcoActionPlannerRoute) {
                EcoActionPlannerScreen(
                    onBackClick = navController::navigateUp
                )
            }
            composable(QuizRoute) {
                QuizScreen(
                    onBackClick = navController::navigateUp
                )
            }
            composable(GoalSetterRoute) {
                GoalSetterScreen(
                    onBackClick = navController::navigateUp
                )
            }
        }
    }
}
