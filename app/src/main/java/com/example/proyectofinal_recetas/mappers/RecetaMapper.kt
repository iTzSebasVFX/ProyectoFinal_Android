package com.example.proyectofinal_recetas.mappers

import com.example.proyectofinal_recetas.dataAPI.model.ActualizarRecetaRequest
import com.example.proyectofinal_recetas.dataAPI.model.AgregarRecetaRequest
import com.example.proyectofinal_recetas.dataAPI.model.RecetaRequest
import com.example.proyectofinal_recetas.dataRoom.local.RecetaLocal

fun RecetaLocal.toRequest(): AgregarRecetaRequest {
    return AgregarRecetaRequest(
        nombre = this.nombre,
        ingredientes = this.ingredientes,
        instrucciones = this.instrucciones,
        tiempo_preparacion = this.tiempo_preparacion,
        porciones = this.porciones,
        dificultad = this.dificultad,
        id_usuario = this.id_usuario
    )
}

fun RecetaLocal.toActualizarRequest(): ActualizarRecetaRequest {
    return ActualizarRecetaRequest(
        id = this.id,
        nombre = this.nombre,
        ingredientes = this.ingredientes,
        instrucciones = this.instrucciones,
        tiempo_preparacion = this.tiempo_preparacion,
        porciones = this.porciones,
        dificultad = this.dificultad
    )
}