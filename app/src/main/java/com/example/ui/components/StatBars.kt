package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun StatBars(
    happiness: Int,
    health: Int,
    smarts: Int,
    looks: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("stat_bars_container"),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatItem(
                label = "Happy",
                emoji = "😊",
                value = happiness,
                barColor = StatHappinessColor,
                modifier = Modifier.weight(1f),
                tag = "stat_happiness"
            )
            StatItem(
                label = "Health",
                emoji = "❤️",
                value = health,
                barColor = StatHealthColor,
                modifier = Modifier.weight(1f),
                tag = "stat_health"
            )
            StatItem(
                label = "Smarts",
                emoji = "🧠",
                value = smarts,
                barColor = StatSmartsColor,
                modifier = Modifier.weight(1f),
                tag = "stat_smarts"
            )
            StatItem(
                label = "Looks",
                emoji = "✨",
                value = looks,
                barColor = StatLooksColor,
                modifier = Modifier.weight(1f),
                tag = "stat_looks"
            )
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    emoji: String,
    value: Int,
    barColor: Color,
    modifier: Modifier = Modifier,
    tag: String
) {
    val animatedProgress by animateFloatAsState(
        targetValue = (value.coerceIn(0, 100)) / 100f,
        animationSpec = tween(durationMillis = 400),
        label = "stat_anim"
    )

    Column(
        modifier = modifier.testTag(tag),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = emoji, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "$value%",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
                .background(barColor.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .clip(CircleShape)
                    .background(barColor)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp
        )
    }
}
