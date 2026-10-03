package com.example.data.model

data class SchoolClub(
    val id: String,
    val name: String,
    val emoji: String,
    val category: String, // "Academic", "Arts & Culture", "Athletics", "Leadership"
    val description: String,
    val smartsBonus: Int,
    val happinessBonus: Int
)

data class SchoolClassmate(
    val id: String,
    val name: String,
    val role: String, // "Classmate", "Best Friend", "Class Rival", "Homeroom Teacher"
    val avatar: String,
    var relationshipScore: Int = 75,
    val personality: String
)
