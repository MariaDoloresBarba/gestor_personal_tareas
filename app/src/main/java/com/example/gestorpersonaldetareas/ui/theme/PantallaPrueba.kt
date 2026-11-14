package com.example.gestorpersonaldetareas.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorpersonaldetareas.model.Tarea

@Composable
fun PantallaPrueba() {
    var tarea by remember { mutableStateOf(Tarea(titulo = "Estudiar Kotlin")) }

    Column(modifier = Modifier.padding(16.dp)) {
        TarjetaTarea(tarea = tarea)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = { nuevoValor ->
                    tarea = tarea.copy(completada = nuevoValor)
                }
            )
            Text(text = "Marcar como completada")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPruebaPreview() {
    PantallaPrueba()
}