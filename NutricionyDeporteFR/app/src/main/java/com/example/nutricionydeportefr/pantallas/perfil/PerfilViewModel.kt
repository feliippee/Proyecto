package com.example.nutricionydeportefr.pantallas.perfil

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    private val _expandir = MutableLiveData(false)
    val expandir: LiveData<Boolean> = _expandir

    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }

    fun setSexo(sexo: String) {
        _sexo.value = sexo
    }

    fun setDesplegable() {
        _expandir.value = !(_expandir.value ?: false)
    }

    fun obtenerNombreUsuario() {
        val usuario = FirebaseAuth.getInstance().currentUser
        _nombreUsuario.value = usuario?.displayName

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
}


