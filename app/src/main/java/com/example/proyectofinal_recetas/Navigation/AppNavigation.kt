package com.example.proyectofinal_recetas.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal_recetas.ui.Screens.RecetaInsertForm
import com.example.proyectofinal_recetas.ui.Screens.RecetaUpdateForm
import com.example.proyectofinal_recetas.ui.Screens.RecetasDetalles
import com.example.proyectofinal_recetas.ui.Screens.RecetasScreen
import com.example.proyectofinal_recetas.ui.Screens.UsuarioLogin
import com.example.proyectofinal_recetas.ui.Screens.UsuarioRegistro

@Composable
fun appNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "usuarioRegistro") {
        composable("usuarioRegistro") {
            UsuarioRegistro(navController)
        }
        composable("usuarioLogin") {
            UsuarioLogin(navController)
        }
        composable("recetasScreen") {
            RecetasScreen(navController)
        }
        composable("recetaInsertForm") {
            RecetaInsertForm(navController)
        }
        composable("recetaUpdateForm/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
            RecetaUpdateForm(navController, id)
        }
        composable("recetasDetalles/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
            RecetasDetalles(navController, id)
        }
    }
}