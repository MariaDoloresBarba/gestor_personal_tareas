package com.example.gestorpersonaldetareas.ui

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
import com.example.gestorpersonaldetareas.ui.theme.TarjetaTarea

@Composable
fun ListaTareas(
    tareas: List<Tarea>,
    onCompletadaChange: (Tarea, Boolean) -> Unit,
    onEditar: (Tarea) -> Unit,
    onCompartir: (Tarea) -> Unit,
    onEliminar: (Tarea) -> Unit,
    modifier: Modifier = Modifier,
    mensajeVacio: String = "No hay tareas. ¡Añade la primera!"
) {
    if (tareas.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = mensajeVacio)
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(tareas, key = { it.id }) { tarea ->
                TarjetaTarea(
                    tarea = tarea,
                    onCompletadaChange = { nuevoValor -> onCompletadaChange(tarea, nuevoValor) },
                    onEditar = { onEditar(tarea) },
                    onCompartir = { onCompartir(tarea) },
                    onEliminar = { onEliminar(tarea) }
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
    ListaTareas(
        tareas = tareasDePrueba,
        onCompletadaChange = { _, _ -> },
        onEditar = {},
        onCompartir = {},
        onEliminar = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ListaTareasVaciaPreview() {
    ListaTareas(
        tareas = emptyList(),
        onCompletadaChange = { _, _ -> },
        onEditar = {},
        onCompartir = {},
        onEliminar = {}
    )
}