package cl.duoc.bitacora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cl.duoc.bitacora.data.local.AppDatabase
import cl.duoc.bitacora.data.repository.BitacoraRepository
import cl.duoc.bitacora.ui.AppNavigation
import cl.duoc.bitacora.ui.theme.BitacoraTheme
import cl.duoc.bitacora.viewmodel.AuthViewModel
import cl.duoc.bitacora.viewmodel.BitacoraViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val repository = BitacoraRepository(database.appDao())

        val authViewModel = AuthViewModel(repository)
        val bitacoraViewModel = BitacoraViewModel(repository)

        setContent {
            BitacoraTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation(authViewModel = authViewModel, bitacoraViewModel = bitacoraViewModel)
                }
            }
        }
    }
}