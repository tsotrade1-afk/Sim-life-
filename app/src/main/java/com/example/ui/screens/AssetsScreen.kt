package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Asset
import com.example.data.model.AssetType
import com.example.data.model.Character
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AssetsScreen(
    character: Character,
    onSellAsset: (Asset) -> Unit,
    onOpenShop: () -> Unit,
    onOpenPhone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currencyFormatter = NumberFormat.getNumberInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }
    val symbol = character.currencySymbol

    val totalAssetValue = character.assets.sumOf { it.value }
    val netWorth = character.bankBalance + totalAssetValue
    val annualMaint = character.assets.sumOf { it.annualMaintenance }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
            .testTag("assets_list"),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 4.dp, bottom = 90.dp)
    ) {
        // SMARTPHONE CARD
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("phone_asset_card"),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
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
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "📱", fontSize = 24.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (character.hasPhone) character.phoneModelName else "SimPhone Not Owned",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (character.hasPhone) LifeEmerald.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = if (character.hasSimPhonePro) "PRO" else if (character.hasPhone) "OWNED" else "LOCKED",
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (character.hasPhone) LifeEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 9.sp
                                )
                            }
                        }
                        Text(
                            text = if (character.hasPhone) "News • SimBank Wire • Richest 100" else if (character.currentYear >= 2001) "Buy in Tech Store (${character.currencySymbol}499)" else "Invented in Year 2001",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (character.hasPhone) {
                        Button(
                            onClick = onOpenPhone,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.testTag("open_phone_button")
                        ) {
                            Text("Open 📱", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        OutlinedButton(
                            onClick = onOpenShop,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("buy_phone_store_button")
                        ) {
                            Text("Buy Tech 🛒", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Net worth summary card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Text(
                        text = "Total Net Worth",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "$symbol${currencyFormatter.format(netWorth)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = LifeGold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Liquid Cash", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "$symbol${currencyFormatter.format(character.bankBalance)}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column {
                            Text(text = "Physical Assets", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "$symbol${currencyFormatter.format(totalAssetValue)}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column {
                            Text(text = "Annual Expenses", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "$symbol${currencyFormatter.format(annualMaint)}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (annualMaint > 0) LifeRose else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Possessions (${character.assets.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = onOpenShop) {
                    Text("+ Buy Assets")
                }
            }
        }

        if (character.assets.isEmpty()) {
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "📦", fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "You don't own any physical assets yet.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Earn money from jobs or gigs, then acquire cars, houses, pets, or phones!",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        } else {
            items(character.assets, key = { it.id }) { asset ->
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
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = asset.name,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Value: $symbol${currencyFormatter.format(asset.value)} • Upkeep: $symbol${currencyFormatter.format(asset.annualMaintenance)}/yr",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }

                        if (asset.type == AssetType.PHONE) {
                            Button(
                                onClick = onOpenPhone,
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                            ) {
                                Text("Apps", fontSize = 11.sp)
                            }
                        } else {
                            OutlinedButton(
                                onClick = { onSellAsset(asset) },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Sell", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
