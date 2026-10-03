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
import com.example.data.generators.LifeEventGenerator
import com.example.data.model.Asset
import com.example.data.model.Character
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold

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
    modifier: Modifier = Modifier
) {
    var showAssetShop by remember { mutableStateOf(false) }

    if (showAssetShop) {
        AssetShopSheet(
            character = character,
            onBuyAsset = onBuyAsset,
            onBack = { showAssetShop = false }
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
                    title = "Car, Real Estate & Pet Dealership",
                    subtitle = "Browse vehicles, luxury houses, and adorable pets.",
                    cost = "Browse",
                    emoji = "🏬",
                    benefits = "Acquire assets & build net worth",
                    onClick = { showAssetShop = true },
                    tag = "activity_asset_shop"
                )
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
        tonalElevation = 1.dp
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
                    text = "Bank: $${character.bankBalance}",
                    fontWeight = FontWeight.Bold,
                    color = LifeGold
                )
            }
        }

        item { SectionHeader("Vehicles & Cars") }
        items(cars) { car ->
            AssetBuyCard(asset = car, canAfford = character.bankBalance >= car.value) {
                onBuyAsset(car)
            }
        }

        item { SectionHeader("Real Estate & Houses") }
        items(houses) { house ->
            AssetBuyCard(asset = house, canAfford = character.bankBalance >= house.value) {
                onBuyAsset(house)
            }
        }

        item { SectionHeader("Pets for Adoption") }
        items(pets) { pet ->
            AssetBuyCard(asset = pet, canAfford = character.bankBalance >= pet.value) {
                onBuyAsset(pet)
            }
        }

        item { SectionHeader("💻 Tech & Electronics Store") }
        val techList = LifeEventGenerator.TECH_FOR_SALE
        items(techList) { techItem ->
            val isPhone = techItem.type == AssetType.PHONE
            val isLockedByYear = isPhone && character.currentYear < 2001
            val alreadyOwns = character.assets.any { it.name == techItem.name }
            val canAfford = character.bankBalance >= techItem.value

            if (isLockedByYear) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🔒", fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${techItem.name} (${character.currencySymbol}${techItem.value})",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Releases in Year 2001 (Current Year: ${character.currentYear})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                AssetBuyCard(
                    asset = techItem,
                    canAfford = canAfford && !alreadyOwns
                ) {
                    onBuyAsset(techItem)
                }
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
            Text(text = asset.type.icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(12.dp))
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
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
            ) {
                Text(text = "Buy", fontSize = 12.sp)
            }
        }
    }
}
