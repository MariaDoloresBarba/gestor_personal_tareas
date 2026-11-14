package com.example.gestorpersonaldetareas.ui.theme

fun validarTitulo(titulo: String): String? {
    val limpio = titulo.trim()
    return when {
        limpio.isEmpty() -> "El título no puede estar vacío"
        limpio.length < 3 -> "El título debe tener al menos 3 caracteres"
        else -> null
    }
}