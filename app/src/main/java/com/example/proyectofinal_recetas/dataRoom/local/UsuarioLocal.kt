package com.example.proyectofinal_recetas.dataRoom.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuario",
    indices = [Index(value = ["correo"], unique = true)]
)
data class UsuarioLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val contraseña: String,

    val sincronizado: Boolean = false
)
