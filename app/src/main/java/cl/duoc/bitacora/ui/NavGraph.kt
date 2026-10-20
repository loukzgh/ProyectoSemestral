package cl.duoc.bitacora.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.bitacora.ui.screens.*
import cl.duoc.bitacora.viewmodel.AuthViewModel
import cl.duoc.bitacora.viewmodel.BitacoraViewModel

@Composable
fun AppNavigation(authViewModel: AuthViewModel, bitacoraViewModel: BitacoraViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(viewModel = authViewModel) { rol ->
                val destination = if (rol == "Docente") "dashboard_docente" else "dashboard_estudiante"
                navController.navigate(destination) { popUpTo("login") { inclusive = true } }
            }
        }

        composable("dashboard_docente") {
            DashboardDocenteScreen(
                authVM = authViewModel,
                bitacoraVM = bitacoraViewModel,
                onNavigateToCrear = { navController.navigate("crear_bitacora") },
                onNavigateToEditar = { id -> navController.navigate("editar_bitacora/$id") },
                onNavigateToDetalle = { id -> navController.navigate("detalle_bitacora/$id") },
                onNavigateToHistorial = { navController.navigate("historial") },
                onLogout = { navController.navigate("login") { popUpTo(0) } }
            )
        }

        composable("dashboard_estudiante") {
            DashboardEstudianteScreen(
                authVM = authViewModel,
                bitacoraVM = bitacoraViewModel,
                onNavigateToDetalle = { id -> navController.navigate("detalle_bitacora/$id") },
                onNavigateToHistorial = { navController.navigate("historial") },
                onLogout = { navController.navigate("login") { popUpTo(0) } }
            )
        }

        composable("crear_bitacora") {
            val docenteId = authViewModel.currentUser.value?.id ?: 1
            FormularioBitacoraScreen(
                bitacoraId = 0,
                docenteId = docenteId,
                viewModel = bitacoraViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "editar_bitacora/{bitacoraId}",
            arguments = listOf(navArgument("bitacoraId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bitacoraId = backStackEntry.arguments?.getInt("bitacoraId") ?: 0
            val docenteId = authViewModel.currentUser.value?.id ?: 1
            FormularioBitacoraScreen(
                bitacoraId = bitacoraId,
                docenteId = docenteId,
                viewModel = bitacoraViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "detalle_bitacora/{bitacoraId}",
            arguments = listOf(navArgument("bitacoraId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bitacoraId = backStackEntry.arguments?.getInt("bitacoraId") ?: 0
            val rol = authViewModel.currentUser.value?.rol ?: "Estudiante"
            DetalleBitacoraScreen(
                bitacoraId = bitacoraId,
                rol = rol,
                viewModel = bitacoraViewModel,
                onNavigateToCrearRegistro = { navController.navigate("crear_registro/$bitacoraId") },
                onNavigateToEditarRegistro = { registroId -> navController.navigate("editar_registro/$bitacoraId/$registroId") },
                onNavigateToRetroalimentacion = { registroId -> navController.navigate("retroalimentacion/$registroId") },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "crear_registro/{bitacoraId}",
            arguments = listOf(navArgument("bitacoraId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bitacoraId = backStackEntry.arguments?.getInt("bitacoraId") ?: 0
            val estudianteId = authViewModel.currentUser.value?.id ?: 1
            FormularioRegistroScreen(
                registroId = 0,
                bitacoraId = bitacoraId,
                estudianteId = estudianteId,
                viewModel = bitacoraViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "editar_registro/{bitacoraId}/{registroId}",
            arguments = listOf(
                navArgument("bitacoraId") { type = NavType.IntType },
                navArgument("registroId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val bitacoraId = backStackEntry.arguments?.getInt("bitacoraId") ?: 0
            val registroId = backStackEntry.arguments?.getInt("registroId") ?: 0
            val estudianteId = authViewModel.currentUser.value?.id ?: 1
            FormularioRegistroScreen(
                registroId = registroId,
                bitacoraId = bitacoraId,
                estudianteId = estudianteId,
                viewModel = bitacoraViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "retroalimentacion/{registroId}",
            arguments = listOf(navArgument("registroId") { type = NavType.IntType })
        ) { backStackEntry ->
            val registroId = backStackEntry.arguments?.getInt("registroId") ?: 0
            RetroalimentacionScreen(
                registroId = registroId,
                viewModel = bitacoraViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("historial") {
            HistorialScreen(
                viewModel = bitacoraViewModel,
                onNavigateToDetalle = { id -> navController.navigate("detalle_bitacora/$id") },
                onBack = { navController.popBackStack() }
            )
        }
    }
}