package com.example.nutricionydeportefr

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                NutricionYDeporteFRTheme {
                }
            }
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Vista() {
    Surface(color = MaterialTheme.colorScheme.background) {
        NutricionYDeporteFRTheme {

        }
    }
}



