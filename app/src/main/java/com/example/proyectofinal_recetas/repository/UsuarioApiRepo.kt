package com.example.proyectofinal_recetas.repository

import com.example.proyectofinal_recetas.dataAPI.model.ExitoResponse
import com.example.proyectofinal_recetas.dataAPI.model.RegistroRequest
import com.example.proyectofinal_recetas.dataAPI.remote.UsuarioApi
import retrofit2.Response
import javax.inject.Inject

class UsuarioApiRepo @Inject constructor(
    private val usuarioApi: UsuarioApi
) {
    suspend fun registrarUsuario(registroRequest: RegistroRequest): Response<ExitoResponse> {
        return usuarioApi.registrarUsuario(registroRequest)
    }
}