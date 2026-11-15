package com.example.gestorpersonaldetareas.ui.theme

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import com.example.gestorpersonaldetareas.model.Tarea

fun compartirTarea(context: Context, tarea: Tarea): Boolean {
    val texto = buildString {
        appendLine("Tarea: ${tarea.titulo}")
        if (tarea.descripcion.isNotBlank()) {
            appendLine("Descripción: ${tarea.descripcion}")
        }
        append("Prioridad: ${tarea.prioridad.name}")
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, tarea.titulo)
        putExtra(Intent.EXTRA_TEXT, texto)
    }

    return try {
        context.startActivity(Intent.createChooser(intent, "Compartir tarea"))
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}
