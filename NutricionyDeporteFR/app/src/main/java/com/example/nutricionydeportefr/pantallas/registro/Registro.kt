package com.example.nutricionydeportefr.pantallas.registro

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.nutricionydeportefr.ui.theme.NutricionYDeporteFRTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nutricionydeportefr.R
import com.google.firebase.Firebase
import com.google.firebase.auth.*
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

//Variables de Firebase
private lateinit var firebaseAuth: FirebaseAuth


@Composable
fun Registro(navController: NavController) {


    val context = LocalContext.current

    //Instanciamos firebase
    firebaseAuth = FirebaseAuth.getInstance()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Textotitulo()
        Spacer(modifier = Modifier.height(50.dp))
        CamposTextos()
        Spacer(modifier = Modifier.height(50.dp))
        BotonRegistro(context, navController)
    }
}

@Composable
fun Textotitulo() {
    // Texto de bienvenida
    Text(
        text = "Crear Cuenta",
        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamposTextos() {
    //Variables
    val calendar = Calendar.getInstance()
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasenas by remember { mutableStateOf(false) }
    var confirmarcontrasena by remember { mutableStateOf("") }
    var errorConfirmarContraseña by remember { mutableStateOf(false) }
    var correo by remember { mutableStateOf("") }
    var correvalido by remember { mutableStateOf(false) }
    var fechaNacimiento by remember { mutableStateOf("") }
    var fechaNacimientoDialog by remember { mutableStateOf(false) }
    // OutlinedTextfield para registrar el nombre del usuario
    OutlinedTextField(value = usuario,
        onValueChange = {
            usuario = it
        },
        label = { Text("Nombre de usuario") })

    Spacer(modifier = Modifier.height(20.dp))

    // OutlinedTextfield para registrar la contraseña del usuario
    OutlinedTextField(
        //Vinculamos el valor del campo de texto con la variable contrasena
        value = contrasena,
        //Cuando el usuario introduce la contraseña, se guarda en la variable contrasena
        onValueChange = { contrasena = it },
        label = { Text("Contraseña") },
        //Si mostrarContrasenas es true, se muestra la contraseña, si no, se oculta
        visualTransformation = if (mostrarContrasenas) VisualTransformation.None else PasswordVisualTransformation()

    )
    Spacer(modifier = Modifier.height(20.dp))

    // OutlinedTextfield para confirmar la contraseña del usuario
    OutlinedTextField(

        value = confirmarcontrasena,
        onValueChange = { confirmarcontrasena = it },
        label = { Text("Confirmar Contraseña") },
        visualTransformation = if (mostrarContrasenas) VisualTransformation.None else PasswordVisualTransformation(),
        //Si las contraseñas no coinciden, se muestra un mensaje de error
        isError = errorConfirmarContraseña,
    )
    if (errorConfirmarContraseña) {
        Text(
            text = "Las contraseñas no coinciden",
            color = Color.Red
        )
    }
    Spacer(modifier = Modifier.height(20.dp))

    // Checkbox para mostrar/ocultar contraseñas
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = mostrarContrasenas,
            onCheckedChange = { mostrarContrasenas = it }
        )
        Text("Mostrar contraseña")
    }
    Spacer(modifier = Modifier.height(20.dp))
    OutlinedTextField(
        value = correo,
        onValueChange = {
            correo = it
            correvalido = !validarCorreo(correo)

        },
        label = { Text("Correo") },
        isError = correvalido
    )
    if (correvalido) {
        Text(
            text = "Correo no valido",
            color = Color.Red
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
    // OutlinedTextfield para registrar la fecha de nacimiento del usuario
    OutlinedTextField(
        value = fechaNacimiento,
        onValueChange = { fechaNacimiento = it },
        label = { Text("Fecha de nacimiento") },
        readOnly = true,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.calendario),
                contentDescription = "Fecha de nacimiento",
                modifier = Modifier.clickable {
                    fechaNacimientoDialog = true
                }
            )
        }
    )
    if (fechaNacimientoDialog) {
        FechaDialog(calendar) {
            fechaNacimiento = it
            fechaNacimientoDialog = false
        }
    }
}

@Composable
fun BotonRegistro(context: android.content.Context, navController: NavController) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasenas by remember { mutableStateOf(false) }
    var confirmarcontrasena by remember { mutableStateOf("") }
    var errorConfirmarContraseña by remember { mutableStateOf(false) }
    var correo by remember { mutableStateOf("") }
    var correvalido by remember { mutableStateOf(false) }
    var fechaNacimiento by remember { mutableStateOf("") }
    //Boton de registro
    Button(onClick = {
        //Comprobamos si los campos estan vacios
        if (usuario.isEmpty() || contrasena.isEmpty() || confirmarcontrasena.isEmpty() || correo.isEmpty() || fechaNacimiento.isEmpty()) {
            //Muestro error en los campos
            Toast.makeText(context, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
        } else {
            //Comprobamos si las contraseñas coinciden y si el email es valido.
            if (contrasena == confirmarcontrasena && !correvalido) {
                registrarUsuario(usuario, contrasena, correo, fechaNacimiento, context, navController)

            } else {
                errorConfirmarContraseña = true
            }
        }
    }) {
        Text(text = "Registrarse")
    }
}

//Funcion para registrar usuario en firebase
private fun registrarUsuario(
    usuario: String,
    contrasena: String,
    correo: String,
    fechaNacimiento: String,
    //Context es para indicarle donde queremos mostrar el mesaje
    context: android.content.Context,
    navController: NavController
) {
    firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //Obtenemos el usuario logueado en firebase
                val user = firebaseAuth.currentUser
                //Actualizamos el perfil del usuario y le ponemos el nombre
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(usuario)
                    .build()

                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            Toast.makeText(context, "Usuario registrado con exito", Toast.LENGTH_SHORT).show()
                            registrarDatosUsuarios(usuario, correo, fechaNacimiento)
                            GlobalScope.launch(Dispatchers.Main) {
                                delay(1500)
                            }
                            navController.navigate("home")
                        }
                    }
            } else {
                val exception = task.exception
                when (exception) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        //Contraseña invalida
                        Toast.makeText(
                            context,
                            "La contraseña  debe de contener al menos 8 caracteres",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is FirebaseAuthInvalidUserException -> {
                        //Email invalido
                        Toast.makeText(context, "El correo introducido no es válido", Toast.LENGTH_SHORT).show()
                    }

                    is FirebaseAuthUserCollisionException -> {
                        //Email en uso
                        Toast.makeText(context, "El correo introducido ya está en uso", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        //Otros errores
                        Toast.makeText(context, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
}

//Funcion para guardar los datos de los usuarios en Firebase
fun registrarDatosUsuarios(
    usuario: String,
    correo: String,
    fechaNacimiento: String
) {
    val db = Firebase.firestore
    val usuario = hashMapOf(
        "nombre" to usuario,
        "correo" to correo,
        "fecha" to fechaNacimiento
    )
    db.collection("usuarios")
        .add(usuario)
        .addOnSuccessListener { documentReference ->
            println("DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            println("Error adding document $e")
        }
}

//Funcion para validar el correo
fun validarCorreo(correo: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
}

//Funcion DaterPickerDialog
@Composable
fun FechaDialog(calendar: Calendar, onDateSelected: (String) -> Unit) {
    val fecha = DatePickerDialog(
        LocalContext.current,
        { view: DatePicker, year: Int, month: Int, day: Int ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            formatoFecha(calendar, onDateSelected)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    fecha.datePicker.maxDate = System.currentTimeMillis()
    fecha.show()
}

//Funcion para formato de la fecha
fun formatoFecha(calendar: Calendar, onDateSelected: (String) -> Unit) {
    val fechaformato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fecha = fechaformato.format(calendar.time)
    onDateSelected(fecha)
}

@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    NutricionYDeporteFRTheme {
        Registro(navController = NavController(LocalContext.current))
    }
}

