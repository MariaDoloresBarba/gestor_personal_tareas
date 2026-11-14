package com.example.gestorpersonaldetareas.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorpersonaldetareas.model.Prioridad
import com.example.gestorpersonaldetareas.model.Tarea

@Composable
fun FormularioTarea(
    tareaInicial: Tarea? = null,
    onGuardar: (titulo: String, descripcion: String, prioridad: Prioridad) -> Unit,
    onCancelar: () -> Unit
) {
    var titulo by rememberSaveable { mutableStateOf(tareaInicial?.titulo ?: "") }
    var descripcion by rememberSaveable { mutableStateOf(tareaInicial?.descripcion ?: "") }
    var prioridad by rememberSaveable { mutableStateOf(tareaInicial?.prioridad ?: Prioridad.MEDIA) }
    // ... el resto (errorTitulo, menuAbierto y el Column) queda igual

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        var errorTitulo = null
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            isError = errorTitulo != null,
            supportingText = { errorTitulo },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Box {
            OutlinedButton(onClick = { var menuAbierto = true }) {
                Text("Prioridad: ${prioridad.name}")
            }
            var menuAbierto = false
            DropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = { menuAbierto = false }
            ) {
                Prioridad.entries.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion.name) },
                        onClick = {
                            prioridad = opcion
                            menuAbierto = false
                        }
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val error = validarTitulo(titulo)
                errorTitulo = error as Nothing?
                if (error == null) {
                    onGuardar(titulo.trim(), descripcion.trim(), prioridad)
                }
            }) {
                Text("Guardar")
            }
            OutlinedButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioTareaPreview() {
    FormularioTarea(onGuardar = { _, _, _ -> }, onCancelar = {})
}