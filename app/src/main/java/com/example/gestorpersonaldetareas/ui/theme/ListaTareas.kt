package com.example.gestorpersonaldetareas.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gestorpersonaldetareas.model.Prioridad
import com.example.gestorpersonaldetareas.model.Tarea

@Composable
fun ListaTareas(tareas: List<Tarea>) {
    if (tareas.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No hay tareas. ¡Añade la primera!")
        }
    } else {
        LazyColumn {
            items(tareas, key = { it.id }) { tarea ->
                TarjetaTarea(
                    tarea = tarea,
                    onCompletadaChange = TODO(),
                    onEditar = TODO(),
                    onEliminar = TODO()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaTareasPreview() {
    val tareasDePrueba = listOf(
        Tarea(id = 1, titulo = "Estudiar Kotlin", prioridad = Prioridad.ALTA),
        Tarea(id = 2, titulo = "Hacer la compra"),
        Tarea(id = 3, titulo = "Llamar al dentista", completada = true)
    )
    ListaTareas(tareas = tareasDePrueba)
}

@Preview(showBackground = true)
@Composable
fun ListaTareasVaciaPreview() {
    ListaTareas(tareas = emptyList())
}

@Composable
fun ListaTareas(
    tareas: List<Tarea>,
    onCompletadaChange: (Tarea, Boolean) -> Unit,
    onEditar: (Tarea) -> Unit,
    onEliminar: (Tarea) -> Unit
) {
    if (tareas.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No hay tareas. ¡Añade la primera!")
        }
    } else {
        LazyColumn {
            items(tareas, key = { it.id }) { tarea ->
                TarjetaTarea(
                    tarea = tarea,
                    onCompletadaChange = { nuevoValor -> onCompletadaChange(tarea, nuevoValor) },
                    onEditar = { onEditar(tarea) },
                    onEliminar = { onEliminar(tarea) }
                )
            }
        }
    }
}