package com.ecohabit.compass.ui.dashboard

data class HomeUiState(
    val visitStreak: Int = 0,
    val canCheckToday: Boolean = true
)