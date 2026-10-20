package cl.duoc.bitacora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.bitacora.data.local.Bitacora
import cl.duoc.bitacora.data.local.Registro
import cl.duoc.bitacora.data.repository.BitacoraRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BitacoraViewModel(private val repository: BitacoraRepository) : ViewModel() {
    val bitacoras: StateFlow<List<Bitacora>> = repository.getBitacoras()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getBitacoraById(id: Int): Bitacora? = repository.getBitacoraById(id)
    fun getRegistroById(id: Int): Registro? = repository.getRegistroById(id)

    fun guardarBitacora(id: Int = 0, titulo: String, asignatura: String, fecha: String, docenteId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (titulo.isBlank() || asignatura.isBlank() || fecha.isBlank()) {
            onError("Todos los campos son obligatorios")
            return
        }
        if (titulo.length < 5) {
            onError("El título debe tener al menos 5 caracteres")
            return
        }
        viewModelScope.launch {
            if (id == 0) {
                repository.insertBitacora(Bitacora(titulo = titulo, asignatura = asignatura, fecha = fecha, docenteId = docenteId))
            } else {
                val bitacoraExistente = repository.getBitacoraById(id)
                if (bitacoraExistente != null) {
                    repository.updateBitacora(bitacoraExistente.copy(titulo = titulo, asignatura = asignatura, fecha = fecha))
                }
            }
            onSuccess()
        }
    }

    fun cambiarEstadoBitacora(id: Int, nuevoEstado: String) {
        viewModelScope.launch { repository.updateEstadoBitacora(id, nuevoEstado) }
    }

    fun getRegistros(bitacoraId: Int): StateFlow<List<Registro>> = repository.getRegistrosPorBitacora(bitacoraId)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun guardarRegistro(id: Int = 0, bitacoraId: Int, estudianteId: Int, contenido: String, uri: String?, onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (contenido.isBlank()) {
            onError("El contenido no puede estar vacío")
            return
        }
        viewModelScope.launch {
            if (id == 0) {
                repository.insertRegistro(Registro(bitacoraId = bitacoraId, estudianteId = estudianteId, contenido = contenido, evidenciaUri = uri))
            } else {
                val registroExistente = repository.getRegistroById(id)
                if (registroExistente != null) {
                    val nuevaUri = uri ?: registroExistente.evidenciaUri
                    repository.updateRegistro(registroExistente.copy(contenido = contenido, evidenciaUri = nuevaUri))
                }
            }
            onSuccess()
        }
    }

    fun agregarRetroalimentacion(registroId: Int, feedback: String) {
        viewModelScope.launch { repository.updateRetroalimentacion(registroId, feedback) }
    }
}