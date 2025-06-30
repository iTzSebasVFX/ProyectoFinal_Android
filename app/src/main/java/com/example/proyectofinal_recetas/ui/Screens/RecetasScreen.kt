package com.example.proyectofinal_recetas.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectofinal_recetas.utils.ObservadorConectividad
import com.example.proyectofinal_recetas.viewModel.RecetaViewModel
import com.example.proyectofinal_recetas.viewModel.UsuarioViewModel
import kotlinx.coroutines.delay

@Composable
fun RecetasScreen(navController: NavHostController) {
    val recetaViewModel: RecetaViewModel = hiltViewModel()
    val usuarioViewModel: UsuarioViewModel = hiltViewModel()
    val observadorConectividad: ObservadorConectividad = hiltViewModel()

    val conectado by observadorConectividad.conectado.collectAsState()

    val errorApi = usuarioViewModel.errorApi.collectAsState()
    val exitoApi = usuarioViewModel.exitoApi.collectAsState()
    val errorLocal = usuarioViewModel.errorLocal.collectAsState()
    val exitoLocal = usuarioViewModel.exitoLocal.collectAsState()

    val recetas = recetaViewModel.recetasPorUsuario.collectAsState()

    val yaSincronizado = rememberSaveable { mutableStateOf(false) }

    // ✅ Cargar recetas si aún no lo hemos hecho
    LaunchedEffect(Unit) {
        recetaViewModel.obtenerRecetas()
    }

    LaunchedEffect(conectado) {
        if (conectado && !yaSincronizado.value) {
            recetaViewModel.sincronizarRecetasPendientes()
            usuarioViewModel.SincronizarConApi()
            yaSincronizado.value = true
        }
    }

    LaunchedEffect(errorApi.value, exitoApi.value, errorLocal.value, exitoLocal.value) {
        delay(8000)
        usuarioViewModel.clearErroresYExitos()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (errorApi.value != null || exitoApi.value != null || errorLocal.value != null || exitoLocal.value != null) {
                Column {
                    listOfNotNull(
                        errorApi.value,
                        exitoApi.value,
                        errorLocal.value,
                        exitoLocal.value
                    ).forEach { mensaje ->
                        Text(
                            text = mensaje,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(Color(0xFF444444), shape = CircleShape)
                                .padding(horizontal = 18.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            OutlinedButton(
                onClick = {
                    navController.navigate("recetaInsertForm")
                }
            ) {
                Text(text = "Nueva Receta")
            }

            LazyColumn(modifier = Modifier.fillMaxSize(0.5f)) {
                val lista = recetas.value ?: emptyList()
                if (lista.isEmpty()) {
                    item {
                        Text(text = "No hay recetas para mostrar.")
                    }
                } else {
                    items(lista) { receta ->
                        Text(text = receta.nombre)
                        Text(text = receta.tiempo_preparacion)
                        Text(text = receta.dificultad)
                        TextButton(
                            onClick = {
                                navController.navigate("recetasDetalles/${receta.id}")
                            }
                        ) {
                            Text(text = "Detalles...")
                        }
                        TextButton(
                            onClick = {
                                navController.navigate("recetaUpdateForm/${receta.id}")
                            }
                        ) {
                            Text(text = "🖋️")
                        }
                        TextButton(
                            onClick = {
                                recetaViewModel.eliminarReceta(receta.id)
                            }
                        ) {
                            Text(text = "✖️")
                        }
                    }
                }
            }
        }
    }
}
