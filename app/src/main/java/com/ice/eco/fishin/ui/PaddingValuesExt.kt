package com.ice.eco.fishin.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection

@Composable
operator fun PaddingValues.plus(paddingValues: PaddingValues) = LocalLayoutDirection.current.let {
    PaddingValues(
        start = calculateStartPadding(it) + paddingValues.calculateStartPadding(it),
        top = calculateTopPadding() + paddingValues.calculateTopPadding(),
        end = calculateEndPadding(it) + paddingValues.calculateEndPadding(it),
        bottom = calculateBottomPadding() + paddingValues.calculateBottomPadding(),
    )
}