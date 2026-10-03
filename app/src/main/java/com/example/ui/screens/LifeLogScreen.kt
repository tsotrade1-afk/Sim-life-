package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LifeLogEntry
import com.example.ui.components.LifeLogItem
import com.example.ui.theme.LifeEmerald
import kotlinx.coroutines.launch

@Composable
fun LifeLogScreen(
    lifeLogs: List<LifeLogEntry>,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // Direction state: false = Latest on Top (Newest first), true = Reverse / Chronological (Oldest first)
    var isReversed by remember { mutableStateOf(false) }

    val displayLogs = remember(lifeLogs, isReversed) {
        if (isReversed) lifeLogs.reversed() else lifeLogs
    }

    // Automatically scroll to the latest event whenever new logs are added
    LaunchedEffect(lifeLogs.size, isReversed) {
        if (displayLogs.isNotEmpty()) {
            val targetIndex = if (isReversed) displayLogs.size - 1 else 0
            listState.animateScrollToItem(targetIndex)
        }
    }

    if (lifeLogs.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "🌱", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "A blank canvas awaits!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Press 'Age Up (+1 Year)' below to write your first chapter.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Top Direction & Order Toggle Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Life Feed (${lifeLogs.size} events)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    FilterChip(
                        selected = isReversed,
                        onClick = { isReversed = !isReversed },
                        label = {
                            Text(
                                text = if (isReversed) "⬆️ Oldest First" else "⬇️ Newest First",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.SwapVert,
                                contentDescription = "Reverse Order",
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        modifier = Modifier.height(30.dp)
                    )
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .testTag("life_log_list"),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(top = 4.dp, bottom = 100.dp)
                ) {
                    items(displayLogs, key = { it.id }) { entry ->
                        LifeLogItem(entry = entry)
                    }
                }
            }

            // Quick Floating "Scroll to Latest" pill button if user has scrolled away
            val isScrolledAway = if (isReversed) {
                listState.firstVisibleItemIndex < (displayLogs.size - 4).coerceAtLeast(0)
            } else {
                listState.firstVisibleItemIndex > 2
            }

            AnimatedVisibility(
                visible = isScrolledAway,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 90.dp)
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val targetIndex = if (isReversed) displayLogs.size - 1 else 0
                            listState.animateScrollToItem(targetIndex)
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = if (isReversed) "Jump to Latest (End) ⬇️" else "Jump to Latest (Top) ⬆️",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
