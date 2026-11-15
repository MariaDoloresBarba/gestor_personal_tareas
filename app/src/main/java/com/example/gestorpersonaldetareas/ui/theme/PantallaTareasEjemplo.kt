package com.example.gestorpersonaldetareas.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestorpersonaldetareas.network.ClienteRetrofit
import com.example.gestorpersonaldetareas.network.TareaRemota
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface EstadoTareasEjemplo {
    data object Inicial : EstadoTareasEjemplo
    data object Cargando : EstadoTareasEjemplo
    data class Exito(val tareas: List<TareaRemota>) : EstadoTareasEjemplo
    data class Error(val mensaje: String) : EstadoTareasEjemplo
}

@Composable
fun PantallaTareasEjemplo(onVolver: () -> Unit) {
    var estado by remember { mutableStateOf<EstadoTareasEjemplo>(EstadoTareasEjemplo.Inicial) }
    val scope = rememberCoroutineScope()

    fun cargarTareas() {
        if (estado is EstadoTareasEjemplo.Cargando) return
        estado = EstadoTareasEjemplo.Cargando
        scope.launch {
            estado = try {
                EstadoTareasEjemplo.Exito(ClienteRetrofit.api.obtenerTareas(userId = 1))
            } catch (e: IOException) {
                EstadoTareasEjemplo.Error("No se pudo conectar. Comprueba tu conexión a Internet.")
            } catch (e: HttpException) {
                EstadoTareasEjemplo.Error("El servidor respondió con un error (código ${e.code()}).")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Tareas de ejemplo", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Datos de prueba de un servicio externo (JSONPlaceholder). No son tus tareas.")

        Button(
            onClick = { cargarTareas() },
            enabled = estado !is EstadoTareasEjemplo.Cargando
        ) {
            Text("Cargar tareas de ejemplo")
        }
        OutlinedButton(onClick = onVolver) {
            Text("Volver a mis tareas")
        }

        when (val actual = estado) {
            EstadoTareasEjemplo.Inicial -> {
                Text("Pulsa el botón para consultar las tareas de ejemplo.")
            }
            EstadoTareasEjemplo.Cargando -> {
                CircularProgressIndicator()
            }
            is EstadoTareasEjemplo.Error -> {
                Text(text = actual.mensaje, color = MaterialTheme.colorScheme.error)
                Button(onClick = { cargarTareas() }) {
                    Text("Reintentar")
                }
            }
            is EstadoTareasEjemplo.Exito -> {
                if (actual.tareas.isEmpty()) {
                    Text("El servidor no devolvió ninguna tarea.")
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(actual.tareas, key = { it.id }) { tarea ->
                            TarjetaTareaRemota(tarea)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaTareaRemota(tarea: TareaRemota) {
    val estado = if (tarea.completed) "Completada" else "Pendiente"
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "#${tarea.id} · $estado", style = MaterialTheme.typography.labelMedium)
            Text(text = tarea.title)
        }
    }
}