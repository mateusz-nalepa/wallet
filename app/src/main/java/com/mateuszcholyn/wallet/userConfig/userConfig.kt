package com.mateuszcholyn.wallet.userConfig

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class DataStoreConfig(
    val selectedTheme: String?,
)

// TODO: merge all configs into one class XD
class UserConfigProvider(
    private val context: Context,
) {
    companion object {
        // Data Store config
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("walletSettings")
        private val USER_TOKEN_KEY = stringPreferencesKey("userSelectedTheme")
    }

    fun getUserDataStoreConfig(): Flow<DataStoreConfig?> =
        context.dataStore.data.map { preferences ->
            DataStoreConfig(
                selectedTheme = preferences[USER_TOKEN_KEY],
            )
        }

    suspend fun saveSelectedTheme(userSelectedTheme: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = userSelectedTheme
        }
    }

}


