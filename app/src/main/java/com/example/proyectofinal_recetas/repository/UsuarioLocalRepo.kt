package com.example.proyectofinal_recetas.repository

import com.example.proyectofinal_recetas.dataRoom.dao.UsuarioDao
import com.example.proyectofinal_recetas.dataRoom.local.UsuarioLocal
import javax.inject.Inject

class UsuarioLocalRepo @Inject constructor(private val usuarioDao: UsuarioDao) {
    suspend fun registrar(usuarioLocal: UsuarioLocal) {
        usuarioDao.registrar(usuarioLocal)
    }

    suspend fun actualizar(usuarioLocal: UsuarioLocal) {
        usuarioDao.actualizar(usuarioLocal)
    }

    suspend fun acceder(correo: String, contraseña: String): UsuarioLocal? {
        return usuarioDao.acceder(correo, contraseña)
    }

    suspend fun obtenerNoSincronizados(): List<UsuarioLocal> {
        return usuarioDao.obtenerNoSincronizados()
    }
}