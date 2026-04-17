package com.ice.eco.fishin

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ice.eco.fishin.ui.EcoTrackApp
import com.ice.eco.fishin.ui.theme.IceFishingAppTheme

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
