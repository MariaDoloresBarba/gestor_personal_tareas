package com.example.gestorpersonaldetareas.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.gestorpersonaldetareas.model.Tarea

@Composable
fun AppTareas() {
    var tareas by remember { mutableStateOf(emptyList<Tarea>()) }
    var siguienteId by remember { mutableStateOf(1) }
    var mostrandoFormulario by rememberSaveable { mutableStateOf(false) }
    var idEnEdicion by rememberSaveable { mutableStateOf<Int?>(null) }
    var tareaAEliminar by remember { mutableStateOf<Tarea?>(null) }

    Scaffold(
        floatingActionButton = {
            if (!mostrandoFormulario) {
                FloatingActionButton(onClick = {
                    idEnEdicion = null
                    mostrandoFormulario = true
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir tarea")
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (mostrandoFormulario) {
                val tareaEnEdicion = tareas.find { it.id == idEnEdicion }

                FormularioTarea(
                    tareaInicial = tareaEnEdicion,
                    onGuardar = { titulo, descripcion, prioridad ->
                        if (tareaEnEdicion == null) {
                            tareas = tareas + Tarea(
                                id = siguienteId,
                                titulo = titulo,
                                descripcion = descripcion,
                                prioridad = prioridad
                            )
                            siguienteId++
                        } else {
                            tareas = tareas.map {
                                if (it.id == tareaEnEdicion.id) {
                                    it.copy(
                                        titulo = titulo,
                                        descripcion = descripcion,
                                        prioridad = prioridad
                                    )
                                } else it
                            }
                        }
                        mostrandoFormulario = false
                        idEnEdicion = null
                    },
                    onCancelar = {
                        mostrandoFormulario = false
                        idEnEdicion = null
                    }
                )
            } else {
                ListaTareas(
                    tareas = tareas,
                    onCompletadaChange = { tarea, nuevoValor ->
                        tareas = tareas.map {
                            if (it.id == tarea.id) it.copy(completada = nuevoValor) else it
                        }
                    },
                    onEditar = { tarea ->
                        idEnEdicion = tarea.id
                        mostrandoFormulario = true
                    },
                    onEliminar = { tarea -> tareaAEliminar = tarea }
                )
            }
        }
    }

    tareaAEliminar?.let { tarea ->
        AlertDialog(
            onDismissRequest = { tareaAEliminar = null },
            title = { Text("Eliminar tarea") },
            text = { Text("¿Seguro que quieres eliminar \"${tarea.titulo}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    tareas = tareas.filter { it.id != tarea.id }
                    tareaAEliminar = null
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { tareaAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}