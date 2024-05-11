package com.example.nutricionydeportefr

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import com.example.nutricionydeportefr.navegacion.*
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                color = MaterialTheme.colorScheme.background) {
                NutricionYDeporteFRTheme {
                 Navegacion()
                }
            }
        }
    }
}




