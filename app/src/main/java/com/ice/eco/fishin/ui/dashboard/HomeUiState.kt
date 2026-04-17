package com.ice.eco.fishin.ui.dashboard

data class HomeUiState(
    val visitStreak: Int = 0,
    val canCheckToday: Boolean = true
)