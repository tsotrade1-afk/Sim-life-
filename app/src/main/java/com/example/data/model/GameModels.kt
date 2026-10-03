package com.example.data.model

import java.util.UUID

enum class Gender(val displayName: String, val icon: String) {
    MALE("Male", "👦"),
    FEMALE("Female", "👧"),
    NON_BINARY("Non-Binary", "🧑")
}

data class CountryInfo(
    val name: String,
    val flag: String,
    val currencyCode: String,
    val currencySymbol: String,
    val cities: List<String>
)

enum class EducationLevel(val displayName: String) {
    NONE("None"),
    ELEMENTARY("Elementary School"),
    HIGH_SCHOOL("High School"),
    COLLEGE("University Degree"),
    GRADUATE("Master's / Doctorate")
}

enum class Degree(val displayName: String, val bonusSmarts: Int) {
    NONE("No Degree", 0),
    COMPUTER_SCIENCE("Computer Science", 15),
    BUSINESS("Business & Finance", 10),
    MEDICINE("Medicine & Surgery", 20),
    LAW("Law & Jurisprudence", 15),
    ARTS("Visual & Performing Arts", 8)
}

enum class AssetType(val icon: String) {
    CAR("🚗"),
    REAL_ESTATE("🏠"),
    PET("🐶"),
    PHONE("📱"),
    PC("🖥️"),
    ROUTER("📶"),
    TECH("💻"),
    BUSINESS("🏢")
}

data class Asset(
    val id: String = UUID.randomUUID().toString(),
    val type: AssetType,
    val name: String,
    val value: Long,
    val annualMaintenance: Long = 0,
    val condition: Int = 100
)

enum class RelationshipRole(val title: String, val icon: String) {
    MOTHER("Mother", "👩"),
    FATHER("Father", "👨"),
    BROTHER("Brother", "👦"),
    SISTER("Sister", "👧"),
    BEST_FRIEND("Best Friend", "🤝"),
    PARTNER("Partner", "❤️"),
    PET_DOG("Golden Retriever", "🐕"),
    PET_CAT("Tabby Cat", "🐈")
}

data class Relationship(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val role: RelationshipRole,
    var meter: Int = 85, // 0..100
    val status: String = "Good terms"
)

enum class LogTag(val label: String, val colorHex: Long) {
    GENERAL("Life", 0xFF64748B),
    MILESTONE("Milestone", 0xFFF59E0B),
    HEALTH("Health", 0xFF10B981),
    CAREER("Career", 0xFF6366F1),
    EDUCATION("School", 0xFF3B82F6),
    RELATIONSHIP("Family", 0xFFEC4899),
    WEALTH("Finances", 0xFF14B8A6),
    CLIENT("Clients", 0xFF06B6D4),
    TECH("Technology", 0xFF8B5CF6),
    MISCHIEF("Drama", 0xFFF43F5E)
}

data class LifeLogEntry(
    val age: Int,
    val title: String,
    val description: String,
    val emoji: String,
    val tag: LogTag = LogTag.GENERAL,
    val happinessDelta: Int = 0,
    val healthDelta: Int = 0,
    val smartsDelta: Int = 0,
    val looksDelta: Int = 0,
    val moneyDelta: Long = 0,
    val id: String = UUID.randomUUID().toString()
)

data class Occupation(
    val title: String = "Infant",
    val workplaceOrSchool: String = "Home",
    val annualSalary: Long = 0,
    val performance: Int = 50,
    val isJob: Boolean = false
)

data class InteractiveChoice(
    val text: String,
    val emoji: String = "👉",
    val happinessDelta: Int = 0,
    val healthDelta: Int = 0,
    val smartsDelta: Int = 0,
    val looksDelta: Int = 0,
    val moneyDelta: Long = 0,
    val outcomeNarrative: String
)

data class InteractiveEvent(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val emoji: String,
    val choices: List<InteractiveChoice>,
    val minAge: Int = 0,
    val maxAge: Int = 120
)

data class JobListing(
    val id: String,
    val title: String,
    val company: String,
    val emoji: String,
    val salary: Long,
    val minAge: Int,
    val minSmarts: Int,
    val category: String = "General",
    val requiredDegree: Degree = Degree.NONE,
    val interviewQuestion: String,
    val interviewChoices: List<JobInterviewChoice>
)

data class FoodItem(
    val id: String,
    val name: String,
    val cost: Long,
    val hungerRestore: Int,
    val healthBonus: Int,
    val happinessBonus: Int,
    val emoji: String,
    val description: String
)

data class UberTrip(
    val passengerName: String,
    val destination: String,
    val fare: Long,
    val tip: Long,
    val rating: Float,
    val emoji: String,
    val review: String
)

data class JobInterviewChoice(
    val text: String,
    val isAccepted: Boolean,
    val feedback: String
)

// Client & Freelance System
data class ClientGigApproach(
    val title: String,
    val description: String,
    val successChance: Float, // 0..1
    val successRewardMultiplier: Float,
    val happinessDelta: Int,
    val healthDelta: Int = 0,
    val successNarrative: String,
    val failNarrative: String
)

data class ClientGig(
    val id: String,
    val title: String,
    val clientName: String,
    val clientType: String,
    val emoji: String,
    val basePayout: Long,
    val minAge: Int,
    val minSmarts: Int,
    val description: String,
    val approaches: List<ClientGigApproach>
)

// 2-Step Interactive Actions
data class TwoStepChoiceOption(
    val title: String,
    val subtitle: String,
    val cost: Long = 0L,
    val emoji: String = "👉",
    val happinessDelta: Int = 0,
    val healthDelta: Int = 0,
    val smartsDelta: Int = 0,
    val looksDelta: Int = 0,
    val moneyDelta: Long = 0L,
    val resultTitle: String,
    val resultNarrative: String
)

data class TwoStepActionData(
    val title: String,
    val subtitle: String,
    val emoji: String,
    val category: String,
    val options: List<TwoStepChoiceOption>
)

// News Article in Phone
data class NewsArticle(
    val id: String,
    val year: Int,
    val headline: String,
    val snippet: String,
    val category: String, // Tech, World, Economy, Entertainment, Science
    val source: String,
    val emoji: String,
    val impactOnWorld: String
)

// Richest People Leaderboard (Top 100)
data class BillionaireEntry(
    val rank: Int,
    val name: String,
    val netWorth: Long,
    val industry: String,
    val country: String,
    val isPlayer: Boolean = false,
    val emoji: String = "💼"
)

// Bank App Transactions
data class BankTransaction(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: Long,
    val isIncoming: Boolean,
    val dateOrYear: String,
    val recipient: String? = null
)

// Roadmap & Patch Notes
data class UpdateLogEntry(
    val version: String,
    val releaseDate: String,
    val highlight: String,
    val changes: List<String>,
    val isBigUpdate: Boolean = false
)

data class RoadmapMilestone(
    val phase: String,
    val title: String,
    val status: String, // "Planned", "In Development", "Next Up"
    val icon: String,
    val description: String,
    val highlights: List<String>
)

data class BusinessProduct(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val unitCost: Long,
    val retailPrice: Long,
    val emoji: String,
    var unitsSoldThisYear: Int = 0,
    var isActive: Boolean = true
)

data class PublishedPhoneModel(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val chipTier: String,
    val retailPrice: Long,
    val partnerCompany: String,
    val unitsProduced: Int,
    var unitsSold: Int = 0,
    var totalRevenue: Long = 0L
)

data class BusinessEntity(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val industry: String,
    val emoji: String,
    var valuation: Long,
    var cashBalance: Long,
    var employees: Int = 2,
    var managers: Int = 0,
    var storageCapacity: Int = 250,
    var annualRevenue: Long = 0L,
    var annualExpenses: Long = 0L,
    val products: MutableList<BusinessProduct> = mutableListOf(),
    val publishedPhones: MutableList<PublishedPhoneModel> = mutableListOf()
)

data class BusinessBlueprint(
    val id: String,
    val name: String,
    val industry: String,
    val emoji: String,
    val minCapital: Long,
    val description: String,
    val defaultProducts: List<BusinessProduct>
)

data class Character(
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val country: String,
    val city: String,
    val zodiac: String,
    var birthYear: Int = 2000,
    var currencySymbol: String = "$",
    var currencyCode: String = "USD",
    var age: Int = 0,
    var happiness: Int = 80,
    var health: Int = 95,
    var smarts: Int = 60,
    var looks: Int = 65,
    var bankBalance: Long = 0,
    var occupation: Occupation = Occupation("Baby", "Crib", 0, 50, false),
    var education: EducationLevel = EducationLevel.NONE,
    var degree: Degree = Degree.NONE,
    val relationships: MutableList<Relationship> = mutableListOf(),
    val assets: MutableList<Asset> = mutableListOf(),
    val sideJobs: MutableList<JobListing> = mutableListOf(),
    val businesses: MutableList<BusinessEntity> = mutableListOf(),
    val stockHoldings: MutableMap<String, Long> = mutableMapOf(),
    val bondHoldings: MutableMap<String, Long> = mutableMapOf(),
    val etfHoldings: MutableMap<String, Long> = mutableMapOf(),
    val cryptoHoldings: MutableMap<String, Double> = mutableMapOf(),
    val customCryptoCoins: MutableList<CustomCryptoCoin> = mutableListOf(),
    var creditScore: Int = 710,
    var socialFollowers: Long = 180L,
    var isSocialVerified: Boolean = false,
    val socialPosts: MutableList<SocialPost> = mutableListOf(),
    val chatThreads: MutableList<ChatThread> = mutableListOf(),
    var schoolGradePercent: Int = 86,
    val joinedClubs: MutableList<String> = mutableListOf(),
    var teacherRelationship: Int = 80,
    var schoolDetentions: Int = 0,
    var isAlive: Boolean = true,
    var causeOfDeath: String? = null,
    var ageOfDeath: Int? = null,
    var phoneNotified: Boolean = false,
    var pcNotified: Boolean = false,
    var kickedOutNotified: Boolean = false,
    var hunger: Int = 95,
    var debt: Long = 0L,
    var hasLemonadeStand: Boolean = false,
    var lemonadeSupplies: Int = 15,
    var lemonadePrice: Double = 1.50,
    var lemonadeStandRevenue: Long = 0L,
    var lemonadeStandUpgradeLevel: Int = 1,
    var rideshareTripsThisYear: Int = 0
) {
    val fullName: String get() = "$firstName $lastName"
    val currentYear: Int get() = birthYear + age
    val hasPhone: Boolean get() = assets.any { it.type == AssetType.PHONE || it.name.contains("SimPhone", ignoreCase = true) }
    val hasSimPhoneMax: Boolean get() = assets.any { it.name.contains("Max", ignoreCase = true) }
    val hasSimPhonePro: Boolean get() = assets.any { it.name.contains("Pro", ignoreCase = true) }
    val phoneModelName: String get() = assets.firstOrNull { it.type == AssetType.PHONE || it.name.contains("SimPhone", ignoreCase = true) }?.name ?: "None"
    val hasCar: Boolean get() = assets.any { it.type == AssetType.CAR }
    val hasPC: Boolean get() = assets.any { it.type == AssetType.PC || it.name.contains("Desktop PC", ignoreCase = true) }
    val hasRouter: Boolean get() = assets.any { it.type == AssetType.ROUTER || it.name.contains("Router", ignoreCase = true) }
    val hasInternet: Boolean get() = age < 18 || hasRouter
    val hasHome: Boolean get() = age < 18 || assets.any { it.type == AssetType.REAL_ESTATE }
    val totalPhysicalAssetValue: Long get() = assets.sumOf { it.value }
    val totalBusinessValue: Long get() = businesses.sumOf { it.valuation }
    val totalCustomCryptoValue: Long get() = customCryptoCoins.sumOf { (it.playerCoinsHeld * it.price).toLong() }
    val totalInvestmentsValue: Long get() = totalCustomCryptoValue + stockHoldings.values.sum() * 150L + bondHoldings.values.sum() * 1000L + etfHoldings.values.sum() * 300L + (cryptoHoldings.values.sum() * 1000.0).toLong()
    val netWorth: Long get() = bankBalance + totalPhysicalAssetValue + totalBusinessValue + totalInvestmentsValue - debt
    val bankTransactions: MutableList<BankTransaction> = mutableListOf()

    val avatarEmoji: String
        get() = when {
            !isAlive -> "🪦"
            age < 3 -> "👶"
            age < 12 -> if (gender == Gender.FEMALE) "👧" else "👦"
            age < 20 -> if (gender == Gender.FEMALE) "👱‍♀️" else "👱‍♂️"
            age < 60 -> if (gender == Gender.FEMALE) "👩" else "👨"
            else -> if (gender == Gender.FEMALE) "👵" else "👴"
        }
}
