package com.example.proyectofinal_recetas.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal_recetas.dataAPI.model.ErrorResponse
import com.example.proyectofinal_recetas.dataAPI.model.RegistroRequest
import com.example.proyectofinal_recetas.dataRoom.local.UsuarioLocal
import com.example.proyectofinal_recetas.mappers.toRequest
import com.example.proyectofinal_recetas.repository.UsuarioApiRepo
import com.example.proyectofinal_recetas.repository.UsuarioLocalRepo
import com.example.proyectofinal_recetas.utils.UsuarioSession
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val repository: UsuarioApiRepo,
    private val localRepo: UsuarioLocalRepo,
    private val session: UsuarioSession
): ViewModel() {
    // Para Api
    private val _errorApi = MutableStateFlow<String?>(null)
    val errorApi: StateFlow<String?> = _errorApi
    private val _exitoApi = MutableStateFlow<String?>(null)
    val exitoApi: StateFlow<String?> = _exitoApi

    // Para local
    private val _errorLocal = MutableStateFlow<String?>(null)
    val errorLocal: StateFlow<String?> = _errorLocal
    private val _exitoLocal = MutableStateFlow<String?>(null)
    val exitoLocal: StateFlow<String?> = _exitoLocal

    fun clearErroresYExitos() {
        _errorApi.value = null
        _exitoApi.value = null
        _errorLocal.value = null
        _exitoLocal.value = null
    }

    fun registrarUsuario(nombre: String, correo: String, contraseña: String) {
        viewModelScope.launch {
            val nuevoRegistro = RegistroRequest(nombre, correo, contraseña)
            try {
                val response = repository.registrarUsuario(nuevoRegistro)

                if (response.isSuccessful) {
                    _exitoApi.value = response.body()?.exito
                    _errorApi.value = null

                    val usuarioLocal = UsuarioLocal(
                        nombre = nombre,
                        correo = correo,
                        contraseña = contraseña,
                        sincronizado = true
                    )
                    localRepo.registrar(usuarioLocal)

                } else {
                    val errorJson = response.errorBody()?.string()
                    val moshi = Moshi.Builder().build()
                    val adapter = moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(errorJson)

                    _errorApi.value = errorResponse?.error ?: "Error ${response.code()}"
                    _exitoApi.value = null

                    guardarUsuarioLocal(nombre, correo, contraseña, false)
                }
            } catch (_: Exception) {
                _errorApi.value = "Error de red o inesperado"
                guardarUsuarioLocal(nombre, correo, contraseña, false)
            }
        }
    }

    private fun guardarUsuarioLocal(nombre: String, correo: String, contraseña: String, sincronizado: Boolean) {
        val usuario = UsuarioLocal(nombre = nombre, correo = correo, contraseña = contraseña, sincronizado = sincronizado)
        viewModelScope.launch {
            try {
                localRepo.registrar(usuario)
                _exitoLocal.value = "Registrado localmente"
            } catch (e: Exception) {
                _errorLocal.value = "Correo ya registrado localmente"
            }
        }
    }

    fun loguearUsuarioLocal(correo: String, contraseña: String) {
        viewModelScope.launch {
            try {
                val usuarioLocal = localRepo.acceder(correo, contraseña)
                _exitoLocal.value = "Acceso exitoso."
                session.usuario = usuarioLocal
            } catch (e: Exception) {
                _errorLocal.value = "Verifique los datos o registrese."
            }
        }
    }

    // Funcion de sincronizacion
    fun SincronizarConApi() {
        viewModelScope.launch {
            var usuariosPendientes = localRepo.obtenerNoSincronizados()

            usuariosPendientes.forEach { usuarioLocal ->
                try {
                    val usuarioApi = usuarioLocal.toRequest()
                    val response = repository.registrarUsuario(usuarioApi)
                    if (response.isSuccessful) {
                        val usuarioSincronizado = usuarioLocal.copy(sincronizado = true)
                        localRepo.actualizar(usuarioSincronizado)
                        _exitoLocal.value = "La sincronización ha sido exitosa."
                    }
                } catch (_: Exception) {

                }
            }
        }
    }
}