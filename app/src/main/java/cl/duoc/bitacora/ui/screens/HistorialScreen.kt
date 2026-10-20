package cl.duoc.bitacora.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.bitacora.ui.theme.StatusClosed
import cl.duoc.bitacora.ui.theme.StatusOpen
import cl.duoc.bitacora.viewmodel.BitacoraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(viewModel: BitacoraViewModel, onNavigateToDetalle: (Int) -> Unit, onBack: () -> Unit) {
    val bitacoras by viewModel.bitacoras.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var filterEstado by remember { mutableStateOf("Todas") }

    // Lógica del filtro (Punto clave de la rúbrica)
    val filteredBitacoras = bitacoras.filter {
        (filterEstado == "Todas" || it.estado == filterEstado) &&
                (it.titulo.contains(searchQuery, ignoreCase = true) || it.asignatura.contains(searchQuery, ignoreCase = true))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial y Búsqueda") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por título o asignatura") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                FilterChip(selected = filterEstado == "Todas", onClick = { filterEstado = "Todas" }, label = { Text("Todas") })
                FilterChip(selected = filterEstado == "Abierta", onClick = { filterEstado = "Abierta" }, label = { Text("Abiertas") })
                FilterChip(selected = filterEstado == "Cerrada", onClick = { filterEstado = "Cerrada" }, label = { Text("Cerradas") })
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (filteredBitacoras.isEmpty()) {
                    item { Text("No se encontraron resultados de tu búsqueda.", modifier = Modifier.padding(16.dp)) }
                }
                items(filteredBitacoras) { bitacora ->
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
}