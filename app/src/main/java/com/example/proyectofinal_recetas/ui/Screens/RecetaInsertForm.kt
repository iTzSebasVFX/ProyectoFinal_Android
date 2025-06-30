package com.example.proyectofinal_recetas.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.proyectofinal_recetas.viewModel.RecetaViewModel
import kotlinx.coroutines.delay

@Composable
fun RecetaInsertForm(navController: NavHostController) {
    val recetaViewModel: RecetaViewModel = hiltViewModel()

    val errorApi = recetaViewModel.errorApi.collectAsState()
    val exitoApi = recetaViewModel.exitoApi.collectAsState()
    val errorLocal = recetaViewModel.errorLocal.collectAsState()
    val exitoLocal = recetaViewModel.exitoLocal.collectAsState()

    var nombre by rememberSaveable { mutableStateOf("") }
    var ingredientes by rememberSaveable { mutableStateOf("") }
    var instrucciones by rememberSaveable { mutableStateOf("") }
    var tiempo by rememberSaveable { mutableStateOf("") }
    var porciones by rememberSaveable { mutableStateOf("") }
    var dificultad by rememberSaveable { mutableStateOf("") }

    val scroll = rememberScrollState()

    // Mensajes de éxito/error
    LaunchedEffect(errorApi.value, exitoApi.value, errorLocal.value, exitoLocal.value) {
        delay(3000)
        if (exitoApi.value != null || exitoLocal.value != null) {
            navController.popBackStack() // vuelve a pantalla anterior (recetasScreen)
        }
        recetaViewModel.clearErroresYExitos()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nueva Receta", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

            listOfNotNull(errorApi.value, exitoApi.value, errorLocal.value, exitoLocal.value).forEach { mensaje ->
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

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ingredientes,
                onValueChange = { ingredientes = it },
                label = { Text("Ingredientes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            OutlinedTextField(
                value = instrucciones,
                onValueChange = { instrucciones = it },
                label = { Text("Instrucciones") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            OutlinedTextField(
                value = tiempo,
                onValueChange = { tiempo = it },
                label = { Text("Tiempo de preparación") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = porciones,
                onValueChange = { porciones = it },
                label = { Text("Porciones") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = dificultad,
                onValueChange = { dificultad = it },
                label = { Text("Dificultad") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    recetaViewModel.crearReceta(
                        nombre,
                        ingredientes,
                        instrucciones,
                        tiempo,
                        porciones,
                        dificultad
                    )
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Crear")
            }
        }
    }
}
