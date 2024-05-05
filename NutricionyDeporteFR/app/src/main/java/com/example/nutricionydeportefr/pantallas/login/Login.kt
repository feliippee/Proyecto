package com.example.nutricionydeportefr.pantallas.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.nutricionydeportefr.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.navegacion.Escenas
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme
import com.google.firebase.auth.FirebaseAuth


lateinit var resultLauncher: ActivityResultLauncher<Intent>

@Composable
fun Login(navController: NavController, loginViewModel: LoginViewModel) {
    val email: String by loginViewModel.email.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val context = LocalContext.current
    /*val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)*/
    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
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


            Botonlogin(
                correo = email,
                contrasena = password,
                context = context,
                navController = navController,
                loginViewModel
            )
            Spacer(modifier = Modifier.height(30.dp))
            //Linea Divisora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    thickness = 1.dp, modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "OR",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Divider(
                    thickness = 1.dp, modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            BotonesLoginRedes(loginViewModel, firebaseAuth, navController, context)
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
    val email: String by loginViewModel.email.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val mostrarpassword by loginViewModel.mostrarpassword.observeAsState(initial = false)
    val emailError: String? by loginViewModel.emailError.observeAsState(initial = null)
    val passwordError: String? by loginViewModel.passwordError.observeAsState(initial = null)

    OutlinedTextField(
        value = email,
        onValueChange = {loginViewModel.onEmailChanged(it) },
        placeholder = { Text("Correo") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = emailError != null,
        supportingText = {
            emailError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        }

    )
    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        value = password,
        onValueChange = {
            loginViewModel.onPasswordChanged(it)
        },
        placeholder = { Text("Contraseña") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
        },
        isError = passwordError != null,
        supportingText = {
            passwordError?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.Red),
                )
            }
        }
    )
}

@Composable
fun Recuperarcontraseña(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Recuperar Contraseña",
            modifier = Modifier
                .padding(end = 55.dp)
                .clickable { navController.navigate(route = Escenas.RecuperarPassword.ruta) },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold

        )
    }

}

@Composable
fun Botonlogin(
    correo: String,
    contrasena: String,
    context: android.content.Context,
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    Button(
        onClick = {
            loginViewModel.InicioSesion(correo, contrasena, context, navController)

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
    //googleSignInClient: GoogleSignInClient,
    firebaseAuth: FirebaseAuth,
    navController: NavController,
    context: Context,
) {
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
fun TextoRegistro(navController: NavController) {
    Text(
        text = "¿No tienes cuenta? Registrate",
        modifier = Modifier
            .clickable { navController.navigate(route = Escenas.Registro.ruta) },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}



