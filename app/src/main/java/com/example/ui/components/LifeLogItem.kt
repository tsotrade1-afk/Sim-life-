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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LifeLogEntry
import com.example.ui.theme.*

@Composable
fun LifeLogItem(
    entry: LifeLogEntry,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("log_item_${entry.id}"),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Age and Category Tag
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(entry.tag.colorHex).copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "Age ${entry.age}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(entry.tag.colorHex),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "• ${entry.tag.label}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Event icon
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = entry.emoji, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Narrative Description
            Text(
                text = entry.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            // Stat deltas chips row if any
            val hasDeltas = entry.happinessDelta != 0 || entry.healthDelta != 0 ||
                    entry.smartsDelta != 0 || entry.looksDelta != 0 || entry.moneyDelta != 0L

            if (hasDeltas) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (entry.happinessDelta != 0) {
                        DeltaChip(label = "${if (entry.happinessDelta > 0) "+" else ""}${entry.happinessDelta}% Happy", isPositive = entry.happinessDelta > 0)
                    }
                    if (entry.healthDelta != 0) {
                        DeltaChip(label = "${if (entry.healthDelta > 0) "+" else ""}${entry.healthDelta}% Health", isPositive = entry.healthDelta > 0)
                    }
                    if (entry.smartsDelta != 0) {
                        DeltaChip(label = "${if (entry.smartsDelta > 0) "+" else ""}${entry.smartsDelta}% Smarts", isPositive = entry.smartsDelta > 0)
                    }
                    if (entry.looksDelta != 0) {
                        DeltaChip(label = "${if (entry.looksDelta > 0) "+" else ""}${entry.looksDelta}% Looks", isPositive = entry.looksDelta > 0)
                    }
                    if (entry.moneyDelta != 0L) {
                        DeltaChip(
                            label = "${if (entry.moneyDelta > 0) "+$" else "-$"}${kotlin.math.abs(entry.moneyDelta)}",
                            isPositive = entry.moneyDelta > 0
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeltaChip(label: String, isPositive: Boolean) {
    val bgColor = if (isPositive) LifeEmerald.copy(alpha = 0.12f) else LifeRose.copy(alpha = 0.12f)
    val textColor = if (isPositive) LifeEmerald else LifeRose

    Surface(
        shape = RoundedCornerShape(6.dp),
        color = bgColor
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontSize = 10.sp
        )
    }
}
