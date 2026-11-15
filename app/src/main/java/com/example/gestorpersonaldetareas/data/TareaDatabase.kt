package com.example.gestorpersonaldetareas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TareaEntity::class], version = 1, exportSchema = false)
abstract class TareaDatabase : RoomDatabase() {

    abstract fun tareaDao(): TareaDao

    companion object {
        @Volatile
        private var instancia: TareaDatabase? = null

        fun obtener(context: Context): TareaDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    TareaDatabase::class.java,
                    "tareas.db"
                ).build().also { instancia = it }
            }
        }
    }
}