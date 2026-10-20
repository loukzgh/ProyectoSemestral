package cl.duoc.bitacora.data.local

import androidx.room.*

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val rol: String
)

@Entity(
    tableName = "bitacoras",
    foreignKeys = [ForeignKey(entity = Usuario::class, parentColumns = ["id"], childColumns = ["docenteId"])]
)
data class Bitacora(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val asignatura: String,
    val fecha: String,
    val estado: String = "Abierta",
    val docenteId: Int
)

@Entity(
    tableName = "registros",
    foreignKeys = [
        ForeignKey(entity = Bitacora::class, parentColumns = ["id"], childColumns = ["bitacoraId"]),
        ForeignKey(entity = Usuario::class, parentColumns = ["id"], childColumns = ["estudianteId"])
    ]
)
data class Registro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bitacoraId: Int,
    val estudianteId: Int,
    val contenido: String,
    val evidenciaUri: String?,
    val retroalimentacion: String? = null
)