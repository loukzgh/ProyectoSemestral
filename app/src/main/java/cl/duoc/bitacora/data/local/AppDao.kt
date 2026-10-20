package cl.duoc.bitacora.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasena = :contrasena")
    fun login(correo: String, contrasena: String): Usuario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsuario(usuario: Usuario)

    @Query("SELECT COUNT(*) FROM usuarios")
    fun countUsuarios(): Int

    @Insert
    fun insertBitacora(bitacora: Bitacora)

    @Update
    fun updateBitacora(bitacora: Bitacora)

    @Query("SELECT * FROM bitacoras WHERE id = :id LIMIT 1")
    fun getBitacoraById(id: Int): Bitacora?

    @Query("SELECT * FROM bitacoras ORDER BY id DESC")
    fun getBitacoras(): Flow<List<Bitacora>>

    @Query("UPDATE bitacoras SET estado = :estado WHERE id = :id")
    fun updateEstadoBitacora(id: Int, estado: String)

    @Insert
    fun insertRegistro(registro: Registro)

    @Update
    fun updateRegistro(registro: Registro)

    @Query("SELECT * FROM registros WHERE id = :id LIMIT 1")
    fun getRegistroById(id: Int): Registro?

    @Query("SELECT * FROM registros WHERE bitacoraId = :bitacoraId")
    fun getRegistrosPorBitacora(bitacoraId: Int): Flow<List<Registro>>

    @Query("UPDATE registros SET retroalimentacion = :feedback WHERE id = :registroId")
    fun updateRetroalimentacion(registroId: Int, feedback: String)
}