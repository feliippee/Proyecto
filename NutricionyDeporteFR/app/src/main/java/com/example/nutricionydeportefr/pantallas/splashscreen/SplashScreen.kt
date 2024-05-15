package com.example.nutricionydeportefr.pantallas.splashscreen

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun SplashScreen(navController: NavController, SplashScreenViewModel: SplashScreenViewModel){
    LaunchedEffect(key1 = true) {
        delay(2000)

        if (SplashScreenViewModel.usuarioLogueado()){
            navController.navigate("home") {
                popUpTo("splashscreen") {
                    inclusive = true
                }
            }
        }else{
            navController.navigate("login") {
                popUpTo("splashscreen") {
                    inclusive = true
                }
            }
        }
    }
    Splash()
}

@Composable
fun Splash(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    )  {
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
fun SplashScreenPreview(){
    Splash()
}