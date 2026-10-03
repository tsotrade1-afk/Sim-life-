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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.TwoStepActionData
import com.example.data.model.TwoStepChoiceOption
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose

@Composable
fun TwoStepActionDialog(
    actionData: TwoStepActionData,
    onConfirmOption: (TwoStepChoiceOption) -> Unit,
    onDismiss: () -> Unit
) {
    var step by remember { mutableStateOf(1) }
    var selectedOption by remember { mutableStateOf<TwoStepChoiceOption?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("two_step_action_dialog"),
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
                label = "step_anim"
            ) { currentStep ->
                if (currentStep == 1) {
                    // STEP 1: CHOOSE INTENSITY / APPROACH / PROTOCOL
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
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = actionData.category,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = actionData.emoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = actionData.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Step 1 of 2: Choose approach",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = actionData.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            actionData.options.forEachIndexed { index, option ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedOption = option
                                            step = 2
                                        }
                                        .testTag("step1_option_$index"),
                                    shape = RoundedCornerShape(14.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    border = ButtonDefaults.outlinedButtonBorder()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = option.emoji, fontSize = 22.sp)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = option.title,
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = option.subtitle,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            if (option.cost > 0L) {
                                                Text(
                                                    text = "Cost: $${option.cost}",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = LifeGold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // STEP 2: CONSEQUENCE / OUTCOME
                    val opt = selectedOption
                    if (opt != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = LifeEmerald.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "Step 2 of 2: Result",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = LifeEmerald,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = opt.emoji, fontSize = 32.sp)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = opt.resultTitle,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = opt.resultNarrative,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Deltas preview chips
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (opt.happinessDelta != 0) {
                                    ResultDeltaChip("${if (opt.happinessDelta > 0) "+" else ""}${opt.happinessDelta}% Happy", opt.happinessDelta > 0)
                                }
                                if (opt.healthDelta != 0) {
                                    ResultDeltaChip("${if (opt.healthDelta > 0) "+" else ""}${opt.healthDelta}% Health", opt.healthDelta > 0)
                                }
                                if (opt.smartsDelta != 0) {
                                    ResultDeltaChip("${if (opt.smartsDelta > 0) "+" else ""}${opt.smartsDelta}% Smarts", opt.smartsDelta > 0)
                                }
                                if (opt.looksDelta != 0) {
                                    ResultDeltaChip("${if (opt.looksDelta > 0) "+" else ""}${opt.looksDelta}% Looks", opt.looksDelta > 0)
                                }
                                if (opt.moneyDelta != 0L) {
                                    ResultDeltaChip("${if (opt.moneyDelta > 0) "+$" else "-$"}${kotlin.math.abs(opt.moneyDelta)}", opt.moneyDelta > 0)
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    onConfirmOption(opt)
                                    onDismiss()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("step2_confirm_button"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                            ) {
                                Text("Awesome!", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultDeltaChip(label: String, isPositive: Boolean) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (isPositive) LifeEmerald.copy(alpha = 0.15f) else LifeRose.copy(alpha = 0.15f)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = if (isPositive) LifeEmerald else LifeRose
        )
    }
}
