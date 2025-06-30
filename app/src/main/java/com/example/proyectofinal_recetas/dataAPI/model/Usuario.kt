package com.example.proyectofinal_recetas.dataAPI.model

import com.squareup.moshi.JsonClass

// Modelos de pedido
@JsonClass(
    generateAdapter = true
)
data class RegistroRequest(
    val nombre: String,
    val correo: String,
    val contraseña: String
)

// Modelo de respuesta
@JsonClass(
    generateAdapter = true
)
data class UsuarioResponse(
    val id: Int,
    val nombre: String,
    val correo: String,
    val contraseña: String
)
