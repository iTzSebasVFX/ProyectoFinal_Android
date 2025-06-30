package com.example.proyectofinal_recetas.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.proyectofinal_recetas.viewModel.RecetaViewModel

@Composable
fun RecetasDetalles(navController: NavHostController, id: Int) {
    val recetaViewModel: RecetaViewModel = hiltViewModel()

    val id = id
    recetaViewModel.obtenerPorId(id)
    val receta = recetaViewModel.recetaPorId.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            receta.value?.let { it ->
                item {
                    TextButton(
                        onClick = {
                            navController.navigate("recetasScreen")
                        }
                    ) {
                        Text(text = "<- Volver")
                    }
                }

                item {
                    Text("Nombre Anterior: ${it.nombre}")
                    Text("Ingredientes Anteriores: ${it.ingredientes}")
                    Text("Instrucciones Anteriores: ${it.instrucciones}")
                    Text("Tiempo de Preparación: ${it.tiempo_preparacion}")
                    Text("Porciones Anteriores: ${it.porciones}")
                    Text("Dificultad Anterior: ${it.dificultad}")
                }
            }
        }
    }
}