package cl.duoc.bitacora.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import cl.duoc.bitacora.viewmodel.BitacoraViewModel

@Composable
fun FormularioRegistroScreen(registroId: Int = 0, bitacoraId: Int, estudianteId: Int, viewModel: BitacoraViewModel, onBack: () -> Unit) {
    var contenido by remember { mutableStateOf("") }
    var evidenciaText by remember { mutableStateOf<String?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var errorMsg by remember { mutableStateOf("") }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) { evidenciaText = uri.toString(); imageBitmap = null }
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) { imageBitmap = bitmap; evidenciaText = "Foto capturada" }
    }

    LaunchedEffect(registroId) {
        if (registroId != 0) {
            val registro = viewModel.getRegistroById(registroId)
            if (registro != null) {
                contenido = registro.contenido
                evidenciaText = registro.evidenciaUri
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(if (registroId == 0) "Nuevo Registro" else "Editar Registro", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contenido,
            onValueChange = { contenido = it; errorMsg = "" },
            label = { Text("Contenido de lo aprendido") },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            isError = errorMsg.isNotEmpty()
        )
        if (errorMsg.isNotEmpty()) Text(errorMsg, color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilledTonalButton(onClick = { galleryLauncher.launch(androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null)
            }
            FilledTonalButton(onClick = { cameraLauncher.launch(null) }) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
            }
        }

        if (evidenciaText != null || imageBitmap != null) {
            Text("Evidencia adjunta correctamente.", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top=8.dp))
            imageBitmap?.let {
                Image(bitmap = it.asImageBitmap(), contentDescription = null, modifier = Modifier.size(100.dp).padding(top=8.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { viewModel.guardarRegistro(registroId, bitacoraId, estudianteId, contenido, evidenciaText, onSuccess = onBack, onError = { errorMsg = it }) },
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (registroId == 0) "Enviar Registro" else "Actualizar Registro") }
    }
}