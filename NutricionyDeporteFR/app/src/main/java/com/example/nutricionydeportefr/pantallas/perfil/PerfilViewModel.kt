package com.example.nutricionydeportefr.pantallas.perfil

import android.content.Context
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


    //Variable para la seleccion de las opciones del bottom menu
    private var _opcionBottonMenu = MutableLiveData(3)
    var opcionBottonMenu: LiveData<Int> = _opcionBottonMenu

    private val _nombreUsuario = MutableLiveData<String>()
    val nombreUsuario: LiveData<String> = _nombreUsuario

    private val _imagenPerfilUrl = MutableLiveData<String>()
    val imagenPerfilUrl: LiveData<String> = _imagenPerfilUrl

    fun setOpcionBottonMenu(opcion: Int) {
        _opcionBottonMenu.value = opcion
    }

    fun obtenerNombreUsuario() {
        val usuario = FirebaseAuth.getInstance().currentUser
        _nombreUsuario.value = usuario?.displayName

        // Cargar la URL de la imagen de perfil del usuario desde Firestore
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("usuario").document(usuario?.uid ?: return)
        userRef.get()
            .addOnSuccessListener { document ->
                val profileImageUrl = document.getString("profileImageUrl")
                _imagenPerfilUrl.value = profileImageUrl
            }
            .addOnFailureListener {
                Log.e("PerfilViewModel", "Error al cargar la URL de la imagen de perfil", it)
            }
    }

    fun subirImagen(context: Context, uri: Uri) {
        val user = auth.currentUser
        if (user != null) {
            val storageRef = storage.reference
            val imagenperfil = storageRef.child("imagenesperfil/${user.uid}")
            Log.d("UID", user.uid)
            val uploadTask = imagenperfil.putFile(uri)
            uploadTask.addOnSuccessListener {
                imagenperfil.downloadUrl.addOnSuccessListener { downloadUri ->
                    guardarUrlFirebase(downloadUri.toString())
                    _imagenPerfilUrl.value = downloadUri.toString()
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
                    if (document.exists()) {
                        // El documento existe, actualiza el campo
                        usuario.update("fotodeperfil", downloadUri)
                            .addOnSuccessListener {
                                Log.d("PerfilViewModel", "La url de la imagen se ha guardado con exito")
                            }
                            .addOnFailureListener {
                                Log.e("PerfilViewModel", "Error al guardar la url", it)
                            }
                    } else {
                        // El documento no existe, crea uno nuevo
                        val userData = hashMapOf(
                            "fotodeperfil" to downloadUri
                        )
                        usuario.set(userData)
                            .addOnSuccessListener {
                                Log.d("PerfilViewModel", "La url de la imagen se ha guardado con exito")
                            }
                            .addOnFailureListener {
                                Log.e("PerfilViewModel", "Error al guardar la url", it)
                            }
                    }
                }
                .addOnFailureListener {
                    Log.e("PerfilViewModel", "Error al comprobar la existencia del documento", it)
                }
        }
    }
}


