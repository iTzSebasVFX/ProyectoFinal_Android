package com.example.proyectofinal_recetas.dataRoom.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "receta",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioLocal::class,
            parentColumns = ["id"],
            childColumns = ["id_usuario"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("id_usuario")]
)
data class RecetaLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val ingredientes: String,
    val instrucciones: String,
    val tiempo_preparacion: String,
    val porciones: String,
    val dificultad: String,
    val id_usuario: Int?,

    val ocultar: Boolean = false,
    val sincronizada: Boolean = false
)