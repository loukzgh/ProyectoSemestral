package cl.duoc.bitacora.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.bitacora.viewmodel.BitacoraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleBitacoraScreen(
    bitacoraId: Int,
    rol: String,
    viewModel: BitacoraViewModel,
    onNavigateToCrearRegistro: () -> Unit,
    onNavigateToEditarRegistro: (Int) -> Unit,
    onNavigateToRetroalimentacion: (Int) -> Unit,
    onBack: () -> Unit
) {
    val registros by viewModel.getRegistros(bitacoraId).collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registros de Aprendizaje") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) } }
            )
        },
        floatingActionButton = {
            if (rol == "Estudiante") {
                FloatingActionButton(onClick = onNavigateToCrearRegistro) { Icon(Icons.Default.Add, contentDescription = null) }
            }
        }
    ) { padding ->
        LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxSize().padding(16.dp)) {
            if (registros.isEmpty()) {
                item { Text("No hay registros en esta bitácora.", style = MaterialTheme.typography.bodyLarge) }
            }
            items(registros) { registro ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { if (rol == "Docente") onNavigateToRetroalimentacion(registro.id) },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(registro.contenido, style = MaterialTheme.typography.bodyLarge)
                            if (registro.evidenciaUri != null) {
                                Text("🖼️ Evidencia adjunta", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 4.dp))
                            }
                            if (registro.retroalimentacion != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Retroalimentación Docente:", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
                                Text(registro.retroalimentacion, style = MaterialTheme.typography.bodyMedium)
                            } else if (rol == "Docente") {
                                Text("Toca para retroalimentar", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 8.dp))
                            }
                        }
                        // Solo el estudiante puede editar su registro, y solo si el profe aún no le da retroalimentación
                        if (rol == "Estudiante" && registro.retroalimentacion == null) {
                            IconButton(onClick = { onNavigateToEditarRegistro(registro.id) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                            }
                        }
                    }
                }
            }
        }
    }
}