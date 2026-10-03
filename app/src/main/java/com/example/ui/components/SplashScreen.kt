package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeEmeraldDark
import com.example.ui.theme.LifeGold
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        val steps = 20
        for (i in 1..steps) {
            delay(50)
            progress = i / steps.toFloat()
        }
        delay(200)
        onSplashFinished()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF1E293B),
                        Color(0xFF0F172A)
                    )
                )
            )
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .scale(pulseScale)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(LifeEmerald, LifeEmeraldDark, Color(0xFF047857))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "⏳", fontSize = 48.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "LifeSim",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Live Any Life • Write Your Legacy",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .width(200.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = LifeEmerald,
                trackColor = Color.White.copy(alpha = 0.15f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Simulating Life Engine v2.0...",
                style = MaterialTheme.typography.labelSmall,
                color = LifeGold,
                fontSize = 11.sp
            )
        }
    }
}
