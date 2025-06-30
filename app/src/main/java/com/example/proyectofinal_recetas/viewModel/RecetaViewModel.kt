package com.example.proyectofinal_recetas.viewModel

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal_recetas.dataAPI.model.ActualizarRecetaRequest
import com.example.proyectofinal_recetas.dataAPI.model.AgregarRecetaRequest
import com.example.proyectofinal_recetas.dataAPI.model.ErrorResponse
import com.example.proyectofinal_recetas.dataAPI.model.RecetaResponse
import com.example.proyectofinal_recetas.dataRoom.local.RecetaLocal
import com.example.proyectofinal_recetas.dataRoom.local.UsuarioLocal
import com.example.proyectofinal_recetas.mappers.toActualizarRequest
import com.example.proyectofinal_recetas.mappers.toRequest
import com.example.proyectofinal_recetas.repository.RecetaApiRepo
import com.example.proyectofinal_recetas.repository.RecetaLocalRepo
import com.example.proyectofinal_recetas.utils.UsuarioSession
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecetaViewModel @Inject constructor(
    private val repository: RecetaApiRepo,
    private val localRepo: RecetaLocalRepo,
    private val session: UsuarioSession
) : ViewModel() {
    val id_usuario: Int? get() = session.usuario?.id
    val usuario: UsuarioLocal? get() = session.usuario

    private val _recetasPorUsuario = MutableStateFlow<List<RecetaLocal>?>(emptyList())
    val recetasPorUsuario: StateFlow<List<RecetaLocal>?> = _recetasPorUsuario
    private val _recetaPorId = MutableStateFlow<RecetaLocal?>(null)
    val recetaPorId: StateFlow<RecetaLocal?> = _recetaPorId

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

    fun obtenerRecetas() {
        viewModelScope.launch {
            try {
                val uid = id_usuario ?: return@launch
                _recetasPorUsuario.value = localRepo.obtenerPorUsuario(uid)
                _errorApi.value = null
                _errorLocal.value = null
            } catch (e: Exception) {
                _errorApi.value = "Algo fallo en la búsqueda: ${e.localizedMessage}"
            }
        }
    }

    fun obtenerPorId(id: Int) {
        viewModelScope.launch {
            try {
                _recetaPorId.value = localRepo.obtenerPorId(id)
            } catch (e: Exception) {
                _errorLocal.value = "Algo inesperado ha sucedido: ${e.localizedMessage}"
            }
        }
    }

    fun crearReceta(
        nombre: String,
        ingredientes: String,
        instrucciones: String,
        tiempo_preparacion: String,
        porciones: String,
        dificultad: String
    ) {
        viewModelScope.launch {
            println("el id del usuario es:" + id_usuario + "comprobar")
            val uid = id_usuario ?: return@launch
            println(uid)
            var sincronizada  = false

            // Intentar enviar a la API
            try {
                val recetaRequest = AgregarRecetaRequest( // tu DTO
                    nombre,
                    ingredientes,
                    instrucciones,
                    tiempo_preparacion,
                    porciones,
                    dificultad,
                    uid
                )

                val response = repository.agregarNuevaReceta(recetaRequest)

                if (response.isSuccessful) {
                    _exitoApi.value = response.body()?.exito
                    sincronizada  = true
                } else {
                    val errorJson = response.errorBody()?.toString()
                    val moshi = Moshi.Builder().build()
                    val adapter = moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(errorJson)

                    _errorApi.value = errorResponse?.error ?: "Error: ${response.code()}"
                }
            } catch (_: Exception) {
                _errorApi.value = "Error en la conexion."
            }

            // Insertar localmente
            val recetaLocal = RecetaLocal(
                nombre = nombre,
                ingredientes = ingredientes,
                instrucciones = instrucciones,
                tiempo_preparacion = tiempo_preparacion,
                porciones = porciones,
                dificultad = dificultad,
                id_usuario = uid,
                ocultar = false,
                sincronizada = sincronizada
            )

            localRepo.insertarReceta(recetaLocal)
            _exitoLocal.value = "Nueva receta agregada exitosamente en local"
        }
    }

    fun actualizarReceta(
        id: Int,
        nombre: String,
        ingredientes: String,
        instrucciones: String,
        tiempo_preparacion: String,
        porciones: String,
        dificultad: String
    ) {
        viewModelScope.launch {
            val uid = id_usuario ?: return@launch
            var sincronizada = false

            try {
                val recetaRequest = ActualizarRecetaRequest(id, nombre, ingredientes, instrucciones, tiempo_preparacion, porciones, dificultad)
                val response = repository.actualizarReceta(recetaRequest)

                if (response.isSuccessful) {
                    _exitoApi.value = response.body()?.exito
                    sincronizada = true
                } else {
                    val errorJson = response.errorBody()?.toString()
                    val moshi = Moshi.Builder().build()
                    val adapter = moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(errorJson)

                    _errorApi.value = errorResponse?.error ?: "Error: ${response.code()}"
                }
            } catch (_: Exception) {
                _errorApi.value = "Error en la conexión."
            }

            val actualizar = RecetaLocal(
                id = id,
                nombre = nombre,
                ingredientes = ingredientes,
                instrucciones = instrucciones,
                tiempo_preparacion = tiempo_preparacion,
                porciones = porciones,
                dificultad = dificultad,
                id_usuario = uid,
                ocultar = false,
                sincronizada = sincronizada
            )

            localRepo.actualizarReceta(actualizar)
            _exitoLocal.value = "Receta actualizada en local exitosamente."
        }

    }

    fun eliminarReceta(id: Int) {
        viewModelScope.launch {
            var sincronizada = false

            try {
                val resultado = repository.eliminarReceta(id)

                if (resultado.isSuccessful) {
                    _exitoApi.value = resultado.body()?.exito
                    _errorApi.value = null
                    sincronizada = true

                    // ✅ Si fue exitosa en API → eliminar de local
                    localRepo.eliminarRecetaPorId(id)
                } else {
                    val errorJson = resultado.errorBody()?.string()
                    val moshi = Moshi.Builder().build()
                    val adapter = moshi.adapter(ErrorResponse::class.java)
                    val errorResponse = adapter.fromJson(errorJson)

                    _errorApi.value = errorResponse?.error ?: "Error: ${resultado.code()}"
                }
            } catch (e: Exception) {
                _errorApi.value = "Error en la red: ${e.localizedMessage}"
            }

            if (!sincronizada) {
                // 🔴 API no disponible → solo ocultar y marcar como no sincronizada
                val receta = localRepo.obtenerPorId(id)
                receta?.let {
                    val modificada = it.copy(
                        ocultar = true,
                        sincronizada = false
                    )
                    localRepo.actualizarReceta(modificada)
                }
            }
        }
    }

    fun sincronizarRecetasPendientes() {
        viewModelScope.launch {
            val recetasNoSync = localRepo.getRecetasNoSincronizadas()

            for (receta in recetasNoSync) {
                try {
                    val response = if (receta.id != 0) {
                        repository.actualizarReceta(receta.toActualizarRequest()) // ✅ directo
                    } else {
                        repository.agregarNuevaReceta(receta.toRequest())
                    }

                    if (response.isSuccessful) {
                        val recetaActualizada = receta.copy(sincronizada = true)
                        localRepo.actualizarReceta(recetaActualizada)
                    }
                } catch (_: Exception) {
                    // Ignora: sigue con las demás
                }
            }

            val recetasOcultas = localRepo.getRecetasParaEliminar()

            for (receta in recetasOcultas) {
                try {
                    val response = repository.eliminarReceta(receta.id)
                    if (response.isSuccessful) {
                        localRepo.eliminarRecetaPorId(receta.id)
                    }
                } catch (_: Exception) {
                    // Ignora
                }
            }
        }
    }
}