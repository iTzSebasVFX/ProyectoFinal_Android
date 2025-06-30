package com.example.proyectofinal_recetas.dataAPI.remote

import com.example.proyectofinal_recetas.dataAPI.model.ExitoResponse
import com.example.proyectofinal_recetas.dataAPI.model.RegistroRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApi {
    @POST("registrar")
    suspend fun registrarUsuario(@Body registroRequest: RegistroRequest): Response<ExitoResponse>
}