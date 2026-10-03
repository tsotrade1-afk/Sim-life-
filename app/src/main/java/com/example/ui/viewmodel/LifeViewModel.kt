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
        _character.value = curr.copy()
        _feedbackMessage.value = "Received ${curr.currencySymbol}$amount from family!"
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

        // Subtract asset maintenance
        val assetExpenses = curr.assets.sumOf { it.annualMaintenance }
        cashDelta -= assetExpenses
        curr.bankBalance = (curr.bankBalance + cashDelta).coerceAtLeast(0L)

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
        _character.value = curr.copy()
        _feedbackMessage.value = "Purchased ${asset.name}!"
        saveStateToDb()
    }

    fun sellAsset(asset: Asset) {
        val curr = _character.value
        val salePrice = (asset.value * 0.85).toLong()
        curr.bankBalance += salePrice
        curr.assets.remove(asset)

        val entry = LifeLogEntry(
            age = curr.age,
            title = "Sold ${asset.name}",
            description = "You liquidated ${asset.name} on the secondary market for $$salePrice.",
            emoji = "💵",
            tag = LogTag.WEALTH,
            moneyDelta = salePrice
        )
        _lifeLogs.value = listOf(entry) + _lifeLogs.value
        _character.value = curr.copy()
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
