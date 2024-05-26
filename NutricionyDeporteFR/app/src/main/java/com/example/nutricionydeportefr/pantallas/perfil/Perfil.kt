package com.example.nutricionydeportefr.pantallas.perfil

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.nutricionydeportefr.scaffold.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.nutricionydeportefr.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Perfil(navController: NavController, perfilViewModel: PerfilViewModel, scaffoldViewModel: ScaffoldViewModel) {
    LaunchedEffect(key1 = true) {
        perfilViewModel.obtenerNombreUsuario()
    }

    Scaffold(
        topBar = { Toolbar(scaffoldViewModel, navController) },
        bottomBar = { BottomMenu(navController, perfilViewModel) }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Body(Modifier.align(Alignment.TopStart),perfilViewModel)
        }


    }
}

@Composable
fun Body(modifier : Modifier, perfilViewModel: PerfilViewModel) {

    Column(
        modifier = modifier
    ) {
        NombreUsuario(perfilViewModel)
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
        onResult = {uri: Uri? ->
            uri?.let { perfilViewModel.subirImagen(context, it) }
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

        } )




    Column {
        Box(
            modifier = Modifier
                .width(75.dp)
                .height(75.dp)
                .clickable {
                    //En funcion de la version de androd se solicitaran unos permisos u otros
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_MEDIA_IMAGES)
                            == PackageManager.PERMISSION_GRANTED) {
                            galeria.launch("image/*")
                        } else {
                            permisosGaleria.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                        }
                    } else {
                        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
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
                    painter = rememberImagePainter(imagenPerfilUrl),
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
        Text(
            text = "Cambiar",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}



@Composable
fun BottomMenu(navController: NavController, perfilViewModel: PerfilViewModel) {

    val opcionBottonMenu: Int by perfilViewModel.opcionBottonMenu.observeAsState(initial = 3)

    BottomNavigation(
        backgroundColor = Color(0xFF46B62D),
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { androidx.compose.material.Text("Home") },
            selected = opcionBottonMenu == 0,
            onClick = {
                perfilViewModel.setOpcionBottonMenu(0)
                navController.navigate("home")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FitnessCenter, contentDescription = "Ejercicios") },
            label = { androidx.compose.material.Text(text = "Ejercicios") },
            selected = opcionBottonMenu == 1,
            onClick = {
                perfilViewModel.setOpcionBottonMenu(1)
                navController.navigate("ejercicios")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.FoodBank, contentDescription = "Dietas") },
            label = { androidx.compose.material.Text("Dietas") },
            selected = opcionBottonMenu == 2,
            onClick = {
                perfilViewModel.setOpcionBottonMenu(2)
                navController.navigate("alimentacion")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { androidx.compose.material.Text("Perfil") },
            selected = opcionBottonMenu == 3,
            onClick = {
                perfilViewModel.setOpcionBottonMenu(3)
                navController.navigate("perfil")
            }
        )
    }
}
