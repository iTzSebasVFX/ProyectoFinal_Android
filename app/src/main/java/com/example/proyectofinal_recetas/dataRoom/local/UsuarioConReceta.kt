package com.example.proyectofinal_recetas.dataRoom.local

import androidx.room.Embedded
import androidx.room.Relation

data class UsuarioConReceta(
    @Embedded val usuarioLocal: UsuarioLocal,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_usuario"
    )
    val receta: List<RecetaLocal>
)
