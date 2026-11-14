package com.example.gestorpersonaldetareas.model

data class Tarea(
    val id: Int = 0,
    val titulo: String,
    val descripcion: String = "",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val completada: Boolean = false
)

/*val: el campo no se puede reasignar. Para cambiar algo se crea una copia: tarea.copy(completada = true). Compose trabaja así.
id: Int = 0: el identificador es un número entero. El = 0 es el valor por defecto: una tarea nueva todavía no tiene id real, y más adelante Room lo generará automáticamente, así que 0 significa “aún sin asignar”.
titulo: String: texto. No lleva valor por defecto porque una tarea siempre debe tener título.
descripcion: String = "": texto que por defecto es vacío, porque el enunciado dice que la descripción puede estar vacía.
prioridad: Prioridad = Prioridad.MEDIA: usa tu enum como tipo. Por defecto es media, una decisión mía que puedes cambiar.
completada: Boolean = false: Boolean es verdadero/falso. Una tarea nueva empieza pendiente.
 */
