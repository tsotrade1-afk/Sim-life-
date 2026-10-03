package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.generators.LifeEventGenerator
import com.example.data.model.Asset
import com.example.data.model.AssetType
import com.example.data.model.Character
import com.example.data.model.FoodItem
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose

@Composable
fun ActivitiesScreen(
    character: Character,
    onGymClick: () -> Unit,
    onMeditateClick: () -> Unit,
    onReadBookClick: () -> Unit,
    onDoctorClick: () -> Unit,
    onLotteryClick: () -> Unit,
    onVacationClick: (luxury: Boolean) -> Unit,
    onBuyAsset: (Asset) -> Unit,
    onBuyFood: (FoodItem) -> Unit,
    onStartLemonade: () -> Unit,
    onBuyLemonadeSupplies: () -> Unit,
    onSetLemonadePrice: (Double) -> Unit,
    onUpgradeLemonadeStand: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAssetShop by remember { mutableStateOf(false) }
    var showFoodShop by remember { mutableStateOf(false) }
    var showLemonadeDialog by remember { mutableStateOf(false) }

    if (showAssetShop) {
        AssetShopSheet(
            character = character,
            onBuyAsset = onBuyAsset,
            onBack = { showAssetShop = false }
        )
    } else if (showFoodShop) {
        FoodMarketSheet(
            character = character,
            onBuyFood = onBuyFood,
            onBack = { showFoodShop = false }
        )
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .testTag("activities_list"),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
        ) {
            // Food & Sustenance Section
            item {
                SectionHeader("🥗 Nutrition & Sustenance")
            }

            item {
                ActivityCard(
                    title = "Grocery Market & Dining",
                    subtitle = "Refill hunger meter (${character.hunger}%). Eat every year to avoid starvation!",
                    cost = "From $12",
                    emoji = "🥗",
                    benefits = "+Hunger, +Health, +Happiness",
                    onClick = { showFoodShop = true },
                    tag = "activity_food_market"
                )
            }

            // Entrepreneurship Section
            item {
                SectionHeader("🍋 Business & Entrepreneurship")
            }

            item {
                if (!character.hasLemonadeStand) {
                    ActivityCard(
                        title = "Start a Lemonade Stand",
                        subtitle = "Build your very first park concession stand ($75). Earn yearly profits!",
                        cost = "$75",
                        emoji = "🍋",
                        benefits = "Annual revenue based on pricing & sales",
                        onClick = onStartLemonade,
                        tag = "activity_start_lemonade"
                    )
                } else {
                    ActivityCard(
                        title = "Manage Lemonade Stand 🍋",
                        subtitle = "${character.lemonadeSupplies} cups remaining • $${character.lemonadePrice}/cup • Total Earned: $${character.lemonadeStandRevenue}",
                        cost = "Manage",
                        emoji = "🍋",
                        benefits = "Restock lemons & adjust cup pricing",
                        onClick = { showLemonadeDialog = true },
                        tag = "activity_manage_lemonade"
                    )
                }
            }

            item {
                SectionHeader("Health & Wellness")
            }

            item {
                ActivityCard(
                    title = "Fitness Club / Gym",
                    subtitle = "Select specialized workout program (2-Step). (Age 12+)",
                    cost = if (character.age >= 18) "From $20" else "Free",
                    emoji = "💪",
                    benefits = "+Health, +Looks, +Happy",
                    onClick = onGymClick,
                    tag = "activity_gym"
                )
            }

            item {
                ActivityCard(
                    title = "Doctor's Appointment",
                    subtitle = "Select treatment protocol & therapies (2-Step).",
                    cost = if (character.age >= 18) "From $60" else "Free",
                    emoji = "🩺",
                    benefits = "++Health, Cures minor issues",
                    onClick = onDoctorClick,
                    tag = "activity_doctor"
                )
            }

            item {
                ActivityCard(
                    title = "Mindfulness & Meditation",
                    subtitle = "Breathwork and inner stillness.",
                    cost = "Free",
                    emoji = "🧘",
                    benefits = "+Happiness, +Health",
                    onClick = onMeditateClick,
                    tag = "activity_meditate"
                )
            }

            item {
                SectionHeader("Intellect & Mind")
            }

            item {
                ActivityCard(
                    title = "Read a Novel",
                    subtitle = "Read classics, non-fiction, or scientific literature.",
                    cost = "Free",
                    emoji = "📖",
                    benefits = "+Smarts, +Happiness",
                    onClick = onReadBookClick,
                    tag = "activity_read_book"
                )
            }

            item {
                SectionHeader("Fun & Fortune")
            }

            item {
                ActivityCard(
                    title = "Scratch-Off Lottery Ticket",
                    subtitle = "Try your luck on the jackpot wheel! (Age 18+)",
                    cost = "$10",
                    emoji = "🎰",
                    benefits = "Chance to win up to $50,000!",
                    onClick = onLotteryClick,
                    tag = "activity_lottery"
                )
            }

            item {
                ActivityCard(
                    title = "Tropical Vacation",
                    subtitle = "Fly out to a rejuvenating island beach paradise.",
                    cost = "$1,200",
                    emoji = "🏖️",
                    benefits = "+++Happiness boost",
                    onClick = { onVacationClick(false) },
                    tag = "activity_vacation"
                )
            }

            item {
                SectionHeader("Shopping & Investments")
            }

            item {
                ActivityCard(
                    title = "Asset & Electronics Dealership",
                    subtitle = "Buy SimPhones, Laptops, Supercars, Real Estate, and Pets.",
                    cost = "Browse",
                    emoji = "🏬",
                    benefits = "Acquire assets & build net worth",
                    onClick = { showAssetShop = true },
                    tag = "activity_asset_shop"
                )
            }
        }
    }

    // Modal to Manage Lemonade Stand
    if (showLemonadeDialog) {
        Dialog(onDismissRequest = { showLemonadeDialog = false }) {
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
                        Text(
                            text = "🍋 Lemonade Stand HQ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black
                        )
                        IconButton(onClick = { showLemonadeDialog = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Stand Supplies: ${character.lemonadeSupplies} cups left", fontWeight = FontWeight.Bold)
                            Text(text = "Current Cup Price: ${character.currencySymbol}${character.lemonadePrice}")
                            Text(text = "Stand Level: ${character.lemonadeStandUpgradeLevel} (Canopy & Signage)", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Text(text = "Total Lifetime Profit: ${character.currencySymbol}${character.lemonadeStandRevenue}", color = LifeGold, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    val upgradeCost = 60L * character.lemonadeStandUpgradeLevel
                    OutlinedButton(
                        onClick = onUpgradeLemonadeStand,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        enabled = character.bankBalance >= upgradeCost
                    ) {
                        Text("Upgrade Canopy & Signs Lv ${character.lemonadeStandUpgradeLevel + 1} ($$upgradeCost)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "Restock Citrus Supplies:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = {
                            onBuyLemonadeSupplies()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        enabled = character.bankBalance >= 20L
                    ) {
                        Text("Buy Lemons & Sugar ($20 for 30 cups)", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(text = "Adjust Cup Price:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(1.00, 1.50, 2.00, 2.50).forEach { price ->
                            FilterChip(
                                selected = character.lemonadePrice == price,
                                onClick = { onSetLemonadePrice(price) },
                                label = { Text("${character.currencySymbol}$price", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showLemonadeDialog = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                    ) {
                        Text("Done Managing", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// FOOD & DINING MARKET SHEET
@Composable
private fun FoodMarketSheet(
    character: Character,
    onBuyFood: (FoodItem) -> Unit,
    onBack: () -> Unit
) {
    val foods = LifeEventGenerator.FOOD_ITEMS

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("food_market_list"),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("← Back to Activities")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Hunger: ${character.hunger}% 🍽️",
                    fontWeight = FontWeight.Bold,
                    color = if (character.hunger <= 25) LifeRose else LifeEmerald
                )
            }
        }

        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            ) {
                Text(
                    text = "🥗 Eat meals or buy groceries each year to keep your hunger filled. Severe starvation reduces health and happiness!",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        item { SectionHeader("Groceries & Restaurant Dining") }

        items(foods) { food ->
            val canAfford = character.bankBalance >= food.cost
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = food.emoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = food.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                        Text(text = food.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            text = "+${food.hungerRestore}% Hunger • Cost: ${character.currencySymbol}${food.cost}",
                            style = MaterialTheme.typography.labelSmall,
                            color = LifeEmerald,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { onBuyFood(food) },
                        enabled = canAfford,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                    ) {
                        Text("Eat 🍽️", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
    )
}

@Composable
private fun ActivityCard(
    title: String,
    subtitle: String,
    cost: String,
    emoji: String,
    benefits: String,
    onClick: () -> Unit,
    tag: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(tag),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = cost,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (cost == "Free") LifeEmerald else LifeGold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Effects: $benefits",
                    style = MaterialTheme.typography.labelSmall,
                    color = LifeEmerald,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun AssetShopSheet(
    character: Character,
    onBuyAsset: (Asset) -> Unit,
    onBack: () -> Unit
) {
    val phones = LifeEventGenerator.PHONES_FOR_SALE
    val tech = LifeEventGenerator.TECH_FOR_SALE.filter { it.type == AssetType.TECH }
    val cars = LifeEventGenerator.CARS_FOR_SALE
    val houses = LifeEventGenerator.HOUSES_FOR_SALE
    val pets = LifeEventGenerator.PETS_FOR_ADOPTION

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("asset_shop_list"),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 100.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text("← Back to Activities")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Bank: ${character.currencySymbol}${character.bankBalance}",
                    fontWeight = FontWeight.Bold,
                    color = LifeGold
                )
            }
        }

        // 1. SimPhone & Mobile Devices - ALWAYS AVAILABLE
        item { SectionHeader("📱 SimPhone & Mobile Devices") }
        items(phones) { phone ->
            val alreadyOwns = character.assets.any { it.name == phone.name }
            AssetBuyCard(
                asset = phone,
                canAfford = (character.bankBalance >= phone.value) && !alreadyOwns
            ) {
                onBuyAsset(phone)
            }
        }

        // 2. Computers & Wearable Tech
        item { SectionHeader("💻 Computers & Wearable Electronics") }
        items(tech) { techItem ->
            val alreadyOwns = character.assets.any { it.name == techItem.name }
            AssetBuyCard(
                asset = techItem,
                canAfford = (character.bankBalance >= techItem.value) && !alreadyOwns
            ) {
                onBuyAsset(techItem)
            }
        }

        // 3. Vehicles & Luxury Supercars
        item { SectionHeader("🏎️ Vehicles & Luxury Supercars") }
        items(cars) { car ->
            val alreadyOwns = character.assets.any { it.name == car.name }
            AssetBuyCard(asset = car, canAfford = (character.bankBalance >= car.value) && !alreadyOwns) {
                onBuyAsset(car)
            }
        }

        // 4. Real Estate
        item { SectionHeader("🏠 Real Estate & Houses") }
        items(houses) { house ->
            val alreadyOwns = character.assets.any { it.name == house.name }
            AssetBuyCard(asset = house, canAfford = (character.bankBalance >= house.value) && !alreadyOwns) {
                onBuyAsset(house)
            }
        }

        // 5. Pets
        item { SectionHeader("🐶 Pets for Adoption") }
        items(pets) { pet ->
            val alreadyOwns = character.assets.any { it.name == pet.name }
            AssetBuyCard(asset = pet, canAfford = (character.bankBalance >= pet.value) && !alreadyOwns) {
                onBuyAsset(pet)
            }
        }
    }
}

@Composable
private fun AssetBuyCard(asset: Asset, canAfford: Boolean, onBuy: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(text = asset.type.icon, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Price: $${asset.value} • Maint: $${asset.annualMaintenance}/yr",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = onBuy,
                enabled = canAfford,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
            ) {
                Text(text = "Buy", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
