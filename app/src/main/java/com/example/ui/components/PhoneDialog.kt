package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.generators.LifeEventGenerator
import com.example.data.generators.NewsAndRichDatabase
import com.example.data.model.Character
import com.example.data.model.NewsArticle
import com.example.data.model.Relationship
import com.example.data.model.UberTrip
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID

enum class SimPhoneApp {
    HOME,
    NEWS,
    BANK,
    UBER,
    RICHEST,
    SETTINGS
}

@Composable
fun PhoneDialog(
    character: Character,
    onSendMoney: (recipient: String, amount: Long) -> Unit,
    onReceiveMoney: (amount: Long, source: String) -> Unit,
    onPayDebt: (amount: Long) -> Unit,
    onTakeLoan: (amount: Long) -> Unit,
    onCompleteUberRide: (trip: UberTrip) -> Unit,
    onBuyPhoneShortcut: () -> Unit,
    onDismiss: () -> Unit
) {
    var activeApp by remember { mutableStateOf(SimPhoneApp.HOME) }
    var selectedArticle by remember { mutableStateOf<NewsArticle?>(null) }
    var selectedCategory by remember { mutableStateOf("All") }

    val currencyFormatter = remember(character.currencySymbol) {
        NumberFormat.getNumberInstance(Locale.US).apply {
            maximumFractionDigits = 0
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.93f)
                .testTag("phone_device_dialog"),
            shape = RoundedCornerShape(34.dp),
            color = Color(0xFF0F172A), // Titanium Bezel
            tonalElevation = 10.dp,
            border = BorderStroke(2.dp, Color(0xFF334155))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Phone Screen Inner Frame
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(26.dp)),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Status Bar: Time, Notch, Battery
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Year ${character.currentYear} • 9:41",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )

                            // Phone Speaker Camera Notch
                            Box(
                                modifier = Modifier
                                    .width(55.dp)
                                    .height(6.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray.copy(alpha = 0.5f))
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = if (character.hasSimPhonePro) "PRO 5G" else "4G",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(text = "100% 🔋", fontSize = 10.sp)
                            }
                        }

                        // App Header / Navigation Bar inside phone
                        if (activeApp != SimPhoneApp.HOME) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(
                                    onClick = { activeApp = SimPhoneApp.HOME },
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Home", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                Text(
                                    text = when (activeApp) {
                                        SimPhoneApp.NEWS -> "Chronicle News"
                                        SimPhoneApp.BANK -> "SimBank"
                                        SimPhoneApp.UBER -> "SimRider (Uber)"
                                        SimPhoneApp.RICHEST -> "Richest 100"
                                        SimPhoneApp.SETTINGS -> "SimPhone Specs"
                                        else -> ""
                                    },
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Black
                                )

                                IconButton(
                                    onClick = onDismiss,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Phone")
                                }
                            }
                            HorizontalDivider(thickness = 0.5.dp)
                        } else {
                            // Close icon on top right of home screen
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Phone")
                                }
                            }
                        }

                        // App Content Area
                        Box(modifier = Modifier.weight(1f)) {
                            if (!character.hasPhone) {
                                NoPhoneOwnedView(
                                    character = character,
                                    onBuyPhoneShortcut = onBuyPhoneShortcut
                                )
                            } else {
                                when (activeApp) {
                                    SimPhoneApp.HOME -> {
                                        PhoneHomeAppGrid(
                                            character = character,
                                            onLaunchApp = { activeApp = it }
                                        )
                                    }
                                    SimPhoneApp.NEWS -> {
                                        PhoneNewsAppScreen(
                                            currentYear = character.currentYear,
                                            selectedCategory = selectedCategory,
                                            onSelectCategory = { selectedCategory = it },
                                            onSelectArticle = { selectedArticle = it }
                                        )
                                    }
                                    SimPhoneApp.BANK -> {
                                        PhoneBankAppScreen(
                                            character = character,
                                            currencyFormatter = currencyFormatter,
                                            onSendMoney = onSendMoney,
                                            onReceiveMoney = onReceiveMoney,
                                            onPayDebt = onPayDebt,
                                            onTakeLoan = onTakeLoan
                                        )
                                    }
                                    SimPhoneApp.UBER -> {
                                        if (character.hasSimPhonePro) {
                                            PhoneUberAppScreen(
                                                character = character,
                                                onCompleteUberRide = onCompleteUberRide
                                            )
                                        } else {
                                            ProFeatureLockedView(
                                                featureTitle = "SimRider Rideshare App",
                                                featureDescription = "The SimRider Uber driving app is exclusively available on SimPhone Pro ($999). Drive passengers to earn realistic fares ($25-$60)!",
                                                onUpgradeClick = onBuyPhoneShortcut
                                            )
                                        }
                                    }
                                    SimPhoneApp.RICHEST -> {
                                        if (character.hasSimPhonePro) {
                                            PhoneRichestAppScreen(
                                                character = character,
                                                currencyFormatter = currencyFormatter
                                            )
                                        } else {
                                            ProFeatureLockedView(
                                                featureTitle = "Richest 100 Leaderboard",
                                                featureDescription = "The Forbes 100 Richest People Leaderboard is exclusively available on SimPhone Pro ($999). Track your ranking among world billionaires!",
                                                onUpgradeClick = onBuyPhoneShortcut
                                            )
                                        }
                                    }
                                    SimPhoneApp.SETTINGS -> {
                                        PhoneSpecsAppScreen(character = character)
                                    }
                                }
                            }
                        }

                        // Bottom Home Bar Pill
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(72.dp)
                                    .height(4.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                                    .clickable { activeApp = SimPhoneApp.HOME }
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal when user taps a News Article
    selectedArticle?.let { article ->
        NewsArticleDetailDialog(
            article = article,
            onDismiss = { selectedArticle = null }
        )
    }
}

@Composable
private fun NoPhoneOwnedView(
    character: Character,
    onBuyPhoneShortcut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "📱", fontSize = 48.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "No SimPhone Detected",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Purchase a SimPhone ($499) or SimPhone Pro ($999) from the Tech Store in Activities/Assets!",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onBuyPhoneShortcut,
            colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Browse Tech Store 🛒", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun PhoneHomeAppGrid(
    character: Character,
    onLaunchApp: (SimPhoneApp) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Phone Home Header Widget
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${character.city}, ${character.country}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${character.currencySymbol}${character.bankBalance}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = LifeGold
                )
                if (character.debt > 0) {
                    Text(
                        text = "Debt: ${character.currencySymbol}${character.debt}",
                        style = MaterialTheme.typography.labelSmall,
                        color = LifeRose,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Model: ${character.phoneModelName}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "INSTALLED APPS",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Row 1: News & SimBank
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SimPhoneAppIcon(
                title = "News",
                emoji = "📰",
                badge = "Chronicle",
                bgGradient = listOf(Color(0xFF2563EB), Color(0xFF1D4ED8)),
                onClick = { onLaunchApp(SimPhoneApp.NEWS) }
            )

            SimPhoneAppIcon(
                title = "SimBank",
                emoji = "🏦",
                badge = if (character.debt > 0) "Debt Active" else "Wire Money",
                bgGradient = listOf(Color(0xFF059669), Color(0xFF047857)),
                onClick = { onLaunchApp(SimPhoneApp.BANK) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Row 2: SimRider (Uber) & Richest 100
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SimPhoneAppIcon(
                title = "SimRider",
                emoji = "🚗",
                badge = if (character.hasSimPhonePro) "${5 - character.rideshareTripsThisYear} rides left" else "Pro Req.",
                bgGradient = if (character.hasSimPhonePro) listOf(Color(0xFF1E293B), Color(0xFF0F172A)) else listOf(Color(0xFF475569), Color(0xFF334155)),
                onClick = { onLaunchApp(SimPhoneApp.UBER) }
            )

            SimPhoneAppIcon(
                title = "Richest 100",
                emoji = "🏆",
                badge = if (character.hasSimPhonePro) "Pro" else "Pro Req.",
                bgGradient = if (character.hasSimPhonePro) listOf(Color(0xFFD97706), Color(0xFFB45309)) else listOf(Color(0xFF475569), Color(0xFF334155)),
                onClick = { onLaunchApp(SimPhoneApp.RICHEST) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Row 3: Specs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SimPhoneAppIcon(
                title = "Device Info",
                emoji = "⚙️",
                badge = "SimOS",
                bgGradient = listOf(Color(0xFF7C3AED), Color(0xFF6D28D9)),
                onClick = { onLaunchApp(SimPhoneApp.SETTINGS) }
            )
        }
    }
}

@Composable
private fun SimPhoneAppIcon(
    title: String,
    emoji: String,
    badge: String,
    bgGradient: List<Color>,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(bgGradient)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 28.sp)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )

        Surface(
            shape = RoundedCornerShape(4.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Text(
                text = badge,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                style = MaterialTheme.typography.labelSmall,
                fontSize = 8.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// 🚗 SIMRIDER (UBER GIG APP ON SIMPHONE PRO)
@Composable
private fun PhoneUberAppScreen(
    character: Character,
    onCompleteUberRide: (UberTrip) -> Unit
) {
    var isSearching by remember { mutableStateOf(false) }
    var currentPassengerTrip by remember { mutableStateOf<UberTrip?>(null) }
    val tripsLeft = (5 - character.rideshareTripsThisYear).coerceAtLeast(0)

    LaunchedEffect(isSearching) {
        if (isSearching) {
            delay(1200) // Finding passenger radar
            currentPassengerTrip = LifeEventGenerator.UBER_TRIPS.random()
            isSearching = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Driver Profile Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF0F172A)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🚗", fontSize = 26.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "SimRider Driver Hub",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = if (character.hasCar) "Vehicle: Active Car" else "Vehicle: None (Car Required)",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (character.hasCar) LifeEmerald else LifeRose,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Driver Rating: 4.96 ⭐ • Verified",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (tripsLeft > 0) LifeEmerald.copy(alpha = 0.2f) else LifeRose.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "$tripsLeft/5 Left",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (tripsLeft > 0) LifeEmerald else LifeRose,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (!character.hasCar) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "🚫 Vehicle Required to Drive",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "SimRider requires you to own an automobile. Buy a car in the Asset Market to start picking up passengers!",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        } else if (tripsLeft == 0) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "🏁 Annual Driving Quota Reached",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "You have completed all 5 gig rides for Year ${character.currentYear}. Age up (+1 Year) to refresh your driving quota!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            // Find Ride Button or Current Trip Card
            item {
                if (currentPassengerTrip == null) {
                    Button(
                        onClick = { isSearching = true },
                        enabled = !isSearching,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = if (isSearching) "Searching for Nearby Passengers... 📡" else "Find Passenger Ride 🔍",
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    val trip = currentPassengerTrip!!
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 3.dp
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = trip.emoji, fontSize = 28.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = trip.passengerName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Destination: ${trip.destination}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Base Fare: ${character.currencySymbol}${trip.fare}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Tip: +${character.currencySymbol}${trip.tip}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = LifeEmerald,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Note: \"${trip.review}\" • ${trip.rating}⭐",
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            val total = trip.fare + trip.tip
                            Button(
                                onClick = {
                                    onCompleteUberRide(trip)
                                    currentPassengerTrip = null
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                            ) {
                                Text("Complete Ride & Collect ${character.currencySymbol}$total 💸", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// 📰 NEWS SCREEN
@Composable
private fun PhoneNewsAppScreen(
    currentYear: Int,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    onSelectArticle: (NewsArticle) -> Unit
) {
    val categories = listOf("All", "Tech", "Economy", "Culture", "Science", "Entertainment", "World")
    val unlockedNews = NewsAndRichDatabase.getUnlockedNews(currentYear)
    val filtered = if (selectedCategory == "All") unlockedNews else unlockedNews.filter { it.category.equals(selectedCategory, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📰 Year $currentYear Edition • ${unlockedNews.size} Unlocked Stories",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(categories) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { onSelectCategory(cat) },
                    label = { Text(cat, fontSize = 10.sp) },
                    modifier = Modifier.height(30.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(filtered, key = { it.id }) { article ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectArticle(article) },
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = article.emoji, fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${article.source} • ${article.year}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 10.sp
                                )
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = article.category,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 8.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = article.headline,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// 🏦 SIMBANK APP WITH DEBT PAYOFF & RECIPIENTS
@Composable
private fun PhoneBankAppScreen(
    character: Character,
    currencyFormatter: NumberFormat,
    onSendMoney: (recipient: String, amount: Long) -> Unit,
    onReceiveMoney: (amount: Long, source: String) -> Unit,
    onPayDebt: (amount: Long) -> Unit,
    onTakeLoan: (amount: Long) -> Unit
) {
    var transferAmountText by remember { mutableStateOf("100") }
    var selectedRecipient by remember { mutableStateOf(character.relationships.firstOrNull()?.name ?: "Mother") }
    var customRecipientText by remember { mutableStateOf("") }
    var isCustomRecipient by remember { mutableStateOf(false) }

    var transferState by remember { mutableStateOf("IDLE") }
    var lastSentAmount by remember { mutableStateOf(0L) }
    var lastSentRecipient by remember { mutableStateOf("") }
    var transactionId by remember { mutableStateOf("") }

    LaunchedEffect(transferState) {
        if (transferState == "PROCESSING") {
            delay(2000)
            transferState = "SUCCESS_POPUP"
        }
    }

    if (transferState == "SUCCESS_POPUP") {
        Dialog(onDismissRequest = {
            onSendMoney(lastSentRecipient, lastSentAmount)
            transferState = "IDLE"
        }) {
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(LifeEmerald.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "✅", fontSize = 32.sp)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Payment Sent Successfully!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = LifeEmerald
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "You wired ${character.currencySymbol}${currencyFormatter.format(lastSentAmount)} to $lastSentRecipient.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Ref ID: $transactionId",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            onSendMoney(lastSentRecipient, lastSentAmount)
                            transferState = "IDLE"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("fine_payment_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                    ) {
                        Text("Fine", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    if (transferState == "PROCESSING") {
        val infiniteTransition = rememberInfiniteTransition(label = "wire_spin")
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "spin"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.75f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Sync,
                        contentDescription = "Processing",
                        modifier = Modifier
                            .size(38.dp)
                            .rotate(rotation),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Processing Wire Transfer...",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Connecting with SimBank network 💸",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        // Balance Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "SimBank Checking Account",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${character.currencySymbol}${currencyFormatter.format(character.bankBalance)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = LifeGold
                    )
                    Text(
                        text = "Account Holder: ${character.fullName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Debt & Loan Card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = if (character.debt > 0) LifeRose.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "💳 Loan & Debt Center",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (character.debt > 0) "Debt: ${character.currencySymbol}${currencyFormatter.format(character.debt)}" else "No Outstanding Debt",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (character.debt > 0) LifeRose else LifeEmerald
                        )
                    }

                    if (character.debt > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Accrues 5% interest per year. Pay down your balance to protect your credit and avoid penalties!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { onPayDebt(100L) },
                                modifier = Modifier.weight(1f),
                                enabled = character.bankBalance >= 100L
                            ) {
                                Text("Pay ${character.currencySymbol}100", fontSize = 11.sp)
                            }
                            Button(
                                onClick = { onPayDebt(character.debt) },
                                modifier = Modifier.weight(1f),
                                enabled = character.bankBalance >= character.debt,
                                colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                            ) {
                                Text("Pay Full Debt", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Need emergency capital? Apply for an unsecured personal loan from SimBank.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { onTakeLoan(500L) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Borrow ${character.currencySymbol}500", fontSize = 11.sp)
                            }
                            OutlinedButton(
                                onClick = { onTakeLoan(2000L) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Borrow ${character.currencySymbol}2,000", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // Send Money Section
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "💸 Wire Money to Loved Ones",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Select Recipient (Family & Friends):",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(character.relationships) { rel ->
                            FilterChip(
                                selected = selectedRecipient == rel.name && !isCustomRecipient,
                                onClick = {
                                    selectedRecipient = rel.name
                                    isCustomRecipient = false
                                },
                                label = { Text("${rel.role.icon} ${rel.name}", fontSize = 11.sp) }
                            )
                        }
                        item {
                            FilterChip(
                                selected = isCustomRecipient,
                                onClick = { isCustomRecipient = true },
                                label = { Text("✍️ Custom", fontSize = 11.sp) }
                            )
                        }
                    }

                    if (isCustomRecipient) {
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = customRecipientText,
                            onValueChange = { customRecipientText = it },
                            label = { Text("Recipient Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = transferAmountText,
                        onValueChange = { transferAmountText = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Transfer Amount (${character.currencySymbol})") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val amountLong = transferAmountText.toLongOrNull() ?: 0L
                    val canSend = amountLong in 1..character.bankBalance
                    val finalRecipient = if (isCustomRecipient && customRecipientText.isNotBlank()) customRecipientText else selectedRecipient

                    Button(
                        onClick = {
                            lastSentAmount = amountLong
                            lastSentRecipient = finalRecipient
                            transactionId = "TXN-${UUID.randomUUID().toString().take(8).uppercase()}"
                            transferState = "PROCESSING"
                        },
                        enabled = canSend,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("send_money_transfer_button"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                    ) {
                        Text(
                            text = if (amountLong > character.bankBalance) "Insufficient Funds" else "Send ${character.currencySymbol}$amountLong",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 📜 Account Statement & Bank Activity Log
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📜 Account Statement Log",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${character.bankTransactions.size} records",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (character.bankTransactions.isEmpty()) {
                        Text(
                            text = "No recent transactions. Transfers, debt repayments, salary deposits, and Uber earnings will appear here.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            character.bankTransactions.take(20).forEach { tx ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = tx.title,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = tx.dateOrYear,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 10.sp
                                        )
                                    }
                                    Text(
                                        text = "${if (tx.isIncoming) "+" else "-"}${character.currencySymbol}${currencyFormatter.format(tx.amount)}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Black,
                                        color = if (tx.isIncoming) LifeEmerald else LifeRose
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 🏆 RICHEST 100 LEADERBOARD
@Composable
private fun PhoneRichestAppScreen(
    character: Character,
    currencyFormatter: NumberFormat
) {
    val richestList = remember(character.bankBalance, character.assets.size) {
        NewsAndRichDatabase.getRichestLadder(character)
    }
    val playerRank = richestList.firstOrNull { it.isPlayer }?.rank ?: 999999
    val playerNetWorth = character.bankBalance + character.assets.sumOf { it.value }

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = LifeGold.copy(alpha = 0.15f),
            tonalElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "WORLD WEALTH RANK",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = LifeGold
                    )
                    Text(
                        text = if (playerRank <= 100) "#$playerRank IN THE WORLD! 🏆" else "#$playerRank (Climbing)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Net Worth", style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = "${character.currencySymbol}${currencyFormatter.format(playerNetWorth)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = LifeEmerald
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(vertical = 6.dp)
        ) {
            items(richestList, key = { "${it.rank}_${it.name}" }) { entry ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = if (entry.isPlayer) LifeEmerald.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
                    border = if (entry.isPlayer) ButtonDefaults.outlinedButtonBorder() else null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (entry.rank <= 3) LifeGold else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "#${entry.rank}",
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (entry.rank <= 3) Color.Black else MaterialTheme.colorScheme.onSurface,
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = entry.emoji, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(6.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = entry.name,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (entry.isPlayer) FontWeight.Black else FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "${entry.country} • ${entry.industry}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 9.sp
                            )
                        }

                        Text(
                            text = "${character.currencySymbol}${currencyFormatter.format(entry.netWorth)}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (entry.isPlayer) LifeEmerald else LifeGold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProFeatureLockedView(
    featureTitle: String,
    featureDescription: String,
    onUpgradeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(LifeGold.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "👑", fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = featureTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = featureDescription,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = onUpgradeClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LifeGold)
        ) {
            Text("Upgrade to SimPhone Pro 🚀", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun PhoneSpecsAppScreen(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "📱", fontSize = 48.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = character.phoneModelName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Operating System: SimOS v2.2",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                SpecItem("Installed Model", character.phoneModelName)
                SpecItem("Current Calendar Year", "Year ${character.currentYear}")
                SpecItem("SimRider Uber Driving", if (character.hasSimPhonePro) "Supported (Pro)" else "Pro Version Required")
                SpecItem("Leaderboard Support", if (character.hasSimPhonePro) "Enabled (Pro)" else "Pro Version Required")
                SpecItem("Banking Protocol", "SimBank Secure Wireless")
            }
        }
    }
}

@Composable
private fun SpecItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun NewsArticleDetailDialog(
    article: NewsArticle,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "${article.category} • Year ${article.year}",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = article.emoji, fontSize = 32.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = article.headline,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Reported by ${article.source}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = article.snippet,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "💡 Societal Impact",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = article.impactOnWorld,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Close Story")
                }
            }
        }
    }
}
