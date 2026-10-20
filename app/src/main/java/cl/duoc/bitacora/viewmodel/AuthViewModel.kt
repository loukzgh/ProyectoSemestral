package cl.duoc.bitacora.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.bitacora.data.local.Usuario
import cl.duoc.bitacora.data.repository.BitacoraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: BitacoraRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<Usuario?>(null)
    val currentUser: StateFlow<Usuario?> = _currentUser

    private val _loginError = MutableStateFlow("")
    val loginError: StateFlow<String> = _loginError

    init {
        viewModelScope.launch {
            if (repository.countUsuarios() == 0) {
                repository.insertUsuario(Usuario(correo = "docente1@escuela.cl", contrasena = "1234", nombre = "Ana (Docente)", rol = "Docente"))
                repository.insertUsuario(Usuario(correo = "docente2@escuela.cl", contrasena = "1234", nombre = "Pedro (Docente)", rol = "Docente"))
                repository.insertUsuario(Usuario(correo = "estudiante1@escuela.cl", contrasena = "1234", nombre = "Luis (Estudiante)", rol = "Estudiante"))
                repository.insertUsuario(Usuario(correo = "estudiante2@escuela.cl", contrasena = "1234", nombre = "Maria (Estudiante)", rol = "Estudiante"))
            }
        }
    }

    fun login(correo: String, contrasena: String) {
        if (correo.isBlank() || contrasena.isBlank()) {
            _loginError.value = "Todos los campos son obligatorios"
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            _loginError.value = "Formato de correo inválido"
            return
        }
        viewModelScope.launch {
            val user = repository.login(correo, contrasena)
            if (user != null) {
                _loginError.value = ""
                _currentUser.value = user
            } else {
                _loginError.value = "Credenciales incorrectas"
            }
        }
    }

    fun logout() { _currentUser.value = null }
}