package com.example.nutricionydeportefr

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.secondary) {
                NutricionYDeporteFRTheme {
                    Titulo()

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Titulo() {
    //LazyColumn es un composable que permite desplazarse verticalmente
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Nutricion y Deporte FR",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
            )
            //Añadir espacio entre campos
            Spacer(modifier = Modifier.height(10.dp))
            //Imagen
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            var usuario by remember { mutableStateOf("") }
            OutlinedTextField(value = usuario,
                onValueChange = {
                    usuario = it
                },
                label = { Text("Nombre de usuario") })
            Spacer(modifier = Modifier.height(10.dp))
            var contrasena by remember { mutableStateOf("") }
            var mostrarContrasena by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                visualTransformation = if (mostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                        Icon(
                            painter = painterResource(id = if (mostrarContrasena) R.drawable.mostrar_password else R.drawable.ocultar_password),
                            contentDescription = if (mostrarContrasena) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
    LazyRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 350.dp)

    ) {
        item {
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(12.dp)) {
                Text(text = "Registro")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(12.dp)) {
                Text(text = "Recuperar Contraseña")
            }
        }

    }
}

@Composable
fun Botones() {

}

@Preview(showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun DefaultPreview() {
    NutricionYDeporteFRTheme {
        Titulo()

    }
}


