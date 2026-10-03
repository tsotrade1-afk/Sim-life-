package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PastLifeDao {
    @Query("SELECT * FROM past_lives ORDER BY timestamp DESC")
    fun getAllPastLives(): Flow<List<PastLifeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPastLife(life: PastLifeEntity): Long

    @Query("DELETE FROM past_lives WHERE id = :id")
    suspend fun deletePastLife(id: Int)

    @Query("DELETE FROM past_lives")
    suspend fun clearGraveyard()
}
