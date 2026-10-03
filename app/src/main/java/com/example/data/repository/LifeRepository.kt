package com.example.data.repository

import com.example.data.db.LifeDatabase
import com.example.data.db.PastLifeEntity
import com.example.data.db.SavedLifeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LifeRepository(private val database: LifeDatabase) {

    val pastLives: Flow<List<PastLifeEntity>> = database.pastLifeDao().getAllPastLives()

    suspend fun recordPastLife(life: PastLifeEntity) = withContext(Dispatchers.IO) {
        database.pastLifeDao().insertPastLife(life)
    }

    suspend fun deletePastLife(id: Int) = withContext(Dispatchers.IO) {
        database.pastLifeDao().deletePastLife(id)
    }

    suspend fun clearGraveyard() = withContext(Dispatchers.IO) {
        database.pastLifeDao().clearGraveyard()
    }

    suspend fun getSavedLife(): SavedLifeEntity? = withContext(Dispatchers.IO) {
        database.savedLifeDao().getCurrentLife()
    }

    suspend fun saveCurrentLife(life: SavedLifeEntity) = withContext(Dispatchers.IO) {
        database.savedLifeDao().saveCurrentLife(life)
    }

    suspend fun deleteSavedLife() = withContext(Dispatchers.IO) {
        database.savedLifeDao().deleteCurrentLife()
    }
}
