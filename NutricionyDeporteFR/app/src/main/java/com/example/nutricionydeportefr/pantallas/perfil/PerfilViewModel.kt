package com.example.nutricionydeportefr.pantallas.perfil

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage

class PerfilViewModel private constructor()  : ViewModel() {

    //Instanciamos firebase
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()


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

    private val _objetivoMarcado = MutableLiveData<String>()
    val objetivoMarcado: LiveData<String> = _objetivoMarcado

    //Obtenemos los datos nada mas se genere el viewmodel
    init {
        obtenerDatosUsuario()
        cargarImagenPerfil()
    }


    fun setSexo(sexo: String) {
        _sexo.value = sexo
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
    fun setObjetivoMarcado(objetivoMarcado: String) {
        _objetivoMarcado.value = objetivoMarcado
    }

    //Obtenemos los datos del usuario de firebase
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
                        _objetivoMarcado.value = document.getString("objetivoMarcado") ?: ""
                    }
                    Log.d("PerfilViewModel", "Datos del usuario cargados con éxito")
                }
                .addOnFailureListener {
                    Log.e("PerfilViewModel", "Error al cargar los datos del usuario", it)
                }
        }
    }
    //Subimos la imagen en firebase
    fun subirImagen( uri: Uri) {
        val user = auth.currentUser
        if (user != null) {
            val storageRef = storage.reference.child("imagenesperfil/${user.uid}")
            storageRef.putFile(uri).addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    guardarUrlFirebase(downloadUri.toString())
                    _imagenPerfilUrl.value = downloadUri.toString()
                }.addOnFailureListener {
                    Log.e("PerfilViewModel", "Error al guardar la imagen", it)
                }
            }.addOnFailureListener {
                Log.e("PerfilViewModel", "Subida de la imagen fallida", it)
            }
        }
    }
    //Guardamos la url de la imagen en firebase
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
    //Guardamos los datos del usuario en firebase
    fun guardarDatosUsuario() {
        val sexo = _sexo.value
        val edad = _edad.value
        val peso = _peso.value
        val altura = _altura.value
        val objetivo = _objetivoMarcado.value

        val user = auth.currentUser

        if (user != null) {

            val datosUsuario = hashMapOf(
                "sexo" to sexo,
                "edad" to edad,
                "peso" to peso,
                "altura" to altura,
                "objetivoMarcado" to objetivo
            )
            firestore.collection("usuario")
                .whereEqualTo("usuarioId", user.uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        document.reference.set(datosUsuario, SetOptions.merge())
                            .addOnSuccessListener {
                                Log.d("PerfilViewModel", "Datos del usuario guardados con éxito")
                            }
                            .addOnFailureListener {
                                Log.e("PerfilViewModel", "Error al guardar los datos del usuario", it)
                            }
                    }
                }
                .addOnFailureListener {
                    Log.e("PerfilViewModel", "Error al obtener el documento del usuario", it)
                }
        }
    }
    //Cargamos la imagen de perfil
    fun cargarImagenPerfil() {
        val user = auth.currentUser
        if (user != null) {
            firestore.collection("usuario")
                .whereEqualTo("usuarioId", user.uid)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val url = document.getString("fotodeperfil")
                        if (url != null) {
                            _imagenPerfilUrl.value = url
                        }
                    }
                    Log.d("PerfilViewModel", "Imagen de perfil cargada con éxito")
                }
                .addOnFailureListener {
                    Log.e("PerfilViewModel", "Error al cargar la imagen de perfil", it)
                }
        }
    }

    //Singlenton para perfil
    companion object {
        private var instance: PerfilViewModel? = null
        fun getInstance(): PerfilViewModel {
            if (instance == null) {
                instance = PerfilViewModel()
            }
            return instance as PerfilViewModel
        }
    }
}



