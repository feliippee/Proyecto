package com.example.nutricionydeportefr.pantallas.perfil

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage


class PerfilViewModel : ViewModel() {


    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

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

    }

    fun setPeso(peso: String) {
        _peso.value = peso
    }

    fun setAltura(altura: String) {
        _altura.value = altura
    }

    fun obtenerDatosUsuario() {


        val usuario = auth.currentUser
        if (usuario != null) {
            _nombreUsuario.value = usuario.displayName
            val userRef = firestore.collection("usuario")
            userRef.whereEqualTo("usuarioId", usuario.uid).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        _sexo.value = document.getString("sexo") ?: ""
                        _edad.value = document.getString("edad") ?: ""
                        _peso.value = document.getString("peso") ?: ""
                        _altura.value = document.getString("altura") ?: ""
                    }
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
            val storageRef = storage.reference.child("imagenesperfil/${user.uid}")
            storageRef.putFile(uri).addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
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
            firestore.collection("usuario")
                .whereEqualTo("usuarioId", user.uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        document.reference.update("fotodeperfil", downloadUri)
                            .addOnSuccessListener {
                                Log.d("PerfilViewModel", "La url de la imagen se ha guardado con éxito")
                            }
                            .addOnFailureListener {
                                Log.e("PerfilViewModel", "Error al guardar la url", it)
                            }
                    }
                }
                .addOnFailureListener {
                    Log.e("PerfilViewModel", "Error al obtener el documento del usuario", it)
                }
        }
    }
    fun guardarDatosUsuario() {
        val sexo = _sexo.value
        val edad = _edad.value
        val peso = _peso.value
        val altura = _altura.value

        val user = auth.currentUser

        if (user != null) {

            val datosUsuario = hashMapOf(
                "sexo" to sexo,
                "edad" to edad,
                "peso" to peso,
                "altura" to altura
            )
            firestore.collection("usuario")
                .whereEqualTo("usuarioId", user.uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        document.reference.set(datosUsuario, SetOptions.merge())
                            .addOnSuccessListener {
                                Log.d("PerfilViewModel", "La url de la imagen se ha guardado con éxito")
                            }
                            .addOnFailureListener {
                                Log.e("PerfilViewModel", "Error al guardar la url", it)
                            }
                    }
                }
                .addOnFailureListener {
                    Log.e("PerfilViewModel", "Error al obtener el documento del usuario", it)
                }
        }
    }

    fun cargarImagenPerfil(context: Context) {
        val url = cargarUrlDesdePreferencias(context) // Cargar la URL desde SharedPreferences
        if (url != null) {
            _imagenPerfilUrl.value = url
        }
    }

}



