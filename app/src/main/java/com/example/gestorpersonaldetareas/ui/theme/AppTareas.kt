package com.example.gestorpersonaldetareas.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.gestorpersonaldetareas.data.TareaDatabase
import com.example.gestorpersonaldetareas.data.TareaRepository
import com.example.gestorpersonaldetareas.model.Tarea
import com.example.gestorpersonaldetareas.preferences.PreferenciasUsuario
import kotlinx.coroutines.launch

@Composable
fun AppTareas() {
    val context = LocalContext.current
    val repositorio = remember {
        TareaRepository(TareaDatabase.obtener(context).tareaDao())
    }
    val scope = rememberCoroutineScope()
    val preferencias = remember { PreferenciasUsuario(context.applicationContext) }
    val soloPendientesGuardado by preferencias.soloPendientes.collectAsState(initial = null)

    val tareasCargadas by repositorio.tareas.collectAsState(initial = null)
    val tareas = tareasCargadas ?: emptyList()

    val soloPendientes = soloPendientesGuardado ?: false
    val tareasVisibles = if (soloPendientes) tareas.filter { !it.completada } else tareas

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
                        if (mostrandoFormulario) {
                            mostrandoFormulario = false
                            idEnEdicion = null
                            scope.launch {
                                if (tareaEnEdicion == null) {
                                    repositorio.crear(titulo, descripcion, prioridad)
                                } else {
                                    repositorio.actualizar(
                                        tareaEnEdicion.copy(
                                            titulo = titulo,
                                            descripcion = descripcion,
                                            prioridad = prioridad
                                        )
                                    )
                                }
                            }
                        }
                    },
                    onCancelar = {
                        mostrandoFormulario = false
                        idEnEdicion = null
                    }
                )
            } else if (tareasCargadas == null || soloPendientesGuardado == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mostrar solo pendientes",
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = soloPendientes,
                            onCheckedChange = { nuevoValor ->
                                scope.launch { preferencias.guardarSoloPendientes(nuevoValor) }
                            }
                        )
                    }
                    ListaTareas(
                        tareas = tareasVisibles,
                        mensajeVacio = if (soloPendientes) {
                            "No hay tareas pendientes."
                        } else {
                            "No hay tareas. ¡Añade la primera!"
                        },
                        onCompletadaChange = { tarea, nuevoValor ->
                            scope.launch {
                                repositorio.actualizar(tarea.copy(completada = nuevoValor))
                            }
                        },
                        onEditar = { tarea ->
                            idEnEdicion = tarea.id
                            mostrandoFormulario = true
                        },
                        onEliminar = { tarea -> tareaAEliminar = tarea },
                        modifier = Modifier.weight(1f)
                    )
                }
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
                    tareaAEliminar = null
                    scope.launch { repositorio.eliminar(tarea) }
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