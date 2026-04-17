package com.ecohabit.compass.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val IceFishingColorScheme = darkColorScheme(
    primary = AppPrimary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    inversePrimary = InversePrimary,
    secondary = AppSecondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = AppTertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    background = AppBackground,
    onBackground = OnBackground,
    surface = AppBackground,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceTint = AppPrimary,
    inverseSurface = InverseSurface,
    inverseOnSurface = InverseOnSurface,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    outline = Outline,
    outlineVariant = OutlineVariant,
    scrim = Scrim,
    surfaceBright = AppSurfaceBright,
    surfaceDim = AppBackgroundDim,
    surfaceContainer = AppSurface,
    surfaceContainerHigh = AppSurfaceHigh,
    surfaceContainerHighest = AppSurfaceHighest,
    surfaceContainerLow = AppSurfaceLow,
    surfaceContainerLowest = AppSurfaceLowest
)

@Composable
@Suppress("UNUSED_PARAMETER")
fun IceFishingAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = IceFishingColorScheme,
        typography = Typography,
        content = content
    )
}
