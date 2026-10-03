package com.example.data.model

import java.util.UUID

// ==================== STOCKS ====================
data class StockListing(
    val symbol: String,
    val name: String,
    val sector: String,
    var price: Double,
    var changePercent: Double = 0.0,
    val dividendYield: Double = 0.0,
    val description: String,
    val emoji: String
)

// ==================== BONDS ====================
data class BondListing(
    val id: String,
    val name: String,
    val issuer: String,
    val price: Long,
    val annualYield: Double,
    val maturityYears: Int,
    val riskRating: String, // "AAA", "AA", "A", "BBB", "High Yield"
    val emoji: String
)

// ==================== ETFS ====================
data class EtfListing(
    val symbol: String,
    val name: String,
    val category: String,
    var price: Double,
    var changePercent: Double = 0.0,
    val expenseRatio: Double = 0.05,
    val description: String,
    val emoji: String
)

// ==================== CRYPTO ====================
data class CryptoListing(
    val symbol: String,
    val name: String,
    var price: Double,
    var changePercent: Double = 0.0,
    var marketCapBillions: Double,
    val description: String,
    val emoji: String
)

// ==================== CUSTOM USER CRYPTO ====================
data class CustomCryptoCoin(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val symbol: String,
    var price: Double = 0.001,
    var marketCap: Long = 10000L,
    var circulatingSupply: Long = 10000000L,
    var playerCoinsHeld: Double = 5000000.0,
    var hypeRating: Int = 30, // 0 to 100
    var communityMembers: Long = 500L,
    var devUpdatesCount: Int = 1,
    var hasWhitepaper: Boolean = false,
    var hasAudit: Boolean = false,
    var tokensBurned: Long = 0L,
    var emoji: String = "🪙"
)

// ==================== LOANS ====================
data class LoanPlan(
    val id: String,
    val title: String,
    val category: String, // "Personal", "Education", "Vehicle", "Mortgage", "Business"
    val minAmount: Long,
    val maxAmount: Long,
    val interestRate: Double,
    val minCreditScore: Int,
    val description: String,
    val emoji: String
)
