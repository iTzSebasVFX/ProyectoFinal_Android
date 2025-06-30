package com.example.proyectofinal_recetas.mappers

import com.example.proyectofinal_recetas.dataAPI.model.RegistroRequest
import com.example.proyectofinal_recetas.dataRoom.local.UsuarioLocal

fun UsuarioLocal.toRequest(): RegistroRequest {
    return RegistroRequest(
        nombre = this.nombre,
        correo = this.correo,
        contraseña = this.contraseña
    )
}