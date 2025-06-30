package com.example.proyectofinal_recetas.dataAPI.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Modelos de pedido
@JsonClass(
    generateAdapter = true
)
data class AgregarRecetaRequest(
    val nombre: String,
    val ingredientes: String,
    val instrucciones: String,
    val tiempo_preparacion: String,
    val porciones: String,
    val dificultad: String,
    val id_usuario: Int?,
)
@JsonClass(
    generateAdapter = true
)
data class ActualizarRecetaRequest(
    val id: Int,
    val nombre: String?,
    val ingredientes: String?,
    val instrucciones: String?,
    val tiempo_preparacion: String?,
    val porciones: String?,
    val dificultad: String?,
)

// Modelo de respuesta
@JsonClass(
    generateAdapter = true
)
data class RecetaResponse(
    val id: Int,
    val nombre: String,
    val ingredientes: String,
    val instrucciones: String,
    val tiempo_preparacion: String,
    val porciones: String,
    val dificultad: String,
    val id_usuario: Int,
)

// Para la sincronizacion
@JsonClass(
    generateAdapter = true
)
data class RecetaRequest(
    val id: Int,
    val nombre: String,
    val ingredientes: String,
    val instrucciones: String,
    val tiempo_preparacion: String,
    val porciones: String,
    val dificultad: String,
    val id_usuario: Int?,
)
