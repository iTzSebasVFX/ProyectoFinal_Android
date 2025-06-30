package com.example.proyectofinal_recetas.dataAPI.remote

import com.example.proyectofinal_recetas.dataAPI.model.ActualizarRecetaRequest
import com.example.proyectofinal_recetas.dataAPI.model.AgregarRecetaRequest
import com.example.proyectofinal_recetas.dataAPI.model.ExitoResponse
import com.example.proyectofinal_recetas.dataAPI.model.RecetaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecetaApi {
    @POST("recetas/agregar")
    suspend fun agregarNuevaReceta(@Body agregarRecetaRequest: AgregarRecetaRequest): Response<ExitoResponse>

    @PUT("recetas/actualizar")
    suspend fun actualizarReceta(@Body actualizarRecetaRequest: ActualizarRecetaRequest): Response<ExitoResponse>

    @DELETE("recetas/eliminar/{id}")
    suspend fun eliminarReceta(@Path ("id") id: Int): Response<ExitoResponse>
}