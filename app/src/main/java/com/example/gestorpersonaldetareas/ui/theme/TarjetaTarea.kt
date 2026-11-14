package com.example.gestorpersonaldetareas.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorpersonaldetareas.model.Tarea

@Composable
fun TarjetaTarea(tarea: Tarea) {
    val estado = if (tarea.completada) "Completada" else "Pendiente"

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = tarea.titulo)
        Text(text = "Prioridad: ${tarea.prioridad.name}")
        Text(text = "Estado: $estado")
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaTareaPreview() {
    TarjetaTarea(tarea = Tarea(titulo = "Estudiar Kotlin"))
}