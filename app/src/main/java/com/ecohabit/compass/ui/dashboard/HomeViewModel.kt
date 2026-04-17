package com.ecohabit.compass.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecohabit.compass.data.store.CheckResult
import com.ecohabit.compass.data.store.VisitingStreakProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val visitingStreakProvider: VisitingStreakProvider
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        visitingStreakProvider.visitingStreak,
        visitingStreakProvider.canCheckToday
    ) { streak, canCheckToday ->
        HomeUiState(visitStreak = streak, canCheckToday = canCheckToday)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())

    private val _showThankYouDialog = MutableStateFlow(false)
    val showThankYouDialog: StateFlow<Boolean> = _showThankYouDialog.asStateFlow()

    fun onCheckClick() {
        viewModelScope.launch {
            val result = visitingStreakProvider.checkVisitToday()
            if (result == CheckResult.CHECKED) {
                _showThankYouDialog.value = true
            }
        }
    }

    fun dismissThankYouDialog() {
        _showThankYouDialog.update { false }
    }
}
