package com.example.data.db

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "saved_current_life")
data class SavedLifeEntity(
    @PrimaryKey
    val id: Int = 1,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val country: String,
    val city: String,
    val zodiac: String,
    val birthYear: Int = 2000,
    val currencySymbol: String = "$",
    val currencyCode: String = "USD",
    val age: Int,
    val happiness: Int,
    val health: Int,
    val smarts: Int,
    val looks: Int,
    val bankBalance: Long,
    val occupationTitle: String,
    val workplace: String,
    val salary: Long,
    val performance: Int,
    val isJob: Boolean,
    val education: String,
    val degree: String,
    val isAlive: Boolean,
    val causeOfDeath: String?,
    val relationshipsJson: String,
    val assetsJson: String,
    val logsJson: String,
    val phoneNotified: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Dao
interface SavedLifeDao {
    @Query("SELECT * FROM saved_current_life WHERE id = 1 LIMIT 1")
    suspend fun getCurrentLife(): SavedLifeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentLife(life: SavedLifeEntity)

    @Query("DELETE FROM saved_current_life WHERE id = 1")
    suspend fun deleteCurrentLife()
}
