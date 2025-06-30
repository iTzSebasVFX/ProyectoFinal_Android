package com.example.proyectofinal_recetas.repository

import com.example.proyectofinal_recetas.dataRoom.dao.RecetaDao
import com.example.proyectofinal_recetas.dataRoom.local.RecetaLocal
import javax.inject.Inject

class RecetaLocalRepo @Inject constructor(private val recetaDao: RecetaDao) {
    suspend fun getRecetasNoSincronizadas(): List<RecetaLocal> {
        return recetaDao.getRecetasNoSincronizadas()
    }
    suspend fun getRecetasParaEliminar(): List<RecetaLocal> {
        return recetaDao.getRecetasParaEliminar()
    }

    suspend fun marcarComoOculta(id: Int) {
        recetaDao.marcarComoOculta(id)
    }
    suspend fun eliminarRecetaPorId(id: Int) {
        recetaDao.eliminarReceta(id)
    }
    suspend fun obtenerPorId(id: Int): RecetaLocal? {
        return recetaDao.obtenerPorId(id)
    }
    suspend fun obtenerPorUsuario(id_usuario: Int?): List<RecetaLocal> {
        return recetaDao.obtenerPorUsuario(id_usuario)
    }

    suspend fun insertarReceta(recetaLocal: RecetaLocal) {
        recetaDao.insertarReceta(recetaLocal)
    }
    suspend fun actualizarReceta(recetaLocal: RecetaLocal) {
        recetaDao.actualizarReceta(recetaLocal)
    }

    // Por si acaso
    suspend fun eliminarRecetasPorUsuario(id_usuario: Int) {
        recetaDao.eliminarRecetasPorUsuario(id_usuario)
    }
}