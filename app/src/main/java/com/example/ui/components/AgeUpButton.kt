package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeEmeraldDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AgeUpButton(
    currentAge: Int,
    isAlive: Boolean,
    onAgeUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "btn_scale"
    )

    val buttonBrush = if (isAlive) {
        Brush.horizontalGradient(
            colors = listOf(
                LifeEmeraldDark,
                LifeEmerald,
                Color(0xFF34D399)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(Color(0xFF475569), Color(0xFF64748B))
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 4.dp)
            .scale(scale)
            .shadow(
                elevation = if (isAlive) 6.dp else 2.dp,
                shape = RoundedCornerShape(18.dp),
                spotColor = if (isAlive) LifeEmerald else Color.Gray
            )
            .clip(RoundedCornerShape(18.dp))
            .background(buttonBrush)
            .clickable(
                enabled = isAlive,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            ) {
                coroutineScope.launch {
                    isPressed = true
                    delay(120)
                    isPressed = false
                }
                onAgeUp()
            }
            .testTag("age_up_button")
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (isAlive) Icons.Default.HourglassTop else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isAlive) "AGE UP (+1 YEAR)" else "LIFE CONCLUDED",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = 0.5.sp,
                    fontSize = 15.sp
                )
                if (isAlive) {
                    Text(
                        text = "Advancing to Age ${currentAge + 1}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.85f),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}
