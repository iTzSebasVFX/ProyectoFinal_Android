package com.example.proyectofinal_recetas.dataRoom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyectofinal_recetas.dataRoom.local.UsuarioLocal

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuario WHERE correo = :correo AND contraseña = :contraseña")
    suspend fun acceder(correo: String, contraseña: String): UsuarioLocal?

    @Query("SELECT * FROM usuario WHERE sincronizado = 0")
    suspend fun obtenerNoSincronizados(): List<UsuarioLocal>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registrar(usuarioLocal: UsuarioLocal)

    @Update
    suspend fun actualizar(usuarioLocal: UsuarioLocal)
}