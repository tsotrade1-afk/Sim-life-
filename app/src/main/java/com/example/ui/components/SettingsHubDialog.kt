package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Update
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
import androidx.compose.ui.window.Dialog
import com.example.data.generators.LifeEventGenerator
import com.example.data.generators.NewsAndRichDatabase
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold

enum class SettingsHubTab {
    UPDATE_LOG,
    ROADMAP
}

@Composable
fun SettingsHubDialog(
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(SettingsHubTab.UPDATE_LOG) }
    val logs = LifeEventGenerator.GAME_UPDATE_LOGS
    val roadmap = NewsAndRichDatabase.ROADMAP_MILESTONES

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .testTag("settings_hub_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "Settings & Updates",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "LifeSim v2.0 Engine",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Toggle Button Row between Update Log & Roadmap
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(4.dp)
                ) {
                    Button(
                        onClick = { selectedTab = SettingsHubTab.UPDATE_LOG },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTab == SettingsHubTab.UPDATE_LOG) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (selectedTab == SettingsHubTab.UPDATE_LOG) Color.White else MaterialTheme.colorScheme.onSurface
                        ),
                        elevation = if (selectedTab == SettingsHubTab.UPDATE_LOG) ButtonDefaults.buttonElevation(defaultElevation = 2.dp) else ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Update, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Update Log", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { selectedTab = SettingsHubTab.ROADMAP },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTab == SettingsHubTab.ROADMAP) MaterialTheme.colorScheme.primary else Color.Transparent,
                            contentColor = if (selectedTab == SettingsHubTab.ROADMAP) Color.White else MaterialTheme.colorScheme.onSurface
                        ),
                        elevation = if (selectedTab == SettingsHubTab.ROADMAP) ButtonDefaults.buttonElevation(defaultElevation = 2.dp) else ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Map, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Roadmap", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Content Area
                Box(modifier = Modifier.weight(1f)) {
                    AnimatedContent(
                        targetState = selectedTab,
                        label = "settings_tab_anim"
                    ) { tab ->
                        if (tab == SettingsHubTab.UPDATE_LOG) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(logs) { entry ->
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        color = if (entry.isBigUpdate) LifeEmerald.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        border = if (entry.isBigUpdate) ButtonDefaults.outlinedButtonBorder() else null
                                    ) {
                                        Column(modifier = Modifier.padding(14.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = entry.version,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = if (entry.isBigUpdate) LifeEmerald else MaterialTheme.colorScheme.primary
                                                )
                                                Text(
                                                    text = entry.releaseDate,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(4.dp))

                                            Text(
                                                text = entry.highlight,
                                                style = MaterialTheme.typography.bodySmall,
                                                fontWeight = FontWeight.SemiBold,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                entry.changes.forEach { change ->
                                                    Text(
                                                        text = "• $change",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        lineHeight = 18.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            // ROADMAP TAB
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                item {
                                    Text(
                                        text = "Official LifeSim Development Roadmap",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                items(roadmap) { milestone ->
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                    ) {
                                        Column(modifier = Modifier.padding(14.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(text = milestone.icon, fontSize = 20.sp)
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text(
                                                        text = milestone.phase,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        fontWeight = FontWeight.Black,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                }

                                                Surface(
                                                    shape = RoundedCornerShape(6.dp),
                                                    color = when (milestone.status) {
                                                        "Completed" -> LifeEmerald.copy(alpha = 0.2f)
                                                        "Next Up" -> LifeGold.copy(alpha = 0.2f)
                                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                                    }
                                                ) {
                                                    Text(
                                                        text = milestone.status,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                        style = MaterialTheme.typography.labelSmall,
                                                        fontWeight = FontWeight.Bold,
                                                        color = when (milestone.status) {
                                                            "Completed" -> LifeEmerald
                                                            "Next Up" -> LifeGold
                                                            else -> MaterialTheme.colorScheme.onSurface
                                                        }
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(4.dp))

                                            Text(
                                                text = milestone.title,
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold
                                            )

                                            Spacer(modifier = Modifier.height(2.dp))

                                            Text(
                                                text = milestone.description,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                milestone.highlights.forEach { hl ->
                                                    Text(
                                                        text = "✦ $hl",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        lineHeight = 18.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("close_settings_hub_button"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Close")
                }
            }
        }
    }
}
