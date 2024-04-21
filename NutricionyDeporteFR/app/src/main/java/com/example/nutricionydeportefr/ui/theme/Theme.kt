package com.example.nutricionydeportefr.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(


    //Color para botones y otros elementos
    primary = Naranja,
    //Color para los textos de los botones
    onPrimary = White,
    //Color secundario para botones
    secondary = AzulFacebook,
    //Color para los textos de los botones secundarios
    onSecondary = White,
)

private val LightColorScheme = lightColorScheme(
    //Color para botones principales
    primary = Verde,
    //Color de textos principales
    onPrimary = Black,
    //Color Secundario botones
    secondary = AzulFacebook,
    //Color de textos secundarios
    onSecondary = Black,

)

@Composable
fun NutricionYDeporteFRTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}