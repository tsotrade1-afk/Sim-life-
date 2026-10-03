package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "past_lives")
data class PastLifeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val gender: String,
    val country: String,
    val finalAge: Int,
    val netWorth: Long,
    val occupation: String,
    val education: String,
    val causeOfDeath: String,
    val happinessScore: Int,
    val epitaph: String,
    val timestamp: Long = System.currentTimeMillis()
)
