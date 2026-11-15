package com.example.gestorpersonaldetareas.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferencias")

class PreferenciasUsuario(private val context: Context) {

    private val claveSoloPendientes = booleanPreferencesKey("solo_pendientes")

    val soloPendientes: Flow<Boolean> = context.dataStore.data
        .map { preferencias -> preferencias[claveSoloPendientes] ?: false }

    suspend fun guardarSoloPendientes(valor: Boolean) {
        context.dataStore.edit { preferencias ->
            preferencias[claveSoloPendientes] = valor
        }
    }
}