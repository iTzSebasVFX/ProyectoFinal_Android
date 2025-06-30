package com.example.proyectofinal_recetas.repository

import com.example.proyectofinal_recetas.dataAPI.model.ActualizarRecetaRequest
import com.example.proyectofinal_recetas.dataAPI.model.AgregarRecetaRequest
import com.example.proyectofinal_recetas.dataAPI.model.ExitoResponse
import com.example.proyectofinal_recetas.dataAPI.model.RecetaResponse
import com.example.proyectofinal_recetas.dataAPI.remote.RecetaApi
import retrofit2.Response
import javax.inject.Inject

class RecetaApiRepo @Inject constructor(
    private val recetaApiRepo: RecetaApi
) {
    suspend fun agregarNuevaReceta(agregarRecetaRequest: AgregarRecetaRequest): Response<ExitoResponse> {
        return recetaApiRepo.agregarNuevaReceta(agregarRecetaRequest)
    }

    suspend fun actualizarReceta(actualizarRecetaRequest: ActualizarRecetaRequest): Response<ExitoResponse> {
        return recetaApiRepo.actualizarReceta(actualizarRecetaRequest)
    }

    suspend fun eliminarReceta(id: Int): Response<ExitoResponse> {
        return recetaApiRepo.eliminarReceta(id)
    }
}