package com.example.nutricionydeportefr.pantallas.registrosport

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.*

@Composable
fun RegistroSport() {
    Box(Modifier.fillMaxSize().padding(8.dp ) ) {
        Header(Modifier.align(Alignment.TopCenter))
        body()

    }

}
@Composable
fun Header(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = "Registro de deporte",
    )

}

fun body() {

}