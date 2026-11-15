package com.example.gestorpersonaldetareas.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorpersonaldetareas.model.Tarea

@Composable
fun TarjetaTarea(
    tarea: Tarea,
    onCompletadaChange: (Boolean) -> Unit,
    onEditar: () -> Unit,
    onCompartir: () -> Unit,
    onEliminar: () -> Unit
) {
    val estado = if (tarea.completada) "Completada" else "Pendiente"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = onCompletadaChange
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tarea.titulo, style = MaterialTheme.typography.titleMedium)
                Text(text = "Prioridad: ${tarea.prioridad.name} · $estado")
            }
            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, contentDescription = "Editar tarea")
            }
            IconButton(onClick = onCompartir) {
                Icon(Icons.Default.Share, contentDescription = "Compartir tarea")
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar tarea")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaTareaPreview() {
    TarjetaTarea(
        tarea = Tarea(titulo = "Estudiar Kotlin"),
        onCompletadaChange = {},
        onEditar = {},
        onCompartir = {},
        onEliminar = {}
    )
}