package com.example.gestorpersonaldetareas.data

import com.example.gestorpersonaldetareas.model.Prioridad
import com.example.gestorpersonaldetareas.model.Tarea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TareaRepository(private val dao: TareaDao) {

    val tareas: Flow<List<Tarea>> =
        dao.observarTodas().map { lista -> lista.map { it.toTarea() } }

    suspend fun crear(titulo: String, descripcion: String, prioridad: Prioridad) {
        dao.insertar(
            TareaEntity(
                titulo = titulo,
                descripcion = descripcion,
                prioridad = prioridad,
                completada = false
            )
        )
    }

    suspend fun actualizar(tarea: Tarea) {
        dao.actualizar(tarea.toEntity())
    }

    suspend fun eliminar(tarea: Tarea) {
        dao.eliminar(tarea.toEntity())
    }
}