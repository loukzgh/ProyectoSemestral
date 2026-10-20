package cl.duoc.bitacora.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.bitacora.viewmodel.BitacoraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetroalimentacionScreen(
    registroId: Int,
    viewModel: BitacoraViewModel,
    onBack: () -> Unit
) {
    var feedback by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Entregar Retroalimentación") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = feedback,
                onValueChange = { feedback = it },
                label = { Text("Comentarios y sugerencias para el estudiante") },
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    viewModel.agregarRetroalimentacion(registroId, feedback)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = feedback.isNotBlank()
            ) {
                Text("Guardar Retroalimentación")
            }
        }
    }
}