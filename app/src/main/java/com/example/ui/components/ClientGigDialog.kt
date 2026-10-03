package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.ClientGig
import com.example.data.model.ClientGigApproach
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose
import kotlin.random.Random

@Composable
fun ClientGigDialog(
    gig: ClientGig,
    onGigCompleted: (payout: Long, happinessDelta: Int, healthDelta: Int, narrative: String) -> Unit,
    onDismiss: () -> Unit
) {
    var step by remember { mutableStateOf(1) }
    var selectedApproach by remember { mutableStateOf<ClientGigApproach?>(null) }
    var gigResultSuccess by remember { mutableStateOf(true) }
    var finalPayout by remember { mutableStateOf(gig.basePayout) }
    var finalNarrative by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("client_gig_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    slideInHorizontally { width -> width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> -width } + fadeOut()
                },
                label = "gig_step"
            ) { currentStep ->
                if (currentStep == 1) {
                    // STEP 1: REVIEW CLIENT GIG & CHOOSE APPROACH
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = LifeGold.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "Base Pay: $${gig.basePayout}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = LifeGold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = gig.emoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = gig.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Client: ${gig.clientName} (${gig.clientType})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = gig.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Step 1 of 2: Select Delivery Approach",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            gig.approaches.forEachIndexed { index, approach ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedApproach = approach
                                            val isSuccess = Random.nextFloat() <= approach.successChance
                                            gigResultSuccess = isSuccess
                                            finalPayout = if (isSuccess) (gig.basePayout * approach.successRewardMultiplier).toLong() else (gig.basePayout * 0.7f).toLong()
                                            finalNarrative = if (isSuccess) approach.successNarrative else approach.failNarrative
                                            step = 2
                                        }
                                        .testTag("gig_approach_$index"),
                                    shape = RoundedCornerShape(14.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    border = ButtonDefaults.outlinedButtonBorder()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = approach.title,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = approach.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Success Chance: ${(approach.successChance * 100).toInt()}% • Multiplier: ${approach.successRewardMultiplier}x",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = LifeEmerald,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // STEP 2: CLIENT REVIEW & PAYOUT
                    val approach = selectedApproach
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (gigResultSuccess) LifeEmerald.copy(alpha = 0.15f) else LifeRose.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = if (gigResultSuccess) "Contract Completed! 🎉" else "Contract Finalized with Notes ⚠️",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (gigResultSuccess) LifeEmerald else LifeRose,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = if (gigResultSuccess) "💰" else "🤝", fontSize = 32.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Payout: +$$finalPayout",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = LifeGold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = finalNarrative,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                onGigCompleted(
                                    finalPayout,
                                    approach?.happinessDelta ?: 5,
                                    approach?.healthDelta ?: 0,
                                    finalNarrative
                                )
                                onDismiss()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("gig_finish_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                        ) {
                            Text("Collect Payment & Finish", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
