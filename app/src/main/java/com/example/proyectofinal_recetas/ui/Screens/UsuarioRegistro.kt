package com.example.proyectofinal_recetas.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.proyectofinal_recetas.viewModel.UsuarioViewModel
import kotlinx.coroutines.delay

@Composable
fun UsuarioRegistro(navController: NavHostController) {
    val usuarioViewModel: UsuarioViewModel = hiltViewModel()

    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var contraseña by rememberSaveable { mutableStateOf("") }

    val errorApi = usuarioViewModel.errorApi.collectAsState()
    val exitoApi = usuarioViewModel.exitoApi.collectAsState()
    val errorLocal = usuarioViewModel.errorLocal.collectAsState()
    val exitoLocal = usuarioViewModel.exitoLocal.collectAsState()

    LaunchedEffect(errorApi.value, exitoApi.value, errorLocal.value, exitoLocal.value) {
        if (exitoApi.value != null) {
            delay(1000) // opcional para mostrar el mensaje de éxito brevemente
            usuarioViewModel.clearErroresYExitos()
            navController.navigate("usuarioLogin")
        } else {
            delay(8000) // solo espera si hay error o éxito local
            usuarioViewModel.clearErroresYExitos()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0x83002AFF), Color(0xFFB7F5EC), Color(0x83002AFF))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
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
                Text(
                    text = "Registrarse",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text(text = "Nombre") },
                    placeholder = { Text(text = "Ingrese su nombre") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color(0xFF00CCFF),
                        unfocusedContainerColor = Color(0xA60024FF),
                        unfocusedLabelColor = Color.White,
                        unfocusedTextColor = Color(0xFFFFFFFF),
                        focusedIndicatorColor = Color(0xFF31FF00),
                        focusedContainerColor = Color(0xA68A95DE),
                        focusedTextColor = Color(0xFF000000)
                    ),
                    shape = CircleShape
                )
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text(text = "Correo") },
                    placeholder = { Text(text = "Ingrese su correo") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color(0xFF00CCFF),
                        unfocusedContainerColor = Color(0xA60024FF),
                        unfocusedLabelColor = Color.White,
                        unfocusedTextColor = Color(0xFFFFFFFF),
                        focusedIndicatorColor = Color(0xFF31FF00),
                        focusedContainerColor = Color(0xA68A95DE),
                        focusedTextColor = Color(0xFF000000)
                    ),
                    shape = CircleShape
                )
                OutlinedTextField(
                    value = contraseña,
                    onValueChange = { contraseña = it },
                    label = { Text(text = "Contraseña") },
                    placeholder = { Text(text = "Ingrese su contraseña") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color(0xFF00CCFF),
                        unfocusedContainerColor = Color(0xA60024FF),
                        unfocusedLabelColor = Color.White,
                        unfocusedTextColor = Color(0xFFFFFFFF),
                        focusedIndicatorColor = Color(0xFF31FF00),
                        focusedContainerColor = Color(0xA68A95DE),
                        focusedTextColor = Color(0xFF000000)
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(
                    onClick = {
                        usuarioViewModel.registrarUsuario(nombre, correo, contraseña)
                        nombre = ""
                        correo = ""
                        contraseña = ""
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.Black,
                                        Color.Cyan
                                    )
                                ),
                                shape = CircleShape
                            )
                            .padding(horizontal = 18.dp, vertical = 5.dp),
                        text = "Enviar",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
                TextoLoginBoton(navController)
            }
        }
    }
}

@Composable
fun TextoLoginBoton(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Ya tienes cuenta?")
        TextButton(
            onClick = {
                navController.navigate("usuarioLogin")
            }
        ) {
            Text("Acceder")
        }
    }
}