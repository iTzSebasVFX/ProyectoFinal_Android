package com.example.proyectofinal_recetas.utils

import com.example.proyectofinal_recetas.dataRoom.local.UsuarioLocal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioSession @Inject constructor() {
    var usuario: UsuarioLocal? = null
}