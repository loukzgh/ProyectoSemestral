package cl.duoc.bitacora.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.bitacora.ui.theme.ErrorColor
import cl.duoc.bitacora.viewmodel.BitacoraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioBitacoraScreen(bitacoraId: Int = 0, docenteId: Int, viewModel: BitacoraViewModel, onBack: () -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var asignatura by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }

    LaunchedEffect(bitacoraId) {
        if (bitacoraId != 0) {
            val bitacora = viewModel.getBitacoraById(bitacoraId)
            if (bitacora != null) {
                titulo = bitacora.titulo
                asignatura = bitacora.asignatura
                fecha = bitacora.fecha
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (bitacoraId == 0) "Nueva Bitácora" else "Editar Bitácora") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(value = titulo, onValueChange = { titulo = it; errorMsg = "" }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = asignatura, onValueChange = { asignatura = it; errorMsg = "" }, label = { Text("Asignatura") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = fecha, onValueChange = { fecha = it; errorMsg = "" }, label = { Text("Fecha (DD/MM/AAAA)") }, modifier = Modifier.fillMaxWidth())

            AnimatedVisibility(visible = errorMsg.isNotEmpty()) {
                Text(errorMsg, color = ErrorColor, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { viewModel.guardarBitacora(bitacoraId, titulo, asignatura, fecha, docenteId, onSuccess = onBack, onError = { errorMsg = it }) },
                modifier = Modifier.fillMaxWidth()
            ) { Text(if (bitacoraId == 0) "Guardar Bitácora" else "Actualizar Bitácora") }
        }
    }
}