package cl.duoc.bitacora.data.repository

import cl.duoc.bitacora.data.local.AppDao
import cl.duoc.bitacora.data.local.Bitacora
import cl.duoc.bitacora.data.local.Registro
import cl.duoc.bitacora.data.local.Usuario
import kotlinx.coroutines.flow.Flow

class BitacoraRepository(private val dao: AppDao) {
    fun login(correo: String, contrasena: String): Usuario? = dao.login(correo, contrasena)
    fun insertUsuario(usuario: Usuario) = dao.insertUsuario(usuario)
    fun countUsuarios(): Int = dao.countUsuarios()

    fun insertBitacora(bitacora: Bitacora) = dao.insertBitacora(bitacora)
    fun updateBitacora(bitacora: Bitacora) = dao.updateBitacora(bitacora)
    fun getBitacoraById(id: Int): Bitacora? = dao.getBitacoraById(id)
    fun getBitacoras(): Flow<List<Bitacora>> = dao.getBitacoras()
    fun updateEstadoBitacora(id: Int, estado: String) = dao.updateEstadoBitacora(id, estado)

    fun insertRegistro(registro: Registro) = dao.insertRegistro(registro)
    fun updateRegistro(registro: Registro) = dao.updateRegistro(registro)
    fun getRegistroById(id: Int): Registro? = dao.getRegistroById(id)
    fun getRegistrosPorBitacora(bitacoraId: Int): Flow<List<Registro>> = dao.getRegistrosPorBitacora(bitacoraId)
    fun updateRetroalimentacion(id: Int, feedback: String) = dao.updateRetroalimentacion(id, feedback)
}