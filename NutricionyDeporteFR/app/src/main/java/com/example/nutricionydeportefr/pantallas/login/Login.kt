package com.example.nutricionydeportefr.pantallas.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.example.nutricionydeportefr.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.navegacion.Escenas
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider



lateinit var resultLauncher: ActivityResultLauncher<Intent>
@Composable
fun Login(navController: NavController, loginViewModel: LoginViewModel) {
    val email:String by loginViewModel.email.observeAsState(initial = "")
    val password:String by loginViewModel.password.observeAsState(initial = "")
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    Box(
        Modifier.fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Instanciamos firebase
            firebaseAuth = FirebaseAuth.getInstance()
            //Inicializamos resultLauncher


            Titulo()
            Spacer(modifier = Modifier.height(10.dp))
            Fotologin()
            Spacer(modifier = Modifier.height(10.dp))
            //Campos de texto
            Camposlogin(loginViewModel)
            Spacer(modifier = Modifier.height(10.dp))
            Recuperarcontraseña(navController)
            Spacer(modifier = Modifier.height(30.dp))


            Botonlogin(correo = email, contrasena = password , context = context, navController = navController, loginViewModel )
            Spacer(modifier = Modifier.height(30.dp))
            //Linea Divisora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(thickness = 1.dp, modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "OR",
                    fontSize = 12.sp,
                    color = Color.Gray)
                Divider(thickness = 1.dp, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(30.dp))
            BotonesLoginRedes(loginViewModel, googleSignInClient, firebaseAuth, navController, context)
            Spacer(modifier = Modifier.height(160.dp))
            TextoRegistro(navController = navController)

        }
    }
}

@Composable
fun Titulo() {
    Text(
        text = "Nutricion y Deporte FR",
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

@Composable
fun Fotologin() {
    //Imagen
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "Logo",
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Camposlogin(loginViewModel: LoginViewModel) {
    val email:String by loginViewModel.email.observeAsState(initial = "")
    val password:String by loginViewModel.password.observeAsState(initial = "")
    val mostrarpassword by loginViewModel.mostrarpassword.observeAsState(initial = false)

    OutlinedTextField(value = email,
        onValueChange = {
            loginViewModel.onEmailChanged(it)
        },
        label = { Text("Correo") })
    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        value = password,
        onValueChange = {
            loginViewModel.onPasswordChanged(it)
                        },
        label = { Text("Contraseña") },
        visualTransformation = if (mostrarpassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
            loginViewModel.onMostrarPasswod()

            }) {
                Icon(
                    painter = painterResource(id = if (mostrarpassword) R.drawable.mostrar_password else R.drawable.ocultar_password),
                    contentDescription = if (mostrarpassword) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        }
    )
}

@Composable
fun Recuperarcontraseña(navController: NavController,) {
    Text(
        text = "Recuperar Contraseña",
        modifier = Modifier
            .clickable { navController.navigate(route = Escenas.Registro.ruta) }
            .padding(start = 150.dp),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold

    )
}
@Composable
fun Botonlogin(correo: String,
               contrasena: String,
               context: android.content.Context,
               navController: NavController,
               loginViewModel: LoginViewModel) {
    Button(onClick = {
        //Comprobamos que los campos no esten vacios
        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(context, "Introduce el Correo y Contraseña para Iniciar Sesion", Toast.LENGTH_SHORT)
                .show()
            return@Button
        } else {
            //Comprobamos el inicio de sesion
            loginViewModel.comprobarInicioSesion(correo, contrasena, context, navController)
        }

    }, modifier = Modifier
        .padding(8.dp)
        .width(250.dp)
    ) {
        Text(text = "Iniciar Sesión")
    }
}
@Composable
fun BotonesLoginRedes(
    loginViewModel: LoginViewModel,
    googleSignInClient: GoogleSignInClient,
    firebaseAuth: FirebaseAuth,
    navController: NavController,
    context: Context,
    ){
    Button(
        onClick = {
        }
        ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Iniciar Sesión con Google",
            )
        }
    }
}
@Composable
fun TextoRegistro(navController: NavController){
    Text(text = "¿No tienes cuenta? Registrate",
        modifier = Modifier
            .clickable { navController.navigate(route = Escenas.Registro.ruta) },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold)
}



