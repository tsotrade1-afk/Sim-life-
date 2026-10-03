package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.Character
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TombstoneDialog(
    character: Character,
    onStartNewLife: () -> Unit,
    onViewCemetery: () -> Unit
) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }
    val netWorth = character.bankBalance + character.assets.sumOf { it.value }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("tombstone_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Tombstone / Memorial icon
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🪦", fontSize = 40.sp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Rest in Peace",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = character.fullName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Lived to Age ${character.age} in ${character.city}, ${character.country}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Certificate stats
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        DetailRow(label = "Final Career", value = character.occupation.title)
                        DetailRow(label = "Education", value = character.education.displayName)
                        DetailRow(label = "Total Net Worth", value = currencyFormatter.format(netWorth), isHighlight = true)
                        DetailRow(label = "Cause of Death", value = character.causeOfDeath ?: "Natural causes")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Epitaph quote
                Text(
                    text = "\"A life remembered for its unique adventures, choices, and memories.\"",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Reincarnate / Start new life button
                Button(
                    onClick = onStartNewLife,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("reincarnate_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                ) {
                    Text(
                        text = "✨ REINCARNATE / START NEW LIFE",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onViewCemetery,
                    modifier = Modifier.testTag("view_cemetery_button")
                ) {
                    Text(text = "View Cemetery Hall of Fame")
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, isHighlight: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = if (isHighlight) LifeGold else MaterialTheme.colorScheme.onSurface
        )
    }
}
