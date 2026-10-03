package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.db.LifeDatabase
import com.example.data.repository.LifeRepository
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.LifeSimTab
import com.example.ui.viewmodel.LifeViewModel
import com.example.ui.viewmodel.LifeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val context = LocalContext.current
                val database = remember { LifeDatabase.getDatabase(context) }
                val repository = remember { LifeRepository(database) }
                val factory = remember { LifeViewModelFactory(repository) }
                val viewModel: LifeViewModel = viewModel(factory = factory)

                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    SplashScreen(onSplashFinished = { showSplash = false })
                } else {
                    LifeSimApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun LifeSimApp(viewModel: LifeViewModel) {
    val character by viewModel.character.collectAsStateWithLifecycle()
    val lifeLogs by viewModel.lifeLogs.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val activeDilemma by viewModel.activeDilemma.collectAsStateWithLifecycle()
    val activeJobInterview by viewModel.activeJobInterview.collectAsStateWithLifecycle()
    val activeTwoStepAction by viewModel.activeTwoStepAction.collectAsStateWithLifecycle()
    val activeClientGig by viewModel.activeClientGig.collectAsStateWithLifecycle()
    val showPhoneDialog by viewModel.showPhoneDialog.collectAsStateWithLifecycle()
    val showSettingsHub by viewModel.showSettingsHub.collectAsStateWithLifecycle()
    val feedbackMessage by viewModel.feedbackMessage.collectAsStateWithLifecycle()
    val isGameOver by viewModel.isGameOver.collectAsStateWithLifecycle()
    val pastLives by viewModel.pastLives.collectAsStateWithLifecycle()

    var showNewLifeDialog by remember { mutableStateOf(false) }
    var showGraveyard by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(feedbackMessage) {
        feedbackMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearFeedback()
        }
    }

    if (showGraveyard) {
        GraveyardScreen(
            pastLives = pastLives,
            onBack = { showGraveyard = false }
        )
        return
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                // The signature Age Up Button placed ONLY on the Life Feed tab!
                if (currentTab == LifeSimTab.LOG) {
                    AgeUpButton(
                        currentAge = character.age,
                        isAlive = character.isAlive,
                        onAgeUp = { viewModel.ageUp() }
                    )
                }

                // Bottom Navigation Bar with 5 Life Simulator Tabs
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("bottom_nav_bar"),
                    tonalElevation = 4.dp
                ) {
                    LifeSimTab.values().forEach { tab ->
                        NavigationBarItem(
                            selected = currentTab == tab,
                            onClick = { viewModel.setTab(tab) },
                            icon = { Text(text = tab.icon, fontSize = 18.sp) },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontSize = 9.sp,
                                    fontWeight = if (currentTab == tab) FontWeight.Bold else FontWeight.Normal,
                                    maxLines = 1
                                )
                            },
                            modifier = Modifier.testTag("nav_tab_${tab.name}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Top Persistent Character Info Card with Settings & Phone
            CharacterHeader(
                character = character,
                onNewLifeClick = { showNewLifeDialog = true },
                onGraveyardClick = { showGraveyard = true },
                onSettingsClick = { viewModel.openSettingsHub() },
                onPhoneClick = { viewModel.openPhone() },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )

            // Animated 4 Core Stat Bars (Happiness, Health, Smarts, Looks)
            StatBars(
                happiness = character.happiness,
                health = character.health,
                smarts = character.smarts,
                looks = character.looks,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Tab Content
            Box(modifier = Modifier.weight(1f)) {
                when (currentTab) {
                    LifeSimTab.LOG -> {
                        LifeLogScreen(lifeLogs = lifeLogs)
                    }
                    LifeSimTab.ACTIVITIES -> {
                        ActivitiesScreen(
                            character = character,
                            onGymClick = { viewModel.triggerGymTwoStep() },
                            onMeditateClick = { viewModel.doMeditation() },
                            onReadBookClick = { viewModel.triggerReadingTwoStep() },
                            onDoctorClick = { viewModel.triggerDoctorTwoStep() },
                            onLotteryClick = { viewModel.playLottery() },
                            onVacationClick = { luxury -> viewModel.takeVacation(luxury) },
                            onBuyAsset = { asset -> viewModel.buyAsset(asset) }
                        )
                    }
                    LifeSimTab.CAREER -> {
                        CareerScreen(
                            character = character,
                            onWorkHarder = { viewModel.workHarder() },
                            onAskForRaise = { viewModel.askForRaise() },
                            onResign = { viewModel.resignJob() },
                            onApplyJob = { job -> viewModel.openJobInterview(job) },
                            onSelectGig = { gig -> viewModel.openClientGig(gig) }
                        )
                    }
                    LifeSimTab.RELATIONSHIPS -> {
                        RelationshipsScreen(
                            character = character,
                            onInteract = { rel -> viewModel.triggerRelationshipTwoStep(rel) }
                        )
                    }
                    LifeSimTab.ASSETS -> {
                        AssetsScreen(
                            character = character,
                            onSellAsset = { asset -> viewModel.sellAsset(asset) },
                            onOpenShop = { viewModel.setTab(LifeSimTab.ACTIVITIES) },
                            onOpenPhone = { viewModel.openPhone() }
                        )
                    }
                }
            }
        }
    }

    // Modal Phone Device Dialog
    if (showPhoneDialog) {
        PhoneDialog(
            character = character,
            onSendMoney = { recipient, amount ->
                viewModel.sendBankMoney(recipient, amount)
            },
            onReceiveMoney = { amount, source ->
                viewModel.receiveBankMoney(amount, source)
            },
            onBuyPhoneShortcut = {
                viewModel.closePhone()
                viewModel.setTab(LifeSimTab.ACTIVITIES)
            },
            onDismiss = { viewModel.closePhone() }
        )
    }

    // Settings & Updates Hub Dialog (Update Log + Future Roadmap)
    if (showSettingsHub) {
        SettingsHubDialog(
            onDismiss = { viewModel.closeSettingsHub() }
        )
    }

    // Modal Dilemma Event Dialog
    activeDilemma?.let { dilemma ->
        EventDialog(
            event = dilemma,
            onChoiceSelected = { choice -> viewModel.selectDilemmaChoice(choice) },
            onDismiss = { viewModel.dismissDilemma() }
        )
    }

    // Modal 2-Step Interactive Action Dialog (Gym, Doctor, Reading, Social)
    activeTwoStepAction?.let { actionData ->
        TwoStepActionDialog(
            actionData = actionData,
            onConfirmOption = { option -> viewModel.confirmTwoStepAction(option) },
            onDismiss = { viewModel.dismissTwoStepAction() }
        )
    }

    // Modal 2-Step Client Gig Dialog
    activeClientGig?.let { gig ->
        ClientGigDialog(
            gig = gig,
            onGigCompleted = { payout, happy, health, narr ->
                viewModel.completeClientGig(payout, happy, health, narr)
            },
            onDismiss = { viewModel.dismissClientGig() }
        )
    }

    // Modal Job Interview Dialog
    activeJobInterview?.let { job ->
        JobInterviewDialog(
            job = job,
            onChoiceSelected = { choice -> viewModel.answerJobInterview(job, choice) },
            onDismiss = { viewModel.dismissInterview() }
        )
    }

    // Tombstone Dialog when deceased
    if (isGameOver && !character.isAlive) {
        TombstoneDialog(
            character = character,
            onStartNewLife = { viewModel.startBrandNewLife() },
            onViewCemetery = { showGraveyard = true }
        )
    }

    // New Life Customization Dialog
    if (showNewLifeDialog) {
        NewLifeDialog(
            onConfirm = { name, gender, country, birthYear ->
                viewModel.startBrandNewLife(name, gender, country, birthYear)
                showNewLifeDialog = false
            },
            onDismiss = { showNewLifeDialog = false }
        )
    }
}
