package com.example.nutricionydeportefr.pantallas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme

@Composable
fun Home(navController: NavController) {
    Text (
        text = "Bienvenido",
        textAlign = TextAlign.Center,
        fontSize = 30.sp
        )
}


@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    NutricionYDeporteFRTheme {
        Home(navController = NavController(LocalContext.current))
    }
}