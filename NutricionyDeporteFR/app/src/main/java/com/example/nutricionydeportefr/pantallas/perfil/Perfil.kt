package com.example.nutricionydeportefr.pantallas.perfil

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nutricionydeportefr.R
import com.example.nutricionydeportefr.scaffold.ScaffoldViewModel
import com.example.nutricionydeportefr.scaffold.Toolbar


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Perfil(navController: NavController, perfilViewModel: PerfilViewModel, scaffoldViewModel: ScaffoldViewModel) {


    LaunchedEffect(key1 = true) {
        perfilViewModel.obtenerDatosUsuario()
        perfilViewModel.cargarImagenPerfil()
    }

    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController, perfilViewModel) }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                NombreUsuario(perfilViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                Sexo(perfilViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                Edad(perfilViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                Peso(perfilViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                Altura(perfilViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                ObjetivoMarcado(perfilViewModel)
            }
            item { Spacer(modifier = Modifier.height(56.dp)) }
        }
    }

}

@Composable
fun NombreUsuario(perfilViewModel: PerfilViewModel) {

    val nombreUsuario by perfilViewModel.nombreUsuario.observeAsState(initial = "")

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
    ) {
        FotoUsuario(perfilViewModel)
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Bienvenido $nombreUsuario",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
    }


}

@Composable
fun FotoUsuario(perfilViewModel: PerfilViewModel) {
    val context = LocalContext.current
    val imagenPerfilUrl by perfilViewModel.imagenPerfilUrl.observeAsState()

    val galeria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { perfilViewModel.subirImagen(it) }
        }
    )

    val permisosGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { permiso: Boolean ->
            if (permiso) {
                galeria.launch("image/*")
                Log.d("PERMISO", "Concedido")
            } else {
                //Mensaje de error
                Log.d("PERMISO", "Denegado")
            }

        })
    Box(
        modifier = Modifier
            .width(75.dp)
            .height(75.dp)
            .clickable {
                //En funcion de la version de androd se solicitaran unos permisos u otros
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_MEDIA_IMAGES)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        galeria.launch("image/*")
                    } else {
                        permisosGaleria.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        galeria.launch("image/*")
                    } else {
                        permisosGaleria.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            }
            .border(2.dp, Color(0xFF46B62D), CircleShape)
    ) {
        if (imagenPerfilUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(imagenPerfilUrl),
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .width(75.dp)
                    .height(75.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.fotoperfil),
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .width(75.dp)
                    .height(75.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sexo(perfilViewModel: PerfilViewModel) {

    val sexo by perfilViewModel.sexo.observeAsState()
    var expandir by remember { mutableStateOf(false) }
    val eleccionSexo = listOf("Hombre", "Mujer", "Otro")

    Box {
        //OutlineTextfield con un dropdown para seleccionar el sexo
        OutlinedTextField(
            value = sexo ?: "---",
            onValueChange = { perfilViewModel.setSexo(it) },
            label = { Text("Sexo") },
            maxLines = 1,
            readOnly = true,
            enabled = false,
            modifier = Modifier
                .clickable { expandir = !expandir },
            colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black),
        )
        DropdownMenu(
            expanded = expandir,
            onDismissRequest = {
                expandir = false
            }
        ) {
            eleccionSexo.forEach { opcion ->
                DropdownMenuItem(onClick = {
                    perfilViewModel.setSexo(opcion)
                    expandir = false
                }) {
                    Text(text = opcion)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Edad(perfilViewModel: PerfilViewModel) {

    val edad by perfilViewModel.edad.observeAsState(initial = "---")

    OutlinedTextField(
        value = edad,
        onValueChange = {
            perfilViewModel.setEdad(it)

        },

        label = { Text("Edad") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Peso(perfilViewModel: PerfilViewModel) {
    val peso by perfilViewModel.peso.observeAsState(initial = "")

    OutlinedTextField(
        value = peso,
        onValueChange = { perfilViewModel.setPeso(it) },
        label = { Text("Peso Kg") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Altura(perfilViewModel: PerfilViewModel) {
    val altura by perfilViewModel.altura.observeAsState()

    OutlinedTextField(
        value = altura ?: "----",
        onValueChange = {
            perfilViewModel.setAltura(it)

        },
        label = { Text("Altura CM") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjetivoMarcado(perfilViewModel: PerfilViewModel) {

    val objetivoMarcado by perfilViewModel.objetivoMarcado.observeAsState(initial = "Objetivo")
    var expandir by remember { mutableStateOf(false) }
    val opcionesObjetivo = listOf("Perder peso", "Ganar peso", "Mantener peso")

    Box {
        //OutlinedTextField y dropdownmenu con las opciones
        OutlinedTextField(
            value = objetivoMarcado,
            onValueChange = { perfilViewModel.setObjetivoMarcado(it) },
            label = { Text("Objetivo") },
            readOnly = true,
            maxLines = 1,
            enabled = false,
            modifier = Modifier
                .clickable { expandir = !expandir },
            colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black),
        )
        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { expandir = false })
        {
            opcionesObjetivo.forEach { opcion ->
                DropdownMenuItem(onClick = {
                    perfilViewModel.setObjetivoMarcado(opcion)
                    expandir = false
                }) {
                    Text(text = opcion)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    when (objetivoMarcado) {
        "Perder peso" -> {
            Card(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxSize(),
                border = BorderStroke(2.dp, Color(0xFF46B62D)),
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium,
            ) {
                Column {
                    Text(
                        text = "Si deseas perder peso las raciones recomendadas son:",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "Raciones de Frutas: 3\nRaciones Proteina: 5\nRaciones de Hidratos: 4\n" +
                                "Raciones de Grasas: 2\nRaciones de Lacteos: 3\nRaciones de Verduras: 5",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 8.dp)

                    )
                }
            }
        }

        "Ganar peso" -> {
            Card(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxSize(),
                border = BorderStroke(2.dp, Color(0xFF46B62D)),
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium,
            ) {
                Column {
                    Text(
                        text = "Si deseas ganar peso las raciones recomendadas son:",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "Raciones de Frutas: 3\nRaciones Proteina: 5\nRaciones de Hidratos: 4\n" +
                                "Raciones de Grasas: 2\nRaciones de Lacteos: 3\nRaciones de Verduras: 5",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 8.dp)

                    )
                }
            }
        }

        "Mantener peso" -> {
            Card(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxSize(),
                border = BorderStroke(2.dp, Color(0xFF46B62D)),
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium,
            ) {
                Column {
                    Text(
                        text = "Si deseas matener peso las raciones recomendadas son:",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "Raciones de Frutas: 3\nRaciones Proteina: 5\nRaciones de Hidratos: 4\n" +
                                "Raciones de Grasas: 2\nRaciones de Lacteos: 3\nRaciones de Verduras: 5",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 8.dp)

                    )
                }
            }
        }
    }

}


@Composable
fun BottomMenu(navController: NavController, perfilViewModel: PerfilViewModel) {

    val opcionBottonMenu: Int by perfilViewModel.opcionBottonMenu.observeAsState(initial = 3)

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),
        contentColor = Color.Black
    ) {
        /*BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = opcionBottonMenu == 0,
            onClick = {
                perfilViewModel.guardarDatosUsuario()
                perfilViewModel.setOpcionBottonMenu(0)
                navController.navigate("home")
            }
        )*/
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Ejercicios") },
            label = { Text(text = "Ejercicios") },
            selected = opcionBottonMenu == 1,
            onClick = {
                perfilViewModel.guardarDatosUsuario()
                perfilViewModel.setOpcionBottonMenu(1)
                navController.navigate("ejercicios")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Dietas") },
            label = { Text("Dietas") },
            selected = opcionBottonMenu == 2,
            onClick = {
                perfilViewModel.guardarDatosUsuario()
                perfilViewModel.setOpcionBottonMenu(2)
                navController.navigate("alimentacion")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = opcionBottonMenu == 3,
            onClick = {
                perfilViewModel.guardarDatosUsuario()
                perfilViewModel.setOpcionBottonMenu(3)
                navController.navigate("perfil")
            }
        )
    }
}
