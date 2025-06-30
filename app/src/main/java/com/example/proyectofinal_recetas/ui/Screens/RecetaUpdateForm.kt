package com.example.proyectofinal_recetas.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.proyectofinal_recetas.viewModel.RecetaViewModel
import kotlinx.coroutines.delay

@Composable
fun RecetaUpdateForm(navController: NavHostController, id: Int) {
    val recetaViewModel: RecetaViewModel = hiltViewModel()

    val id_receta = id
    recetaViewModel.obtenerPorId(id_receta)
    val receta = recetaViewModel.recetaPorId.collectAsState()

    val scrollState = rememberScrollState()

    val errorApi = recetaViewModel.errorApi.collectAsState()
    val exitoApi = recetaViewModel.exitoApi.collectAsState()
    val errorLocal = recetaViewModel.errorLocal.collectAsState()
    val exitoLocal = recetaViewModel.exitoLocal.collectAsState()

    LaunchedEffect(errorApi.value, exitoApi.value, errorLocal.value, exitoLocal.value) {
        delay(8000)
        // Solo navega si API tuvo éxito
        if (exitoApi.value != null) {
            navController.navigate("recetasScreen")
        }

        recetaViewModel.clearErroresYExitos()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (errorApi.value != null || exitoApi.value != null || errorLocal.value != null || exitoLocal.value != null) {
            // Mostrar Mensajes
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
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = "Formulario actualizar Receta",
                        fontSize = 30.sp
                    )
                }

                receta.value?.let { it ->
                    item {
                        // Intentar meterlas en la columna de muestra de datos
                        var nm by rememberSaveable { mutableStateOf(it.nombre) }
                        var ing by rememberSaveable { mutableStateOf(it.ingredientes) }
                        var ins by rememberSaveable { mutableStateOf(it.instrucciones) }
                        var tiem_prep by rememberSaveable { mutableStateOf(it.tiempo_preparacion) }
                        var porc by rememberSaveable { mutableStateOf(it.porciones) }
                        var dif by rememberSaveable { mutableStateOf(it.dificultad) }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Editar Receta", fontSize = 24.sp)
                        OutlinedTextField(
                            value = nm,
                            onValueChange = { nm = it },
                            label = { Text("Nuevo nombre") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = ing,
                            onValueChange = { ing = it },
                            label = { Text("Nuevos ingredientes") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = ins,
                            onValueChange = { ins = it },
                            label = { Text("Nuevos ingredientes") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = tiem_prep,
                            onValueChange = { tiem_prep = it },
                            label = { Text("Nuevos ingredientes") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = porc,
                            onValueChange = { porc = it },
                            label = { Text("Nuevos ingredientes") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = dif,
                            onValueChange = { dif = it },
                            label = { Text("Nuevos ingredientes") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                recetaViewModel.actualizarReceta(
                                    id_receta,
                                    nm,
                                    ing,
                                    ins,
                                    tiem_prep,
                                    porc,
                                    dif
                                )
                                nm = ""
                                ing = ""
                                ins = ""
                                tiem_prep = ""
                                porc = ""
                                dif = ""
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Actualizar")
                        }
                    }
                }
            }
        }
    }
}