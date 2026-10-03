package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.db.PastLifeEntity
import com.example.data.db.SavedLifeEntity
import com.example.data.generators.LifeEventGenerator
import com.example.data.model.*
import com.example.data.repository.LifeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random

enum class LifeSimTab(val title: String, val icon: String) {
    LOG("Life Feed", "📜"),
    ACTIVITIES("Activities", "⚡"),
    CAREER("Career & School", "💼"),
    RELATIONSHIPS("Relationships", "👥"),
    ASSETS("Assets & Wealth", "💎")
}

class LifeViewModel(private val repository: LifeRepository) : ViewModel() {

    private val _character = MutableStateFlow(LifeEventGenerator.createRandomCharacter())
    val character: StateFlow<Character> = _character.asStateFlow()

    private val _lifeLogs = MutableStateFlow<List<LifeLogEntry>>(emptyList())
    val lifeLogs: StateFlow<List<LifeLogEntry>> = _lifeLogs.asStateFlow()

    private val _currentTab = MutableStateFlow(LifeSimTab.LOG)
    val currentTab: StateFlow<LifeSimTab> = _currentTab.asStateFlow()

    private val _activeDilemma = MutableStateFlow<InteractiveEvent?>(null)
    val activeDilemma: StateFlow<InteractiveEvent?> = _activeDilemma.asStateFlow()

    private val _activeJobInterview = MutableStateFlow<JobListing?>(null)
    val activeJobInterview: StateFlow<JobListing?> = _activeJobInterview.asStateFlow()

    private val _activeTwoStepAction = MutableStateFlow<TwoStepActionData?>(null)
    val activeTwoStepAction: StateFlow<TwoStepActionData?> = _activeTwoStepAction.asStateFlow()

    private val _activeClientGig = MutableStateFlow<ClientGig?>(null)
    val activeClientGig: StateFlow<ClientGig?> = _activeClientGig.asStateFlow()

    private val _showUpdateLog = MutableStateFlow(false)
    val showUpdateLog: StateFlow<Boolean> = _showUpdateLog.asStateFlow()

    private val _showPhoneDialog = MutableStateFlow(false)
    val showPhoneDialog: StateFlow<Boolean> = _showPhoneDialog.asStateFlow()

    private val _showSettingsHub = MutableStateFlow(false)
    val showSettingsHub: StateFlow<Boolean> = _showSettingsHub.asStateFlow()

    private val _showInvestmentsDialog = MutableStateFlow(false)
    val showInvestmentsDialog: StateFlow<Boolean> = _showInvestmentsDialog.asStateFlow()

    private val _showBusinessDialog = MutableStateFlow(false)
    val showBusinessDialog: StateFlow<Boolean> = _showBusinessDialog.asStateFlow()

    private val _showSchoolDialog = MutableStateFlow(false)
    val showSchoolDialog: StateFlow<Boolean> = _showSchoolDialog.asStateFlow()

    private val _showSocialDialog = MutableStateFlow(false)
    val showSocialDialog: StateFlow<Boolean> = _showSocialDialog.asStateFlow()

    private val _showMessagesDialog = MutableStateFlow(false)
    val showMessagesDialog: StateFlow<Boolean> = _showMessagesDialog.asStateFlow()

    private val _showLoanDialog = MutableStateFlow(false)
    val showLoanDialog: StateFlow<Boolean> = _showLoanDialog.asStateFlow()

    private val _feedbackMessage = MutableStateFlow<String?>(null)
    val feedbackMessage: StateFlow<String?> = _feedbackMessage.asStateFlow()

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver.asStateFlow()

    val pastLives: StateFlow<List<PastLifeEntity>> = repository.pastLives
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadOrInitializeGame()
    }

    private fun loadOrInitializeGame() {
        viewModelScope.launch {
            val saved = repository.getSavedLife()
            if (saved != null && saved.isAlive) {
                restoreSavedLife(saved)
            } else {
                startBrandNewLife()
            }
        }
    }

    fun setTab(tab: LifeSimTab) {
        _currentTab.value = tab
    }

    fun clearFeedback() {
        _feedbackMessage.value = null
    }

    fun openUpdateLog() {
        _showUpdateLog.value = true
    }

    fun closeUpdateLog() {
        _showUpdateLog.value = false
    }

    fun openPhone() {
        _showPhoneDialog.value = true
    }

    fun closePhone() {
        _showPhoneDialog.value = false
    }

    fun openSettingsHub() {
        _showSettingsHub.value = true
    }

    fun closeSettingsHub() {
        _showSettingsHub.value = false
    }

    fun openInvestments() { _showInvestmentsDialog.value = true }
    fun closeInvestments() { _showInvestmentsDialog.value = false }
    fun openBusiness() { _showBusinessDialog.value = true }
    fun closeBusiness() { _showBusinessDialog.value = false }
    fun openSchool() { _showSchoolDialog.value = true }
    fun closeSchool() { _showSchoolDialog.value = false }
    fun openSocial() { _showSocialDialog.value = true }
    fun closeSocial() { _showSocialDialog.value = false }
    fun openMessages() { _showMessagesDialog.value = true }
    fun closeMessages() { _showMessagesDialog.value = false }
    fun openLoanHub() { _showLoanDialog.value = true }
    fun closeLoanHub() { _showLoanDialog.value = false }

    fun sendBankMoney(recipient: String, amount: Long) {
        val curr = _character.value
        if (curr.bankBalance < amount) {
            _feedbackMessage.value = "Insufficient funds in SimBank account."
            return
        }
        curr.bankBalance -= amount
        val relation = curr.relationships.find { it.name.equals(recipient, ignoreCase = true) }
        if (relation != null) {
            relation.meter = (relation.meter + 15).coerceAtMost(100)
        }
        curr.happiness = (curr.happiness + 8).coerceAtMost(100)

        val tx = BankTransaction(
            title = "Wire Transfer to $recipient",
            amount = amount,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}",
            recipient = recipient
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "SimBank Wire Transfer Sent",
            description = "You wired ${curr.currencySymbol}$amount to $recipient. They expressed immense gratitude!",
            emoji = "💸",
            tag = LogTag.WEALTH,
            moneyDelta = -amount,
            happinessDelta = +8
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy()
        _feedbackMessage.value = "Successfully wired ${curr.currencySymbol}$amount to $recipient."
        saveStateToDb()
    }

    fun receiveBankMoney(amount: Long, source: String) {
        val curr = _character.value
        curr.bankBalance += amount
        curr.happiness = (curr.happiness + 10).coerceAtMost(100)

        val tx = BankTransaction(
            title = source,
            amount = amount,
            isIncoming = true,
            dateOrYear = "Year ${curr.currentYear}",
            recipient = null
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "SimBank Transfer Received",
            description = "Received ${curr.currencySymbol}$amount ($source) deposited into your account.",
            emoji = "💰",
            tag = LogTag.WEALTH,
            moneyDelta = amount,
            happinessDelta = +10
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Received ${curr.currencySymbol}$amount from family!"
        saveStateToDb()
    }

    fun payBankDebt(amount: Long) {
        val curr = _character.value
        val actualPay = amount.coerceAtMost(curr.debt).coerceAtMost(curr.bankBalance)
        if (actualPay <= 0) {
            _feedbackMessage.value = "Cannot pay debt: insufficient balance or no debt."
            return
        }
        curr.bankBalance -= actualPay
        curr.debt = (curr.debt - actualPay).coerceAtLeast(0L)
        curr.happiness = (curr.happiness + 5).coerceAtMost(100)
        curr.creditScore = (curr.creditScore + Random.nextInt(5, 12)).coerceAtMost(850)

        val tx = BankTransaction(
            title = "Loan Principal Repayment",
            amount = actualPay,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Paid Bank Debt (Credit Score Up!)",
            description = "You paid off ${curr.currencySymbol}$actualPay of loan debt. Credit score increased to ${curr.creditScore}! Remaining debt: ${curr.currencySymbol}${curr.debt}.",
            emoji = "🏦",
            tag = LogTag.WEALTH,
            moneyDelta = -actualPay,
            happinessDelta = +5
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Paid ${curr.currencySymbol}$actualPay towards debt! Credit score is now ${curr.creditScore}."
        saveStateToDb()
    }

    fun takeBankLoan(amount: Long) {
        val curr = _character.value
        if (curr.age < 18) {
            _feedbackMessage.value = "Must be 18 to apply for a bank loan."
            return
        }
        val loanCost = (amount * 1.08).toLong()
        curr.bankBalance += amount
        curr.debt += loanCost

        val tx = BankTransaction(
            title = "Personal Loan Disbursement",
            amount = amount,
            isIncoming = true,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Bank Loan Approved",
            description = "SimBank issued a ${curr.currencySymbol}$amount loan (repayable ${curr.currencySymbol}$loanCost with 8% origination).",
            emoji = "💳",
            tag = LogTag.WEALTH,
            moneyDelta = amount
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Loan of ${curr.currencySymbol}$amount deposited!"
        saveStateToDb()
    }

    fun takeLoanPlan(plan: LoanPlan, amount: Long) {
        val curr = _character.value
        if (curr.age < 18) {
            _feedbackMessage.value = "Must be 18 to apply for credit."
            return
        }
        if (curr.creditScore < plan.minCreditScore) {
            _feedbackMessage.value = "Credit score of ${curr.creditScore} is below minimum requirement (${plan.minCreditScore})."
            return
        }
        val interestMultiplier = 1.0 + (plan.interestRate / 100.0)
        val totalOwed = (amount * interestMultiplier).toLong()
        curr.bankBalance += amount
        curr.debt += totalOwed

        val tx = BankTransaction(
            title = "${plan.title} Disbursed",
            amount = amount,
            isIncoming = true,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "${plan.title} Approved (${plan.interestRate}% APR)",
            description = "Approved for ${curr.currencySymbol}$amount under the ${plan.title} plan! Repayable ${curr.currencySymbol}$totalOwed at ${plan.interestRate}% APR.",
            emoji = plan.emoji,
            tag = LogTag.WEALTH,
            moneyDelta = amount
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "${plan.title} disbursed: ${curr.currencySymbol}$amount received!"
        saveStateToDb()
    }

    fun buyFood(food: FoodItem) {
        val curr = _character.value
        if (curr.bankBalance < food.cost) {
            _feedbackMessage.value = "You need ${curr.currencySymbol}${food.cost} to buy ${food.name}."
            return
        }
        curr.bankBalance -= food.cost
        curr.hunger = (curr.hunger + food.hungerRestore).coerceAtMost(100)
        curr.health = (curr.health + food.healthBonus).coerceIn(0, 100)
        curr.happiness = (curr.happiness + food.happinessBonus).coerceIn(0, 100)

        val tx = BankTransaction(
            title = "Food / Groceries: ${food.name}",
            amount = food.cost,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Enjoyed ${food.name}",
            description = "You purchased ${food.name} for ${curr.currencySymbol}${food.cost}. Hunger restored to ${curr.hunger}%.",
            emoji = food.emoji,
            tag = LogTag.HEALTH,
            moneyDelta = -food.cost,
            healthDelta = food.healthBonus,
            happinessDelta = food.happinessBonus
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Ate ${food.name}! Hunger now ${curr.hunger}%."
        saveStateToDb()
    }

    fun startLemonadeStand() {
        val curr = _character.value
        val cost = 75L
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "You need ${curr.currencySymbol}$cost to build a Lemonade Stand."
            return
        }
        curr.bankBalance -= cost
        curr.hasLemonadeStand = true
        curr.lemonadeSupplies = 30
        curr.lemonadePrice = 1.50
        curr.lemonadeStandUpgradeLevel = 1
        curr.happiness = (curr.happiness + 15).coerceAtMost(100)

        val tx = BankTransaction(
            title = "Startup Capital: Lemonade Stand",
            amount = cost,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val asset = Asset(
            type = AssetType.BUSINESS,
            name = "Neighborhood Lemonade Stand",
            value = 120L,
            annualMaintenance = 0L
        )
        curr.assets.add(asset)

        val log = LifeLogEntry(
            age = curr.age,
            title = "🍋 Founded Lemonade Stand!",
            description = "You set up a cheerful yellow wooden stand at the park corner with wooden signs, fresh lemons, and pitcher cups!",
            emoji = "🍋",
            tag = LogTag.WEALTH,
            moneyDelta = -cost,
            happinessDelta = +15
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Your Lemonade Stand is officially open for business!"
        saveStateToDb()
    }

    fun buyLemonadeSupplies() {
        val curr = _character.value
        val cost = 20L
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "You need ${curr.currencySymbol}$cost for lemon supplies."
            return
        }
        curr.bankBalance -= cost
        curr.lemonadeSupplies += 30
        val tx = BankTransaction(
            title = "Restock Lemonade Supplies",
            amount = cost,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Purchased 30 cups of lemons & sugar! Total: ${curr.lemonadeSupplies} cups."
        saveStateToDb()
    }

    fun upgradeLemonadeStand() {
        val curr = _character.value
        val cost = 60L * curr.lemonadeStandUpgradeLevel
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "You need ${curr.currencySymbol}$cost to upgrade your stand."
            return
        }
        curr.bankBalance -= cost
        curr.lemonadeStandUpgradeLevel += 1
        curr.happiness = (curr.happiness + 8).coerceAtMost(100)
        val tx = BankTransaction(
            title = "Lemonade Stand Canopy Upgrade Lv ${curr.lemonadeStandUpgradeLevel}",
            amount = cost,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Upgraded stand to Level ${curr.lemonadeStandUpgradeLevel}! Attracts more customers."
        saveStateToDb()
    }

    fun setLemonadePrice(price: Double) {
        val curr = _character.value
        curr.lemonadePrice = price
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Lemonade price set to ${curr.currencySymbol}$price/cup."
        saveStateToDb()
    }

    fun completeUberRide(trip: UberTrip) {
        val curr = _character.value
        if (curr.rideshareTripsThisYear >= 5) {
            _feedbackMessage.value = "Annual driving cap reached (5/5 trips). Wait until next year to drive more!"
            return
        }
        val totalEarned = trip.fare + trip.tip
        curr.bankBalance += totalEarned
        curr.rideshareTripsThisYear += 1
        curr.happiness = (curr.happiness + 4).coerceAtMost(100)

        val tx = BankTransaction(
            title = "SimRider Fare: ${trip.passengerName}",
            amount = totalEarned,
            isIncoming = true,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "SimRider Gig Completed",
            description = "Drove ${trip.passengerName} to ${trip.destination}. Earned ${curr.currencySymbol}$totalEarned (${curr.currencySymbol}${trip.fare} fare + ${curr.currencySymbol}${trip.tip} tip). Passenger gave a ${trip.rating}⭐ rating: \"${trip.review}\"",
            emoji = "🚗",
            tag = LogTag.CLIENT,
            moneyDelta = totalEarned,
            happinessDelta = +4
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Earned ${curr.currencySymbol}$totalEarned from SimRider trip! (${curr.rideshareTripsThisYear}/5 completed this year)"
        saveStateToDb()
    }

    fun startBrandNewLife(
        name: String? = null,
        gender: Gender? = null,
        country: String? = null,
        birthYear: Int? = null
    ) {
        viewModelScope.launch {
            val newChar = LifeEventGenerator.createRandomCharacter(name, gender, country, birthYear)
            ensureDefaultSocialAndMessages(newChar)
            val birthLog = LifeEventGenerator.getInitialBirthLog(newChar)
            _character.value = newChar
            _lifeLogs.value = listOf(birthLog)
            _activeDilemma.value = null
            _activeJobInterview.value = null
            _activeTwoStepAction.value = null
            _activeClientGig.value = null
            _isGameOver.value = false
            _feedbackMessage.value = "Welcome to the world, ${newChar.fullName}!"
            saveStateToDb()
        }
    }

    // THE CORE SIGNATURE MECHANIC: AGE UP (+1 YEAR)
    fun ageUp() {
        val curr = _character.value
        if (!curr.isAlive) return

        val newAge = curr.age + 1
        curr.age = newAge

        // Financial cash flow
        var cashDelta = 0L
        if (curr.occupation.isJob) {
            val netPay = (curr.occupation.annualSalary * 0.78).toLong() // after tax
            cashDelta += netPay
        } else if (newAge in 7..17) {
            cashDelta += 100L // Annual pocket money/allowance
        }

        // Reset rideshare trips for the new year
        curr.rideshareTripsThisYear = 0

        // Subtract asset maintenance
        val assetExpenses = curr.assets.sumOf { it.annualMaintenance }
        cashDelta -= assetExpenses
        curr.bankBalance = (curr.bankBalance + cashDelta).coerceAtLeast(0L)

        // Hunger / Food degradation: parents feed minors automatically; adult hunger decreases gently (-18/yr)
        if (newAge < 18) {
            curr.hunger = 100
        } else {
            curr.hunger = (curr.hunger - 18).coerceAtLeast(0)
        }

        // Age-related health changes
        if (newAge > 60) {
            val ageTax = Random.nextInt(1, 4)
            curr.health = (curr.health - ageTax).coerceAtLeast(0)
        }
        if (newAge > 75) {
            curr.looks = (curr.looks - Random.nextInt(1, 3)).coerceAtLeast(5)
        }

        // Natural happiness slight fluctuation
        curr.happiness = (curr.happiness + Random.nextInt(-3, 4)).coerceIn(10, 100)

        // Generate narrative timeline events
        val generatedEvents = LifeEventGenerator.generateYearlyEvents(curr).toMutableList()

        // Apply stat changes from yearly events
        for (event in generatedEvents) {
            applyStatDeltas(
                curr,
                event.happinessDelta,
                event.healthDelta,
                event.smartsDelta,
                event.looksDelta,
                event.moneyDelta
            )
        }

        // Add cash flow log if employed
        if (curr.occupation.isJob && cashDelta > 0) {
            generatedEvents.add(
                LifeLogEntry(
                    age = newAge,
                    title = "Annual Salary Paid",
                    description = "You earned $$cashDelta net salary from your job as ${curr.occupation.title} at ${curr.occupation.workplaceOrSchool}.",
                    emoji = "💰",
                    tag = LogTag.WEALTH,
                    moneyDelta = cashDelta
                )
            )
            curr.bankTransactions.add(0, BankTransaction(
                title = "Primary Salary: ${curr.occupation.title}",
                amount = cashDelta,
                isIncoming = true,
                dateOrYear = "Year ${curr.currentYear}"
            ))
        }

        // Side Jobs & Secondary Careers
        for (sideJob in curr.sideJobs) {
            val sidePay = (sideJob.salary * 0.78).toLong()
            curr.bankBalance += sidePay
            generatedEvents.add(
                LifeLogEntry(
                    age = newAge,
                    title = "Side Job Paycheck (${sideJob.title})",
                    description = "You completed your secondary shifts at ${sideJob.company}, bringing home $${sidePay} net income!",
                    emoji = sideJob.emoji,
                    tag = LogTag.CAREER,
                    moneyDelta = sidePay,
                    happinessDelta = +3
                )
            )
            curr.bankTransactions.add(0, BankTransaction(
                title = "Side Hustle: ${sideJob.title}",
                amount = sidePay,
                isIncoming = true,
                dateOrYear = "Year ${curr.currentYear}"
            ))
        }

        // Lemonade Stand business revenue
        if (curr.hasLemonadeStand && curr.lemonadeSupplies > 0) {
            val upgradeMultiplier = 1.0 + (curr.lemonadeStandUpgradeLevel - 1) * 0.25
            val sellRate = (if (curr.lemonadePrice <= 2.0) 0.85 else 0.50) * upgradeMultiplier
            val cupsSold = (curr.lemonadeSupplies * sellRate).toInt().coerceIn(1, curr.lemonadeSupplies)
            val lemonadeEarnings = (cupsSold * curr.lemonadePrice).toLong()
            curr.bankBalance += lemonadeEarnings
            curr.lemonadeStandRevenue += lemonadeEarnings
            curr.lemonadeSupplies = (curr.lemonadeSupplies - cupsSold).coerceAtLeast(0)
            generatedEvents.add(
                LifeLogEntry(
                    age = newAge,
                    title = "🍋 Lemonade Stand Sales",
                    description = "You operated your park lemonade stand! Sold $cupsSold fresh cups at $${curr.lemonadePrice}/cup (Stand Lv ${curr.lemonadeStandUpgradeLevel}), earning $$lemonadeEarnings profit! Supplies remaining: ${curr.lemonadeSupplies} cups.",
                    emoji = "🍋",
                    tag = LogTag.WEALTH,
                    moneyDelta = lemonadeEarnings,
                    happinessDelta = +5
                )
            )
            curr.bankTransactions.add(0, BankTransaction(
                title = "Lemonade Stand Sales (${cupsSold} cups)",
                amount = lemonadeEarnings,
                isIncoming = true,
                dateOrYear = "Year ${curr.currentYear}"
            ))
        }

        // Multi-Enterprises & Business Annual Operations
        for (biz in curr.businesses) {
            val employeeCap = biz.employees * 120
            val effectiveCap = minOf(biz.storageCapacity, employeeCap.coerceAtLeast(60))
            val managerBoost = 1.0 + (biz.managers * 0.25)

            var bizRevenue = 0L
            var bizCost = (biz.employees * 1200L) + (biz.managers * 5000L)

            if (biz.products.isNotEmpty()) {
                val unitsPerProd = (effectiveCap / biz.products.size).coerceAtLeast(10)
                for (prod in biz.products) {
                    val sold = (unitsPerProd * managerBoost * Random.nextDouble(0.8, 1.2)).toInt()
                    prod.unitsSoldThisYear = sold
                    bizRevenue += (sold * prod.retailPrice)
                    bizCost += (sold * prod.unitCost)
                }
            } else {
                bizRevenue = (effectiveCap * 18L * managerBoost).toLong()
            }

            biz.annualRevenue = bizRevenue
            biz.annualExpenses = bizCost
            val netProfit = bizRevenue - bizCost
            biz.cashBalance += netProfit
            biz.valuation = maxOf(biz.valuation + (netProfit * 2).coerceAtLeast(0L), 5000L)

            if (netProfit > 0) {
                val founderPayout = (netProfit * 0.35).toLong()
                curr.bankBalance += founderPayout
                curr.bankTransactions.add(0, BankTransaction(
                    title = "${biz.name} Founder Dividend",
                    amount = founderPayout,
                    isIncoming = true,
                    dateOrYear = "Year ${curr.currentYear}"
                ))
                generatedEvents.add(
                    LifeLogEntry(
                        age = newAge,
                        title = "${biz.emoji} ${biz.name} Annual Earnings",
                        description = "${biz.name} recorded revenue of ${curr.currencySymbol}$bizRevenue with net profit of ${curr.currencySymbol}$netProfit! Received ${curr.currencySymbol}$founderPayout founder dividend.",
                        emoji = biz.emoji,
                        tag = LogTag.WEALTH,
                        moneyDelta = founderPayout,
                        happinessDelta = +6
                    )
                )
            }
        }

        // Investment Portfolio Annual Returns
        val totalShares = curr.stockHoldings.values.sum()
        if (totalShares > 0) {
            val stockDiv = (totalShares * 8L).coerceAtLeast(15L)
            curr.bankBalance += stockDiv
            curr.bankTransactions.add(0, BankTransaction(
                title = "Stock Portfolio Dividends",
                amount = stockDiv,
                isIncoming = true,
                dateOrYear = "Year ${curr.currentYear}"
            ))
            generatedEvents.add(
                LifeLogEntry(
                    age = newAge,
                    title = "📈 Stock Dividends Deposited",
                    description = "Your equity stock portfolio distributed ${curr.currencySymbol}$stockDiv in cash dividends!",
                    emoji = "📈",
                    tag = LogTag.WEALTH,
                    moneyDelta = stockDiv
                )
            )
        }

        val totalBonds = curr.bondHoldings.values.sum()
        if (totalBonds > 0) {
            val bondYield = (totalBonds * 65L).coerceAtLeast(20L)
            curr.bankBalance += bondYield
            curr.bankTransactions.add(0, BankTransaction(
                title = "Bond Coupon Interest Payment",
                amount = bondYield,
                isIncoming = true,
                dateOrYear = "Year ${curr.currentYear}"
            ))
            generatedEvents.add(
                LifeLogEntry(
                    age = newAge,
                    title = "🏛️ Bond Interest Coupon Paid",
                    description = "Your bond holdings paid ${curr.currencySymbol}$bondYield in guaranteed coupon yield.",
                    emoji = "🏛️",
                    tag = LogTag.WEALTH,
                    moneyDelta = bondYield
                )
            )
        }

        // Custom Crypto Token Fluctuations
        for (customCoin in curr.customCryptoCoins) {
            val pumpMultiplier = if (customCoin.hypeRating >= 45) Random.nextDouble(1.15, 1.85) else Random.nextDouble(0.70, 1.10)
            customCoin.price = (customCoin.price * pumpMultiplier).coerceAtLeast(0.00001)
            customCoin.marketCap = (customCoin.circulatingSupply * customCoin.price).toLong()
            customCoin.hypeRating = (customCoin.hypeRating - 6).coerceAtLeast(10)
        }

        // School Annual Report Card
        if (newAge in 5..17 || curr.education == EducationLevel.UNIVERSITY) {
            curr.schoolGradePercent = (curr.schoolGradePercent - Random.nextInt(1, 5)).coerceIn(40, 100)
            if (curr.schoolGradePercent >= 90) {
                curr.smarts = (curr.smarts + 4).coerceAtMost(100)
                curr.happiness = (curr.happiness + 5).coerceAtMost(100)
                generatedEvents.add(
                    LifeLogEntry(
                        age = newAge,
                        title = "🏆 School Honor Roll Achievement",
                        description = "Distinguished academic record! Your GPA earned a spot on the Dean's Honor Roll (+4 Smarts, +5 Happiness).",
                        emoji = "🎓",
                        tag = LogTag.EDUCATION,
                        smartsDelta = +4,
                        happinessDelta = +5
                    )
                )
            } else if (curr.schoolGradePercent < 60) {
                curr.happiness = (curr.happiness - 8).coerceAtLeast(0)
                generatedEvents.add(
                    LifeLogEntry(
                        age = newAge,
                        title = "⚠️ Academic Probation Notice",
                        description = "Your academic average dipped below passing standard. The principal issued a formal improvement notice.",
                        emoji = "📝",
                        tag = LogTag.EDUCATION,
                        happinessDelta = -8
                    )
                )
            }
        }

        // Loan Debt Interest
        if (curr.debt > 0) {
            val interest = (curr.debt * 0.05).toLong().coerceAtLeast(10L)
            curr.debt += interest
            generatedEvents.add(
                LifeLogEntry(
                    age = newAge,
                    title = "Bank Loan Interest Accrued",
                    description = "Your outstanding SimBank loan accrued 5% annual interest ($$interest). Current debt balance: $${curr.debt}. Pay down your balance in the Bank app!",
                    emoji = "🏦",
                    tag = LogTag.WEALTH
                )
            )
        }

        // Hunger penalty
        if (curr.hunger <= 15) {
            curr.health = (curr.health - 12).coerceAtLeast(0)
            curr.happiness = (curr.happiness - 15).coerceAtLeast(0)
            generatedEvents.add(
                LifeLogEntry(
                    age = newAge,
                    title = "⚠️ Starving & Malnourished!",
                    description = "You went without sufficient groceries or meals throughout the year. Your body suffered (-12 Health). Buy food in Activities!",
                    emoji = "🍽️",
                    tag = LogTag.HEALTH,
                    healthDelta = -12,
                    happinessDelta = -15
                )
            )
        }

        // Check for Death
        val mortalityChance = when {
            curr.health <= 0 -> 1.0f
            newAge > 105 -> 0.60f
            newAge > 95 -> 0.25f
            newAge > 85 -> 0.10f
            newAge > 75 -> 0.04f
            else -> 0.001f
        }

        if (curr.health <= 0 || Random.nextFloat() < mortalityChance) {
            triggerDeath(curr, if (curr.health <= 0) "Failing health and exhaustion" else "Peacefully of natural old age")
            val deathLog = LifeLogEntry(
                age = newAge,
                title = "Death of ${curr.fullName}",
                description = "You passed away at the age of $newAge. Cause: ${curr.causeOfDeath}.",
                emoji = "🪦",
                tag = LogTag.MILESTONE
            )
            generatedEvents.add(deathLog)
        } else {
            // Check for Graduation at age 18
            if (newAge == 18) {
                _activeDilemma.value = LifeEventGenerator.getCollegeDecisionEvent()
            } else {
                // Strictly age-bounded dilemma
                val dilemma = LifeEventGenerator.getRandomDilemma(curr)
                if (dilemma != null) {
                    _activeDilemma.value = dilemma
                }
            }
        }

        _lifeLogs.value = generatedEvents + _lifeLogs.value
        _character.value = curr.copy(
            id = UUID.randomUUID().toString(),
            age = newAge
        )
        saveStateToDb()
    }

    private fun triggerDeath(char: Character, cause: String) {
        char.isAlive = false
        char.causeOfDeath = cause
        char.ageOfDeath = char.age
        _isGameOver.value = true

        val netWorth = char.bankBalance + char.assets.sumOf { it.value }
        val epitaph = when {
            char.age >= 90 -> "A celebrated century of wisdom, longevity, and heartwarming memories."
            netWorth > 500000 -> "A wealthy tycoon whose fortunes were remembered by all."
            char.happiness >= 80 -> "A radiant soul who brought unmatched laughter and joy to everyone."
            else -> "Lived a colorful journey through the unpredictable twists of fate."
        }

        val pastLife = PastLifeEntity(
            fullName = char.fullName,
            gender = char.gender.displayName,
            country = char.country,
            finalAge = char.age,
            netWorth = netWorth,
            occupation = char.occupation.title,
            education = char.education.displayName,
            causeOfDeath = cause,
            happinessScore = char.happiness,
            epitaph = epitaph
        )

        viewModelScope.launch {
            repository.recordPastLife(pastLife)
            repository.deleteSavedLife()
        }
    }

    fun selectDilemmaChoice(choice: InteractiveChoice) {
        val curr = _character.value
        applyStatDeltas(
            curr,
            choice.happinessDelta,
            choice.healthDelta,
            choice.smartsDelta,
            choice.looksDelta,
            choice.moneyDelta
        )

        val entry = LifeLogEntry(
            age = curr.age,
            title = _activeDilemma.value?.title ?: "Decision Made",
            description = choice.outcomeNarrative,
            emoji = choice.emoji,
            tag = LogTag.GENERAL,
            happinessDelta = choice.happinessDelta,
            healthDelta = choice.healthDelta,
            smartsDelta = choice.smartsDelta,
            looksDelta = choice.looksDelta,
            moneyDelta = choice.moneyDelta
        )

        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy()
        _activeDilemma.value = null
        saveStateToDb()
    }

    fun dismissDilemma() {
        _activeDilemma.value = null
    }

    // 2-STEP INTERACTIVE ACTION METHODS
    fun triggerGymTwoStep() {
        val curr = _character.value
        if (curr.age < 12) {
            _feedbackMessage.value = "You must be at least 12 years old to use gym facilities."
            return
        }
        _activeTwoStepAction.value = LifeEventGenerator.getGymAction()
    }

    fun triggerDoctorTwoStep() {
        _activeTwoStepAction.value = LifeEventGenerator.getDoctorAction()
    }

    fun triggerReadingTwoStep() {
        _activeTwoStepAction.value = LifeEventGenerator.getReadingAction()
    }

    fun triggerRelationshipTwoStep(relation: Relationship) {
        _activeTwoStepAction.value = LifeEventGenerator.getRelationshipAction(relation)
    }

    fun dismissTwoStepAction() {
        _activeTwoStepAction.value = null
    }

    fun confirmTwoStepAction(option: TwoStepChoiceOption) {
        val curr = _character.value
        if (option.cost > 0L && curr.bankBalance < option.cost && curr.age >= 18) {
            _feedbackMessage.value = "Insufficient funds for this option."
            return
        }
        if (option.cost > 0L && curr.age >= 18) {
            curr.bankBalance -= option.cost
        }

        applyStatDeltas(
            curr,
            option.happinessDelta,
            option.healthDelta,
            option.smartsDelta,
            option.looksDelta,
            option.moneyDelta
        )

        val entry = LifeLogEntry(
            age = curr.age,
            title = option.resultTitle,
            description = option.resultNarrative,
            emoji = option.emoji,
            tag = LogTag.GENERAL,
            happinessDelta = option.happinessDelta,
            healthDelta = option.healthDelta,
            smartsDelta = option.smartsDelta,
            looksDelta = option.looksDelta,
            moneyDelta = -option.cost
        )

        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy()
        _feedbackMessage.value = "${option.resultTitle} completed!"
        _activeTwoStepAction.value = null
        saveStateToDb()
    }

    // ==================== SCHOOL SYSTEM METHODS ====================
    fun studyHarder() {
        val curr = _character.value
        curr.schoolGradePercent = (curr.schoolGradePercent + Random.nextInt(4, 8)).coerceAtMost(100)
        curr.smarts = (curr.smarts + 2).coerceAtMost(100)
        curr.happiness = (curr.happiness - 2).coerceAtLeast(10)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Studied Rigorously",
            description = "You reviewed study guides, solved practice equations, and revised class notes. Academic standing increased to ${curr.schoolGradePercent}%!",
            emoji = "📖",
            tag = LogTag.EDUCATION,
            smartsDelta = +2,
            happinessDelta = -2
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Studied hard! Standing now ${curr.schoolGradePercent}%."
        saveStateToDb()
    }

    fun slackOffSchool() {
        val curr = _character.value
        curr.schoolGradePercent = (curr.schoolGradePercent - Random.nextInt(5, 10)).coerceAtLeast(20)
        curr.happiness = (curr.happiness + 6).coerceAtMost(100)
        val gotDetention = Random.nextFloat() < 0.25f
        if (gotDetention) {
            curr.schoolDetentions += 1
            curr.happiness = (curr.happiness - 8).coerceAtLeast(10)
        }

        val log = LifeLogEntry(
            age = curr.age,
            title = if (gotDetention) "Caught Slacking (Detention Issued!)" else "Daydreamed & Slacked Off",
            description = if (gotDetention) "The teacher noticed you napping in class and assigned after-school detention!" else "You threw paper airplanes and ignored lectures (+6 Happiness, -Standing).",
            emoji = if (gotDetention) "🚨" else "😴",
            tag = LogTag.EDUCATION,
            happinessDelta = if (gotDetention) -8 else +6
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = if (gotDetention) "Caught! Assigned detention." else "Slacked off in class!"
        saveStateToDb()
    }

    fun askTeacherForHelp() {
        val curr = _character.value
        curr.schoolGradePercent = (curr.schoolGradePercent + 4).coerceAtMost(100)
        curr.teacherRelationship = (curr.teacherRelationship + 6).coerceAtMost(100)
        curr.smarts = (curr.smarts + 1).coerceAtMost(100)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Office Hours Consultation",
            description = "You visited your teacher after class with insightful questions. Teacher relationship improved to ${curr.teacherRelationship}%!",
            emoji = "🙋",
            tag = LogTag.EDUCATION,
            smartsDelta = +1
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Teacher appreciated your dedication!"
        saveStateToDb()
    }

    fun hangOutClassmates() {
        val curr = _character.value
        curr.happiness = (curr.happiness + 8).coerceAtMost(100)
        val log = LifeLogEntry(
            age = curr.age,
            title = "Classmate Socializing",
            description = "Spent the afternoon hanging out at the cafeteria courtyard, sharing snacks and laughing with classmates.",
            emoji = "🎒",
            tag = LogTag.RELATIONSHIP,
            happinessDelta = +8
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Had fun with classmates!"
        saveStateToDb()
    }

    fun joinSchoolClub(club: SchoolClub) {
        val curr = _character.value
        if (curr.joinedClubs.contains(club.name)) return
        curr.joinedClubs.add(club.name)
        curr.smarts = (curr.smarts + club.smartsBonus).coerceAtMost(100)
        curr.happiness = (curr.happiness + club.happinessBonus).coerceAtMost(100)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Joined ${club.name}!",
            description = "Inducted as an official member of ${club.name}. ${club.description}",
            emoji = club.emoji,
            tag = LogTag.EDUCATION,
            smartsDelta = club.smartsBonus,
            happinessDelta = club.happinessBonus
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Joined ${club.name}!"
        saveStateToDb()
    }

    fun applyUniversityDegree(deg: Degree) {
        val curr = _character.value
        if (curr.age < 18) {
            _feedbackMessage.value = "Must be 18 to attend University."
            return
        }
        curr.education = EducationLevel.UNIVERSITY
        curr.degree = deg
        curr.occupation = Occupation("University Student (${deg.name.replace("_", " ")})", "Metropolitan State University", 0, 80, false)
        curr.happiness = (curr.happiness + 15).coerceAtMost(100)
        curr.smarts = (curr.smarts + 8).coerceAtMost(100)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Enrolled in University!",
            description = "Accepted into Metropolitan State University studying ${deg.name.replace("_", " ")}! A bright academic future begins.",
            emoji = "🏛️",
            tag = LogTag.EDUCATION,
            happinessDelta = +15,
            smartsDelta = +8
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Enrolled in University for ${deg.name}!"
        saveStateToDb()
    }

    // ==================== INVESTMENTS METHODS ====================
    fun buyStock(stock: StockListing, shares: Long) {
        val curr = _character.value
        val totalCost = (stock.price * shares).toLong()
        if (curr.bankBalance < totalCost) {
            _feedbackMessage.value = "Need ${curr.currencySymbol}$totalCost to buy $shares shares of ${stock.symbol}."
            return
        }
        curr.bankBalance -= totalCost
        val existing = curr.stockHoldings[stock.symbol] ?: 0L
        curr.stockHoldings[stock.symbol] = existing + shares

        val tx = BankTransaction(
            title = "Stock Purchase: $shares ${stock.symbol}",
            amount = totalCost,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Bought ${stock.symbol} Shares",
            description = "Acquired $shares shares of ${stock.name} (${stock.symbol}) for ${curr.currencySymbol}$totalCost at ${curr.currencySymbol}${stock.price}/share.",
            emoji = stock.emoji,
            tag = LogTag.WEALTH,
            moneyDelta = -totalCost
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Bought $shares shares of ${stock.symbol}!"
        saveStateToDb()
    }

    fun sellStock(stock: StockListing, shares: Long) {
        val curr = _character.value
        val owned = curr.stockHoldings[stock.symbol] ?: 0L
        val toSell = minOf(shares, owned)
        if (toSell <= 0) return
        val totalGain = (stock.price * toSell).toLong()
        curr.bankBalance += totalGain
        curr.stockHoldings[stock.symbol] = owned - toSell
        if (curr.stockHoldings[stock.symbol] == 0L) {
            curr.stockHoldings.remove(stock.symbol)
        }

        val tx = BankTransaction(
            title = "Stock Sale: $toSell ${stock.symbol}",
            amount = totalGain,
            isIncoming = true,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Sold ${stock.symbol} Shares",
            description = "Sold $toSell shares of ${stock.name} for ${curr.currencySymbol}$totalGain.",
            emoji = "💵",
            tag = LogTag.WEALTH,
            moneyDelta = totalGain
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Sold $toSell shares of ${stock.symbol} for ${curr.currencySymbol}$totalGain!"
        saveStateToDb()
    }

    fun buyBond(bond: BondListing, count: Long) {
        val curr = _character.value
        val cost = bond.price * count
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "Insufficient funds for bond purchase."
            return
        }
        curr.bankBalance -= cost
        val owned = curr.bondHoldings[bond.id] ?: 0L
        curr.bondHoldings[bond.id] = owned + count

        val tx = BankTransaction(
            title = "Bond Investment: ${bond.name}",
            amount = cost,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Invested ${curr.currencySymbol}$cost in ${bond.name}!"
        saveStateToDb()
    }

    fun sellBond(bond: BondListing, count: Long) {
        val curr = _character.value
        val owned = curr.bondHoldings[bond.id] ?: 0L
        val toSell = minOf(count, owned)
        if (toSell <= 0) return
        val proceeds = bond.price * toSell
        curr.bankBalance += proceeds
        curr.bondHoldings[bond.id] = owned - toSell
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Redeemed $toSell bonds for ${curr.currencySymbol}$proceeds!"
        saveStateToDb()
    }

    fun buyEtf(etf: EtfListing, shares: Long) {
        val curr = _character.value
        val cost = (etf.price * shares).toLong()
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "Insufficient funds for ETF purchase."
            return
        }
        curr.bankBalance -= cost
        val owned = curr.etfHoldings[etf.symbol] ?: 0L
        curr.etfHoldings[etf.symbol] = owned + shares
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Purchased $shares shares of ${etf.symbol}!"
        saveStateToDb()
    }

    fun sellEtf(etf: EtfListing, shares: Long) {
        val curr = _character.value
        val owned = curr.etfHoldings[etf.symbol] ?: 0L
        val toSell = minOf(shares, owned)
        if (toSell <= 0) return
        val proceeds = (etf.price * toSell).toLong()
        curr.bankBalance += proceeds
        curr.etfHoldings[etf.symbol] = owned - toSell
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Sold $toSell shares of ${etf.symbol}!"
        saveStateToDb()
    }

    fun buyCrypto(coin: CryptoListing, usdAmount: Double) {
        val curr = _character.value
        val cost = usdAmount.toLong()
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "Insufficient bank balance."
            return
        }
        curr.bankBalance -= cost
        val coinsBought = usdAmount / coin.price
        val owned = curr.cryptoHoldings[coin.symbol] ?: 0.0
        curr.cryptoHoldings[coin.symbol] = owned + coinsBought

        val tx = BankTransaction(
            title = "Crypto Buy: ${coin.symbol}",
            amount = cost,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Acquired ${coin.symbol} ($${usdAmount.toLong()})!"
        saveStateToDb()
    }

    fun sellCrypto(coin: CryptoListing, usdAmount: Double) {
        val curr = _character.value
        val ownedCoins = curr.cryptoHoldings[coin.symbol] ?: 0.0
        val maxUsd = ownedCoins * coin.price
        val proceeds = minOf(usdAmount, maxUsd).toLong()
        if (proceeds <= 0) return
        curr.bankBalance += proceeds
        curr.cryptoHoldings.remove(coin.symbol)

        val tx = BankTransaction(
            title = "Crypto Sale: ${coin.symbol}",
            amount = proceeds,
            isIncoming = true,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Cashed out ${coin.symbol} for ${curr.currencySymbol}$proceeds!"
        saveStateToDb()
    }

    fun createCustomCryptoCoin(name: String, ticker: String, funding: Long) {
        val curr = _character.value
        if (curr.bankBalance < funding) {
            _feedbackMessage.value = "Need ${curr.currencySymbol}$funding in liquidity."
            return
        }
        curr.bankBalance -= funding
        val coin = CustomCryptoCoin(
            name = name,
            symbol = ticker.uppercase(),
            price = (funding.toDouble() / 10000000.0).coerceAtLeast(0.0001),
            marketCap = funding,
            circulatingSupply = 10000000L,
            playerCoinsHeld = 5000000.0,
            hypeRating = 50
        )
        curr.customCryptoCoins.add(coin)

        val tx = BankTransaction(
            title = "Crypto Creation: $$ticker Liquidity",
            amount = funding,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Launched Crypto Token: $name ($$ticker)!",
            description = "You deployed your own cryptocurrency token with ${curr.currencySymbol}$funding in initial liquidity! You hold 5,000,000 founder tokens.",
            emoji = "🚀",
            tag = LogTag.WEALTH,
            moneyDelta = -funding,
            happinessDelta = +12
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Deployed $name ($$ticker) to the blockchain!"
        saveStateToDb()
    }

    fun boostCustomCrypto(coinId: String, actionType: String) {
        val curr = _character.value
        val coin = curr.customCryptoCoins.find { it.id == coinId } ?: return
        when (actionType) {
            "marketing" -> {
                if (curr.bankBalance < 500L) return
                curr.bankBalance -= 500L
                coin.hypeRating = (coin.hypeRating + 25).coerceAtMost(100)
                coin.communityMembers += 1200L
                coin.price *= 1.25
                _feedbackMessage.value = "Viral campaign launched! Token price surged +25%!"
            }
            "whitepaper" -> {
                if (curr.bankBalance < 200L) return
                curr.bankBalance -= 200L
                coin.hasWhitepaper = true
                coin.hypeRating = (coin.hypeRating + 15).coerceAtMost(100)
                coin.price *= 1.20
                _feedbackMessage.value = "Whitepaper published! Credibility and price boosted!"
            }
            "token_burn" -> {
                coin.tokensBurned += (coin.circulatingSupply * 0.15).toLong()
                coin.circulatingSupply = (coin.circulatingSupply * 0.85).toLong()
                coin.price *= 1.35
                _feedbackMessage.value = "15% of supply burned! Price surged +35%!"
            }
            "dev_commits" -> {
                coin.devUpdatesCount += 1
                coin.price *= 1.10
                _feedbackMessage.value = "GitHub commits pushed! Investor confidence up +10%!"
            }
            "influencer" -> {
                if (curr.bankBalance < 1500L) return
                curr.bankBalance -= 1500L
                coin.hypeRating = (coin.hypeRating + 40).coerceAtMost(100)
                coin.price *= 1.50
                _feedbackMessage.value = "Celebrity shoutout went viral! Price pumped +50%!"
            }
        }
        coin.marketCap = (coin.circulatingSupply * coin.price).toLong()
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        saveStateToDb()
    }

    fun sellCustomCrypto(coinId: String, amount: Double) {
        val curr = _character.value
        val coin = curr.customCryptoCoins.find { it.id == coinId } ?: return
        val toSell = minOf(amount, coin.playerCoinsHeld)
        val proceeds = (toSell * coin.price).toLong()
        if (proceeds <= 0) return
        curr.bankBalance += proceeds
        coin.playerCoinsHeld -= toSell

        val tx = BankTransaction(
            title = "Founder Cash Out: ${coin.name}",
            amount = proceeds,
            isIncoming = true,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Cashed out ${curr.currencySymbol}$proceeds from founder tokens!"
        saveStateToDb()
    }

    // ==================== BUSINESS TYCOON METHODS ====================
    fun startBusiness(blueprint: BusinessBlueprint) {
        val curr = _character.value
        if (curr.bankBalance < blueprint.minCapital) {
            _feedbackMessage.value = "Need ${curr.currencySymbol}${blueprint.minCapital} minimum startup capital."
            return
        }
        curr.bankBalance -= blueprint.minCapital
        val biz = BusinessEntity(
            name = blueprint.name,
            industry = blueprint.industry,
            emoji = blueprint.emoji,
            valuation = blueprint.minCapital * 2,
            cashBalance = blueprint.minCapital / 2,
            employees = 2,
            managers = 0,
            storageCapacity = 250
        )
        biz.products.addAll(blueprint.defaultProducts)
        curr.businesses.add(biz)

        val tx = BankTransaction(
            title = "Business Founding: ${blueprint.name}",
            amount = blueprint.minCapital,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "Founded ${blueprint.name}!",
            description = "You founded a new enterprise in ${blueprint.industry} with ${curr.currencySymbol}${blueprint.minCapital} startup capital!",
            emoji = blueprint.emoji,
            tag = LogTag.WEALTH,
            moneyDelta = -blueprint.minCapital,
            happinessDelta = +15
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Founded ${blueprint.name}!"
        saveStateToDb()
    }

    fun hireEmployee(businessId: String, count: Int) {
        val curr = _character.value
        val biz = curr.businesses.find { it.id == businessId } ?: return
        val cost = count * 500L
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "Need ${curr.currencySymbol}$cost onboarding fee."
            return
        }
        curr.bankBalance -= cost
        biz.employees += count
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Hired $count employee(s) for ${biz.name}!"
        saveStateToDb()
    }

    fun hireManager(businessId: String) {
        val curr = _character.value
        val biz = curr.businesses.find { it.id == businessId } ?: return
        val cost = 5000L
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "Need ${curr.currencySymbol}$cost for general manager."
            return
        }
        curr.bankBalance -= cost
        biz.managers += 1
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Hired General Manager for ${biz.name}! (+25% Efficiency)"
        saveStateToDb()
    }

    fun upgradeBusinessStorage(businessId: String) {
        val curr = _character.value
        val biz = curr.businesses.find { it.id == businessId } ?: return
        val cost = 3000L
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "Need ${curr.currencySymbol}$cost for storage expansion."
            return
        }
        curr.bankBalance -= cost
        biz.storageCapacity += 500
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Expanded warehouse to ${biz.storageCapacity} units!"
        saveStateToDb()
    }

    fun addProductToBusiness(businessId: String, name: String, unitCost: Long, price: Long, emoji: String) {
        val curr = _character.value
        val biz = curr.businesses.find { it.id == businessId } ?: return
        biz.products.add(BusinessProduct(name = name, unitCost = unitCost, retailPrice = price, emoji = emoji))
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Added $name to ${biz.name} product catalog!"
        saveStateToDb()
    }

    fun takeBusinessPublic(businessId: String) {
        val curr = _character.value
        val biz = curr.businesses.find { it.id == businessId } ?: return
        if (biz.valuation < 1000000L) {
            _feedbackMessage.value = "Requires $1,000,000 minimum valuation to go public."
            return
        }
        val ipoPayout = 500000L
        curr.bankBalance += ipoPayout
        biz.valuation = (biz.valuation * 1.5).toLong()

        val tx = BankTransaction(
            title = "IPO Founder Liquidity: ${biz.name}",
            amount = ipoPayout,
            isIncoming = true,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val log = LifeLogEntry(
            age = curr.age,
            title = "🔔 Initial Public Offering (IPO) for ${biz.name}!",
            description = "You rang the opening bell on Wall Street! ${biz.name} went public on the stock exchange. You received ${curr.currencySymbol}$ipoPayout founder windfall!",
            emoji = "🔔",
            tag = LogTag.WEALTH,
            moneyDelta = ipoPayout,
            happinessDelta = +25
        )
        _lifeLogs.value = listOf(log) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "${biz.name} is now publicly traded on Wall Street!"
        saveStateToDb()
    }

    // ==================== SOCIAL MEDIA & MESSAGING ====================
    fun publishSocialPost(content: String, isVideo: Boolean, tags: List<String>, taggedPerson: String?) {
        val curr = _character.value
        val likes = Random.nextLong(curr.socialFollowers / 6, curr.socialFollowers / 2 + 10).coerceAtLeast(8L)
        val retweets = Random.nextLong(1, likes / 4 + 2)
        val views = if (isVideo) likes * 4 else 0L

        val comments = mutableListOf<SocialComment>()
        if (taggedPerson != null) {
            comments.add(
                SocialComment(
                    authorName = taggedPerson,
                    authorHandle = "@${taggedPerson.lowercase().replace(" ", "_")}",
                    authorAvatar = "⭐",
                    text = "Appreciate the shoutout! Keep inspiring us 🔥",
                    isFamilyOrParent = true
                )
            )
        } else {
            val mom = curr.relationships.firstOrNull { it.role == RelationshipRole.MOTHER }
            if (mom != null && Random.nextBoolean()) {
                comments.add(
                    SocialComment(
                        authorName = mom.name,
                        authorHandle = "@${mom.name.lowercase().replace(" ", "")}",
                        authorAvatar = "👩",
                        text = "So proud of you my darling! Always keep your values high ❤️",
                        isFamilyOrParent = true
                    )
                )
            }
        }
        comments.add(
            SocialComment(
                authorName = "SimBot_Pro",
                authorHandle = "@simbot_ai",
                authorAvatar = "🤖",
                text = "Huge momentum! Great post on the timeline 🚀",
                isFamilyOrParent = false
            )
        )

        val post = SocialPost(
            authorName = curr.fullName,
            authorHandle = "@${curr.firstName.lowercase()}_${curr.lastName.lowercase()}",
            authorAvatar = curr.avatarEmoji,
            content = content,
            isVideo = isVideo,
            videoViews = views,
            likes = likes,
            retweets = retweets,
            comments = comments,
            timestamp = "Just now",
            tags = tags,
            taggedPerson = taggedPerson,
            isPlayerPost = true
        )

        curr.socialPosts.add(0, post)
        val newFollowers = Random.nextLong(15, 60)
        curr.socialFollowers += newFollowers
        curr.happiness = (curr.happiness + 4).coerceAtMost(100)

        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Tweet published! Gained +$likes likes and +$newFollowers followers!"
        saveStateToDb()
    }

    fun sendChatMessage(threadId: String, text: String) {
        val curr = _character.value
        val thread = curr.chatThreads.find { it.id == threadId } ?: return
        val playerMsg = ChatMessage(
            senderName = curr.fullName,
            text = text,
            isFromPlayer = true,
            timestamp = "Just now"
        )
        thread.messages.add(playerMsg)
        thread.isTyping = true
        _character.value = curr.copy(id = UUID.randomUUID().toString())

        viewModelScope.launch {
            kotlinx.coroutines.delay(1500) // 1.5 seconds fast reply!
            val replyText = when {
                text.contains("loan", ignoreCase = true) || text.contains("50", ignoreCase = true) -> {
                    curr.bankBalance += 50L
                    "Of course! Just wired you $50. Make sure to use it wisely! ❤️"
                }
                text.contains("hang out", ignoreCase = true) -> "Yes, absolutely! Let's get pizza and hang out this weekend! 🍕"
                text.contains("stress", ignoreCase = true) -> "Hang in there. Take a deep breath. Things will get better! We're here for you."
                text.contains("love", ignoreCase = true) -> "Aww, you just made my day! Love you so much! ❤️"
                text.contains("crypto", ignoreCase = true) || text.contains("business", ignoreCase = true) -> "Wow, that sounds so ambitious! Cheering on your success! 🚀"
                else -> "Great to hear from you! Always keep in touch and let me know how you're doing."
            }

            val replyMsg = ChatMessage(
                senderName = thread.recipientName,
                text = replyText,
                isFromPlayer = false,
                timestamp = "Just now"
            )
            thread.messages.add(replyMsg)
            thread.isTyping = false
            curr.happiness = (curr.happiness + 4).coerceAtMost(100)
            val relation = curr.relationships.find { it.name.equals(thread.recipientName, ignoreCase = true) }
            if (relation != null) {
                relation.meter = (relation.meter + 5).coerceAtMost(100)
            }
            _character.value = curr.copy(id = UUID.randomUUID().toString())
            saveStateToDb()
        }
    }

    private fun ensureDefaultSocialAndMessages(char: Character) {
        if (char.chatThreads.isEmpty()) {
            val mom = char.relationships.firstOrNull { it.role == RelationshipRole.MOTHER }?.name ?: "Mom"
            val dad = char.relationships.firstOrNull { it.role == RelationshipRole.FATHER }?.name ?: "Dad"
            val sib = char.relationships.firstOrNull { it.role == RelationshipRole.BROTHER || it.role == RelationshipRole.SISTER }?.name

            char.chatThreads.add(
                ChatThread(
                    recipientName = mom,
                    recipientRole = "Mother",
                    recipientAvatar = "👩",
                    messages = mutableListOf(
                        ChatMessage(senderName = mom, text = "Don't forget to eat well and call us! Love you ❤️", isFromPlayer = false, timestamp = "Yesterday")
                    )
                )
            )
            char.chatThreads.add(
                ChatThread(
                    recipientName = dad,
                    recipientRole = "Father",
                    recipientAvatar = "👨",
                    messages = mutableListOf(
                        ChatMessage(senderName = dad, text = "Hey kiddo! Keep working hard. We are proud of you 👍", isFromPlayer = false, timestamp = "Yesterday")
                    )
                )
            )
            if (sib != null) {
                char.chatThreads.add(
                    ChatThread(
                        recipientName = sib,
                        recipientRole = "Sibling",
                        recipientAvatar = "🧒",
                        messages = mutableListOf(
                            ChatMessage(senderName = sib, text = "Did you finish your school homework? 😂", isFromPlayer = false, timestamp = "2d ago")
                        )
                    )
                )
            }
            char.chatThreads.add(
                ChatThread(
                    recipientName = "Alex Rivera",
                    recipientRole = "Best Friend",
                    recipientAvatar = "🧑",
                    messages = mutableListOf(
                        ChatMessage(senderName = "Alex Rivera", text = "Are you free to hang out after school? Let's play games! 🎮", isFromPlayer = false, timestamp = "3h ago")
                    )
                )
            )
            char.chatThreads.add(
                ChatThread(
                    recipientName = "SimBot Assistant",
                    recipientRole = "AI Helper",
                    recipientAvatar = "🤖",
                    messages = mutableListOf(
                        ChatMessage(senderName = "SimBot Assistant", text = "Welcome to LifeSim! Ask me about businesses, stocks, crypto, or career advice anytime.", isFromPlayer = false, timestamp = "Just now")
                    )
                )
            )
        }

        if (char.socialPosts.isEmpty()) {
            char.socialPosts.add(
                SocialPost(
                    authorName = "TechCrunch Sim",
                    authorHandle = "@techcrunch",
                    authorAvatar = "📰",
                    content = "Global tech equities and AI chipmaker TensorCore surge to record high market caps! 📈⚡",
                    likes = 12400L,
                    retweets = 3100L,
                    timestamp = "1h ago",
                    tags = listOf("#Tech", "#Crypto"),
                    isPlayerPost = false
                )
            )
            char.socialPosts.add(
                SocialPost(
                    authorName = "Elon Musk Sim",
                    authorHandle = "@elonmusk_sim",
                    authorAvatar = "🚀",
                    content = "Next-gen humanoid robots and gigafactory expansion ahead of schedule. The future is exciting! 🤖",
                    isVideo = true,
                    videoViews = 85000L,
                    likes = 45200L,
                    retweets = 18900L,
                    timestamp = "4h ago",
                    tags = listOf("#Tech", "#Flex"),
                    isPlayerPost = false
                )
            )
        }
    }

    // CLIENT GIGS
    fun openClientGig(gig: ClientGig) {
        val curr = _character.value
        if (curr.age < gig.minAge) {
            _feedbackMessage.value = "You must be at least ${gig.minAge} years old for this client."
            return
        }
        if (curr.smarts < gig.minSmarts) {
            _feedbackMessage.value = "Client requires smarts of at least ${gig.minSmarts}%."
            return
        }
        _activeClientGig.value = gig
    }

    fun dismissClientGig() {
        _activeClientGig.value = null
    }

    fun completeClientGig(payout: Long, happinessDelta: Int, healthDelta: Int, narrative: String) {
        val curr = _character.value
        val gig = _activeClientGig.value ?: return
        curr.bankBalance += payout
        curr.happiness = (curr.happiness + happinessDelta).coerceIn(0, 100)
        curr.health = (curr.health + healthDelta).coerceIn(0, 100)

        val entry = LifeLogEntry(
            age = curr.age,
            title = "Completed: ${gig.title}",
            description = "$narrative Client '${gig.clientName}' settled payment for $$payout.",
            emoji = gig.emoji,
            tag = LogTag.CLIENT,
            happinessDelta = happinessDelta,
            healthDelta = healthDelta,
            moneyDelta = payout
        )

        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy()
        _feedbackMessage.value = "Earned +$$payout from client!"
        _activeClientGig.value = null
        saveStateToDb()
    }

    // ACTIVITIES
    fun doMeditation() {
        val curr = _character.value
        curr.happiness = (curr.happiness + Random.nextInt(8, 15)).coerceAtMost(100)
        curr.health = (curr.health + 3).coerceAtMost(100)

        val entry = LifeLogEntry(
            age = curr.age,
            title = "Mindfulness & Meditation",
            description = "You sat in stillness for 45 minutes, practicing deep breathing and inner serenity.",
            emoji = "🧘",
            tag = LogTag.HEALTH,
            happinessDelta = +10,
            healthDelta = +3
        )
        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy()
        _feedbackMessage.value = "Inner peace achieved. +Happiness!"
        saveStateToDb()
    }

    fun playLottery() {
        val curr = _character.value
        if (curr.age < 18) {
            _feedbackMessage.value = "You must be 18+ to buy lottery tickets."
            return
        }
        if (curr.bankBalance < 10) {
            _feedbackMessage.value = "You need $10 to buy a scratch-off ticket."
            return
        }
        curr.bankBalance -= 10

        val roll = Random.nextInt(100)
        val (prize, narrative) = when {
            roll == 0 -> Pair(50000L, "🎉 JACKPOT! You scratched off three lucky stars and won $50,000!")
            roll < 5 -> Pair(1000L, "🌟 BIG WIN! You won $1,000 on the gold ticket!")
            roll < 20 -> Pair(50L, "💵 Nice! You won a $50 cash payout!")
            roll < 35 -> Pair(15L, "🎟️ You won $15 back!")
            else -> Pair(0L, "You scratched off a dud ticket. Better luck next time!")
        }

        curr.bankBalance += prize
        if (prize > 0) {
            curr.happiness = (curr.happiness + 15).coerceAtMost(100)
        }

        val entry = LifeLogEntry(
            age = curr.age,
            title = "Lottery Ticket",
            description = narrative,
            emoji = if (prize > 0) "🎰" else "🎫",
            tag = LogTag.WEALTH,
            moneyDelta = prize - 10,
            happinessDelta = if (prize > 0) +15 else -2
        )
        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy()
        _feedbackMessage.value = if (prize > 0) "Won $$prize!" else "Dud ticket."
        saveStateToDb()
    }

    fun takeVacation(luxury: Boolean) {
        val curr = _character.value
        val cost = if (luxury) 3500L else 750L
        if (curr.bankBalance < cost) {
            _feedbackMessage.value = "You need $$cost for this trip."
            return
        }
        curr.bankBalance -= cost
        val boost = if (luxury) 35 else 20
        curr.happiness = (curr.happiness + boost).coerceAtMost(100)
        curr.health = (curr.health + 5).coerceAtMost(100)

        val destination = if (luxury) "The Maldives overwater villa" else "a charming seaside cabin"
        val entry = LifeLogEntry(
            age = curr.age,
            title = if (luxury) "Luxury Island Holiday" else "Refreshing Weekend Trip",
            description = "You flew out to $destination. Crystal blue waves and tropical sunset views completely rejuvenated your spirits.",
            emoji = "✈️",
            tag = LogTag.GENERAL,
            happinessDelta = boost,
            moneyDelta = -cost
        )
        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy()
        _feedbackMessage.value = "Vacation was heavenly! +Happiness!"
        saveStateToDb()
    }

    // CAREER & JOBS
    fun openJobInterview(job: JobListing) {
        val curr = _character.value
        if (curr.age < job.minAge) {
            _feedbackMessage.value = "You must be at least ${job.minAge} years old for this position."
            return
        }
        if (curr.smarts < job.minSmarts) {
            _feedbackMessage.value = "Your smarts (${curr.smarts}%) are below the minimum threshold (${job.minSmarts}%)."
            return
        }
        if (job.requiredDegree != Degree.NONE && curr.degree != job.requiredDegree) {
            _feedbackMessage.value = "Requires a ${job.requiredDegree.displayName}."
            return
        }
        _activeJobInterview.value = job
    }

    fun answerJobInterview(job: JobListing, choice: JobInterviewChoice) {
        val curr = _character.value
        _activeJobInterview.value = null

        if (choice.isAccepted) {
            curr.occupation = Occupation(
                title = job.title,
                workplaceOrSchool = job.company,
                annualSalary = job.salary,
                performance = 65,
                isJob = true
            )
            curr.happiness = (curr.happiness + 15).coerceAtMost(100)

            val entry = LifeLogEntry(
                age = curr.age,
                title = "Hired as ${job.title}!",
                description = "${choice.feedback} Annual salary: $${job.salary}.",
                emoji = job.emoji,
                tag = LogTag.CAREER,
                happinessDelta = +15
            )
            _lifeLogs.value = listOf(entry) + _lifeLogs.value
            _character.value = curr.copy()
            _feedbackMessage.value = "Congratulations! Hired as ${job.title}!"
        } else {
            curr.happiness = (curr.happiness - 5).coerceAtLeast(10)
            val entry = LifeLogEntry(
                age = curr.age,
                title = "Interview with ${job.company}",
                description = "${choice.feedback} The hiring committee went in another direction.",
                emoji = "❌",
                tag = LogTag.CAREER,
                happinessDelta = -5
            )
            _lifeLogs.value = listOf(entry) + _lifeLogs.value
            _character.value = curr.copy()
            _feedbackMessage.value = "Interview rejected."
        }
        saveStateToDb()
    }

    fun dismissInterview() {
        _activeJobInterview.value = null
    }

    fun workHarder() {
        val curr = _character.value
        if (!curr.occupation.isJob && curr.age < 5) {
            _feedbackMessage.value = "Nothing to work harder at right now!"
            return
        }
        curr.occupation = curr.occupation.copy(
            performance = (curr.occupation.performance + Random.nextInt(8, 16)).coerceAtMost(100)
        )
        curr.happiness = (curr.happiness - 3).coerceAtLeast(5)
        _feedbackMessage.value = "You put in overtime. Performance is now ${curr.occupation.performance}%!"
        _character.value = curr.copy()
        saveStateToDb()
    }

    fun askForRaise() {
        val curr = _character.value
        if (!curr.occupation.isJob) {
            _feedbackMessage.value = "You are not currently employed in a job."
            return
        }
        if (curr.occupation.performance >= 70) {
            val raise = (curr.occupation.annualSalary * 0.12).toLong()
            val newSalary = curr.occupation.annualSalary + raise
            curr.occupation = curr.occupation.copy(annualSalary = newSalary, performance = 55)
            curr.happiness = (curr.happiness + 15).coerceAtMost(100)

            val entry = LifeLogEntry(
                age = curr.age,
                title = "Salary Raise Approved!",
                description = "Your boss commended your dedication and gave you a 12% pay bump to $$newSalary/year!",
                emoji = "📈",
                tag = LogTag.CAREER,
                happinessDelta = +15
            )
            _lifeLogs.value = listOf(entry) + _lifeLogs.value
            _feedbackMessage.value = "Raise approved! New salary: $$newSalary"
        } else {
            curr.happiness = (curr.happiness - 5).coerceAtLeast(10)
            _feedbackMessage.value = "Boss denied the raise. 'Work harder first!' (Performance: ${curr.occupation.performance}%)"
        }
        _character.value = curr.copy()
        saveStateToDb()
    }

    fun resignJob() {
        val curr = _character.value
        if (!curr.occupation.isJob) return
        val formerTitle = curr.occupation.title
        curr.occupation = Occupation("Unemployed", "Self", 0, 0, false)
        val entry = LifeLogEntry(
            age = curr.age,
            title = "Resigned from $formerTitle",
            description = "You turned in your badge and keys. You are now officially free of the daily grind.",
            emoji = "🚪",
            tag = LogTag.CAREER
        )
        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy()
        _feedbackMessage.value = "You resigned from your job."
        saveStateToDb()
    }

    fun applySideJob(job: JobListing) {
        val curr = _character.value
        if (curr.age < job.minAge) {
            _feedbackMessage.value = "You must be at least ${job.minAge} for this side job."
            return
        }
        if (curr.sideJobs.any { it.id == job.id }) {
            _feedbackMessage.value = "You are already working this side job!"
            return
        }
        if (curr.sideJobs.size >= 2) {
            _feedbackMessage.value = "Maximum 2 concurrent side jobs allowed."
            return
        }
        curr.sideJobs.add(job)
        curr.happiness = (curr.happiness + 5).coerceAtMost(100)
        val entry = LifeLogEntry(
            age = curr.age,
            title = "Hired for Side Job: ${job.title}",
            description = "You took on a secondary position at ${job.company}! Earn an extra ${curr.currencySymbol}${job.salary}/year.",
            emoji = job.emoji,
            tag = LogTag.CAREER,
            happinessDelta = +5
        )
        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Hired as ${job.title}! (+${curr.currencySymbol}${job.salary}/yr)"
        saveStateToDb()
    }

    fun resignSideJob(jobId: String) {
        val curr = _character.value
        val removed = curr.sideJobs.firstOrNull { it.id == jobId }
        if (removed != null) {
            curr.sideJobs.remove(removed)
            val entry = LifeLogEntry(
                age = curr.age,
                title = "Left Side Job: ${removed.title}",
                description = "You stepped down from your secondary shifts at ${removed.company}.",
                emoji = "👋",
                tag = LogTag.CAREER
            )
            _lifeLogs.value = listOf(entry) + _lifeLogs.value
            _character.value = curr.copy(id = UUID.randomUUID().toString())
            _feedbackMessage.value = "Resigned from ${removed.title}."
            saveStateToDb()
        }
    }

    // ASSETS
    fun buyAsset(asset: Asset) {
        val curr = _character.value
        if (curr.bankBalance < asset.value) {
            _feedbackMessage.value = "You need $${asset.value} to purchase this."
            return
        }
        curr.bankBalance -= asset.value
        curr.assets.add(asset)
        curr.happiness = (curr.happiness + 15).coerceAtMost(100)

        val tx = BankTransaction(
            title = "Purchased ${asset.name}",
            amount = asset.value,
            isIncoming = false,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val entry = LifeLogEntry(
            age = curr.age,
            title = "Purchased ${asset.name}",
            description = "You closed the deal and acquired ${asset.name} for $${asset.value}!",
            emoji = asset.type.icon,
            tag = LogTag.WEALTH,
            happinessDelta = +15,
            moneyDelta = -asset.value
        )
        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = if (asset.name.contains("SimPhone", ignoreCase = true)) {
            "Purchased ${asset.name}! Open it anytime from Assets or the header 📱"
        } else {
            "Purchased ${asset.name}!"
        }
        saveStateToDb()
    }

    fun sellAsset(asset: Asset) {
        val curr = _character.value
        val salePrice = (asset.value * 0.85).toLong()
        curr.bankBalance += salePrice
        curr.assets.remove(asset)

        val tx = BankTransaction(
            title = "Sold ${asset.name}",
            amount = salePrice,
            isIncoming = true,
            dateOrYear = "Year ${curr.currentYear}"
        )
        curr.bankTransactions.add(0, tx)

        val entry = LifeLogEntry(
            age = curr.age,
            title = "Sold ${asset.name}",
            description = "You liquidated ${asset.name} on the secondary market for $$salePrice.",
            emoji = "💵",
            tag = LogTag.WEALTH,
            moneyDelta = salePrice
        )
        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy(id = UUID.randomUUID().toString())
        _feedbackMessage.value = "Sold ${asset.name} for $$salePrice."
        saveStateToDb()
    }

    private fun applyStatDeltas(char: Character, happy: Int, health: Int, smarts: Int, looks: Int, money: Long) {
        char.happiness = (char.happiness + happy).coerceIn(0, 100)
        char.health = (char.health + health).coerceIn(0, 100)
        char.smarts = (char.smarts + smarts).coerceIn(0, 100)
        char.looks = (char.looks + looks).coerceIn(0, 100)
        char.bankBalance = (char.bankBalance + money).coerceAtLeast(0L)
    }

    private fun saveStateToDb() {
        val curr = _character.value
        viewModelScope.launch {
            val entity = SavedLifeEntity(
                id = 1,
                firstName = curr.firstName,
                lastName = curr.lastName,
                gender = curr.gender.name,
                country = curr.country,
                city = curr.city,
                zodiac = curr.zodiac,
                birthYear = curr.birthYear,
                currencySymbol = curr.currencySymbol,
                currencyCode = curr.currencyCode,
                age = curr.age,
                happiness = curr.happiness,
                health = curr.health,
                smarts = curr.smarts,
                looks = curr.looks,
                bankBalance = curr.bankBalance,
                occupationTitle = curr.occupation.title,
                workplace = curr.occupation.workplaceOrSchool,
                salary = curr.occupation.annualSalary,
                performance = curr.occupation.performance,
                isJob = curr.occupation.isJob,
                education = curr.education.name,
                degree = curr.degree.name,
                isAlive = curr.isAlive,
                causeOfDeath = curr.causeOfDeath,
                relationshipsJson = serializeRelationships(curr.relationships),
                assetsJson = serializeAssets(curr.assets),
                logsJson = serializeLogs(_lifeLogs.value),
                phoneNotified = curr.phoneNotified
            )
            repository.saveCurrentLife(entity)
        }
    }

    private fun restoreSavedLife(saved: SavedLifeEntity) {
        val gender = try { Gender.valueOf(saved.gender) } catch (e: Exception) { Gender.MALE }
        val edu = try { EducationLevel.valueOf(saved.education) } catch (e: Exception) { EducationLevel.NONE }
        val degree = try { Degree.valueOf(saved.degree) } catch (e: Exception) { Degree.NONE }

        val restoredChar = Character(
            firstName = saved.firstName,
            lastName = saved.lastName,
            gender = gender,
            country = saved.country,
            city = saved.city,
            zodiac = saved.zodiac,
            birthYear = saved.birthYear,
            currencySymbol = saved.currencySymbol,
            currencyCode = saved.currencyCode,
            age = saved.age,
            happiness = saved.happiness,
            health = saved.health,
            smarts = saved.smarts,
            looks = saved.looks,
            bankBalance = saved.bankBalance,
            occupation = Occupation(saved.occupationTitle, saved.workplace, saved.salary, saved.performance, saved.isJob),
            education = edu,
            degree = degree,
            isAlive = saved.isAlive,
            causeOfDeath = saved.causeOfDeath,
            phoneNotified = saved.phoneNotified
        )
        restoredChar.relationships.addAll(deserializeRelationships(saved.relationshipsJson))
        restoredChar.assets.addAll(deserializeAssets(saved.assetsJson))
        ensureDefaultSocialAndMessages(restoredChar)

        _character.value = restoredChar
        _lifeLogs.value = deserializeLogs(saved.logsJson)
        _isGameOver.value = !saved.isAlive
    }

    private fun serializeRelationships(list: List<Relationship>): String {
        return list.joinToString(";") { "${it.id}|${it.name}|${it.role.name}|${it.meter}|${it.status}" }
    }

    private fun deserializeRelationships(raw: String): List<Relationship> {
        if (raw.isBlank()) return emptyList()
        return raw.split(";").mapNotNull { part ->
            val p = part.split("|")
            if (p.size >= 5) {
                val role = try { RelationshipRole.valueOf(p[2]) } catch (e: Exception) { RelationshipRole.MOTHER }
                Relationship(id = p[0], name = p[1], role = role, meter = p[3].toIntOrNull() ?: 80, status = p[4])
            } else null
        }
    }

    private fun serializeAssets(list: List<Asset>): String {
        return list.joinToString(";") { "${it.id}|${it.type.name}|${it.name}|${it.value}|${it.annualMaintenance}" }
    }

    private fun deserializeAssets(raw: String): List<Asset> {
        if (raw.isBlank()) return emptyList()
        return raw.split(";").mapNotNull { part ->
            val p = part.split("|")
            if (p.size >= 5) {
                val type = try { AssetType.valueOf(p[1]) } catch (e: Exception) { AssetType.CAR }
                Asset(id = p[0], type = type, name = p[2], value = p[3].toLongOrNull() ?: 0L, annualMaintenance = p[4].toLongOrNull() ?: 0L)
            } else null
        }
    }

    private fun serializeLogs(list: List<LifeLogEntry>): String {
        return list.take(80).joinToString(";;") {
            "${it.id}::${it.age}::${it.title}::${it.description}::${it.emoji}::${it.tag.name}::${it.happinessDelta}::${it.healthDelta}::${it.smartsDelta}::${it.looksDelta}::${it.moneyDelta}"
        }
    }

    private fun deserializeLogs(raw: String): List<LifeLogEntry> {
        if (raw.isBlank()) return emptyList()
        return raw.split(";;").mapNotNull { part ->
            val p = part.split("::")
            if (p.size >= 11) {
                val tag = try { LogTag.valueOf(p[5]) } catch (e: Exception) { LogTag.GENERAL }
                LifeLogEntry(
                    id = p[0],
                    age = p[1].toIntOrNull() ?: 0,
                    title = p[2],
                    description = p[3],
                    emoji = p[4],
                    tag = tag,
                    happinessDelta = p[6].toIntOrNull() ?: 0,
                    healthDelta = p[7].toIntOrNull() ?: 0,
                    smartsDelta = p[8].toIntOrNull() ?: 0,
                    looksDelta = p[9].toIntOrNull() ?: 0,
                    moneyDelta = p[10].toLongOrNull() ?: 0L
                )
            } else null
        }
    }
}

class LifeViewModelFactory(private val repository: LifeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LifeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LifeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
