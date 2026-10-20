package cl.duoc.bitacora.data.local

import android.content.Context
import androidx.room.*

@Database(entities = [Usuario::class, Bitacora::class, Registro::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bitacora_db"
                )
                    .allowMainThreadQueries()
                    .build().also { INSTANCE = it }
            }
        }
    }
}