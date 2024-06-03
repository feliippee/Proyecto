package com.example.nutricionydeportefr.pantallas.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, splashScreenViewModel: SplashScreenViewModel) {

    //Nada mas iniciar la vista cargamos el logo y esperamos 2 segundos para redirigir a la siguiente vista
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        if (splashScreenViewModel.usuarioLogueado()) navController.navigate("perfil") else navController.navigate("login")
    }
    Splash()
}

@Composable
fun Splash() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,


    ) {
        Image(
            painter = painterResource(id = R.drawable.nutrisport),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash()
}