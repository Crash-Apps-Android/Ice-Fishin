package com.ecohabit.compass

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ecohabit.compass.ui.EcoTrackApp
import com.ecohabit.compass.ui.theme.IceFishingAppTheme

private val StatusBarStyle = SystemBarStyle.dark(
    Color.TRANSPARENT,
)
private val NavigationBarStyle = StatusBarStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            StatusBarStyle,
            NavigationBarStyle
        )
        setContent {
            IceFishingAppTheme() {
                EcoTrackApp()
            }
        }
    }
}
