package com.example.proyectofinal_recetas.dataRoom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.proyectofinal_recetas.dataRoom.local.RecetaLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface RecetaDao {
    @Query("SELECT * FROM receta WHERE sincronizada = 0 AND ocultar = 0")
    suspend fun getRecetasNoSincronizadas(): List<RecetaLocal>

    @Query("SELECT * FROM receta WHERE sincronizada = 0 AND ocultar = 1")
    suspend fun getRecetasParaEliminar(): List<RecetaLocal>

    @Query("UPDATE receta SET ocultar = 1, sincronizada = 0 WHERE id = :id")
    suspend fun marcarComoOculta(id: Int)

    @Query("DELETE FROM receta WHERE id = :id")
    suspend fun eliminarReceta(id: Int)

    @Query("SELECT * FROM receta WHERE id = :id AND ocultar = 0")
    suspend fun obtenerPorId(id: Int): RecetaLocal?

    @Transaction
    @Query("SELECT * FROM receta WHERE id_usuario = :id_usuario AND ocultar = 0")
    suspend fun obtenerPorUsuario(id_usuario: Int?): List<RecetaLocal>

    @Transaction
    @Query("DELETE FROM receta WHERE id_usuario = :id_usuario")
    suspend fun eliminarRecetasPorUsuario(id_usuario: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReceta(recetaLocal: RecetaLocal)

    @Update
    suspend fun actualizarReceta(recetaLocal: RecetaLocal)
}