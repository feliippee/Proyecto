package com.example.nutricionydeportefr.pantallas.perfil

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nutricionydeportefr.pantallas.registro.firebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

class PerfilViewModel : ViewModel() {


    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val PREFS_NAME = "foto perfil"
    private val PREF_IMAGEN_PERFIL_URL = "imagen_perfil_url"

    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(3)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu

    private val _nombreUsuario = MutableLiveData<String>()
    val nombreUsuario: LiveData<String> = _nombreUsuario

    private val _imagenPerfilUrl = MutableLiveData<String>()
    val imagenPerfilUrl: LiveData<String> = _imagenPerfilUrl

    private val _sexo = MutableLiveData<String>()
    val sexo: LiveData<String> = _sexo


    private val _edad = MutableLiveData<String>()
    val edad: LiveData<String> = _edad

    private val _peso = MutableLiveData<String>()
    val peso: LiveData<String> = _peso

    private val _altura = MutableLiveData<String>()
    val altura: LiveData<String> = _altura

    private val _expandir = MutableLiveData(false)
    val expandir: LiveData<Boolean> = _expandir

    init {
        obtenerDatosUsuario()
    }

    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }

    fun setSexo(sexo: String) {
        _sexo.value = sexo

    }

    fun setDesplegable() {
        _expandir.value = !(_expandir.value ?: false)
    }

    fun setEdad(edad: String) {
        _edad.value = edad
        Log.d("Edad PerfilViewModel", "Edad: $edad")
    }

    fun setPeso(peso: String) {
        _peso.value = peso
    }

    fun setAltura(altura: String) {
        _altura.value = altura
    }

    fun obtenerDatosUsuario() {

        val usuario = FirebaseAuth.getInstance().currentUser

        if (usuario != null) {

            _nombreUsuario.value = usuario.displayName
            val db = FirebaseFirestore.getInstance()
            val user = db.collection("usuario").document(usuario.uid)
            user.get()

                .addOnSuccessListener { document ->

                    val sexoObtenido = document.getString("sexo") ?: ""
                    val edadObtenido = document.getString("edad") ?: ""
                    val pesoObtenido = document.getString("peso") ?: ""
                    val alturaObtenida = document.getString("altura") ?: ""

                    _sexo.value = sexoObtenido
                    _edad.value = edadObtenido

                    _peso.value = pesoObtenido
                    _altura.value = alturaObtenida
                }
                .addOnFailureListener {
                    Log.e("PerfilViewModel", "Error al cargar los datos del usuario", it)
                }
        }

    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun guardarUrlEnPreferencias(context: Context, url: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(PREF_IMAGEN_PERFIL_URL, url)
        editor.apply()
    }

    private fun cargarUrlDesdePreferencias(context: Context): String? {
        return getSharedPreferences(context).getString(PREF_IMAGEN_PERFIL_URL, null)
    }

    fun subirImagen(context: Context, uri: Uri) {
        val user = auth.currentUser
        if (user != null) {
            val storageRef = storage.reference
            val imagenperfil = storageRef.child("imagenesperfil/${user.uid}")

            val uploadTask = imagenperfil.putFile(uri)
            uploadTask.addOnSuccessListener {
                imagenperfil.downloadUrl.addOnSuccessListener { downloadUri ->
                    guardarUrlFirebase(downloadUri.toString())
                    _imagenPerfilUrl.value = downloadUri.toString()
                    guardarUrlEnPreferencias(context, downloadUri.toString())
                }.addOnFailureListener {
                    Log.e("PerfilViewModel", "Error getting download URL", it)
                }
            }.addOnFailureListener {
                Log.e("PerfilViewModel", "Upload failed", it)
            }
        }
    }

    private fun guardarUrlFirebase(downloadUri: String) {
        val user = auth.currentUser
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            val usuario = db.collection("usuario").document(user.uid)
            usuario.get()
                .addOnSuccessListener { document ->
                    usuario.update("fotodeperfil", downloadUri)
                        .addOnSuccessListener {
                            Log.d("PerfilViewModel", "La url de la imagen se ha guardado con exito")
                        }
                        .addOnFailureListener {
                            Log.e("PerfilViewModel", "Error al guardar la url", it)
                        }

                }
                .addOnFailureListener {
                    Log.e("PerfilViewModel", "Error al comprobar la existencia del documento", it)
                }
        }
    }

    fun cargarImagenPerfil(context: Context) {
        val url = cargarUrlDesdePreferencias(context) // Cargar la URL desde SharedPreferences
        if (url != null) {
            _imagenPerfilUrl.value = url
        }
    }

    fun registrarDatos() {
        val db = Firebase.firestore
        val usuario = hashMapOf(
            "edad" to usuario,
            "correo" to correo,
            "fecha nacimiento" to fechaNacimiento,
        )
        val user = firebaseAuth.currentUser

        if (user != null) {
            db.collection("usuario")
                .document(user.uid) // Usamos la ID del usuario como el nombre del documento
                .set(usuario)
                .addOnSuccessListener {
                    println("DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    println("Error writing document $e")
                }
        }

    }

}



