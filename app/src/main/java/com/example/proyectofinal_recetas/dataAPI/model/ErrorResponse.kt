package com.example.proyectofinal_recetas.dataAPI.model

import com.squareup.moshi.JsonClass

@JsonClass(
    generateAdapter = true
)
data class ErrorResponse(val error: String)

@JsonClass(
    generateAdapter = true
)
data class ExitoResponse(val exito: String)
