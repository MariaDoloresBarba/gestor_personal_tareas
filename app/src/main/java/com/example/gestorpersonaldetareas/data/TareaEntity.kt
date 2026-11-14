package com.example.gestorpersonaldetareas.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gestorpersonaldetareas.model.Prioridad
import com.example.gestorpersonaldetareas.model.Tarea

@Entity(tableName = "tareas")
data class TareaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val prioridad: Prioridad,
    val completada: Boolean
)

fun TareaEntity.toTarea(): Tarea = Tarea(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    prioridad = prioridad,
    completada = completada
)

fun Tarea.toEntity(): TareaEntity = TareaEntity(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    prioridad = prioridad,
    completada = completada
)