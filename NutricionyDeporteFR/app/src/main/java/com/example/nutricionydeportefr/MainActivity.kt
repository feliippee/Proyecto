package com.example.nutricionydeportefr

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.example.nutricionydeportefr.navegacion.*
import com.example.nutricionydeportefr.pantallas.perfil.PerfilViewModel
import com.example.nutricionydeportefr.pantallas.registro.RegistroViewModel
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RegistroViewModel().cargarDocumentoId(this)

        setContent {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NutricionYDeporteFRTheme {
                    Navegacion()
                }
            }
        }
    }
}




