package cl.duoc.bitacora.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.bitacora.ui.theme.StatusClosed
import cl.duoc.bitacora.ui.theme.StatusOpen
import cl.duoc.bitacora.viewmodel.AuthViewModel
import cl.duoc.bitacora.viewmodel.BitacoraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardEstudianteScreen(
    authVM: AuthViewModel,
    bitacoraVM: BitacoraViewModel,
    onNavigateToDetalle: (Int) -> Unit,
    onNavigateToHistorial: () -> Unit,
    onLogout: () -> Unit
) {
    val bitacoras by bitacoraVM.bitacoras.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Bitácoras") },
                actions = {
                    IconButton(onClick = onNavigateToHistorial) { Icon(Icons.Default.History, contentDescription = "Historial") }
                    IconButton(onClick = { authVM.logout(); onLogout() }) { Icon(Icons.Default.ExitToApp, contentDescription = "Salir") }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize().padding(16.dp)) {
            items(bitacoras) { bitacora ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).animateContentSize().clickable { onNavigateToDetalle(bitacora.id) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(bitacora.titulo, style = MaterialTheme.typography.titleLarge)
                        Text(bitacora.asignatura, style = MaterialTheme.typography.bodyMedium)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(bitacora.fecha, style = MaterialTheme.typography.bodySmall)
                            Text(bitacora.estado, color = if (bitacora.estado == "Abierta") StatusOpen else StatusClosed, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    }
}