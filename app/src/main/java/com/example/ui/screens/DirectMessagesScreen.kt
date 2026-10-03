package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
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
import com.example.data.model.ChatMessage
import com.example.data.model.ChatThread
import com.example.data.model.Character
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold

@Composable
fun DirectMessagesScreen(
    character: Character,
    onSendMessage: (threadId: String, text: String) -> Unit,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var activeThreadId by remember { mutableStateOf<String?>(null) }

    val activeThread = character.chatThreads.firstOrNull { it.id == activeThreadId }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("direct_messages_screen")
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (activeThread != null) {
                    IconButton(onClick = { activeThreadId = null }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Inbox")
                    }
                    Text(text = activeThread.recipientAvatar, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = activeThread.recipientName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(
                            text = if (activeThread.isTyping) "typing..." else activeThread.recipientRole,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (activeThread.isTyping) LifeEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    if (onBack != null) {
                        TextButton(onClick = onBack, modifier = Modifier.height(32.dp)) {
                            Text("← Back")
                        }
                    }
                    Text(text = "💬 SimChat Messages & Inbox", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (activeThread == null) {
            // Inbox List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                item {
                    Text(
                        text = "Direct Messages (${character.chatThreads.size} Conversations)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                items(character.chatThreads) { thread ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        onClick = { activeThreadId = thread.id }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = thread.recipientAvatar, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = thread.recipientName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(
                                        text = thread.messages.lastOrNull()?.timestamp ?: "Now",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = if (thread.isTyping) "typing..." else thread.messages.lastOrNull()?.text ?: "Start a conversation",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (thread.isTyping) LifeEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Conversation Screen
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(activeThread.messages) { msg ->
                        ChatBubble(message = msg)
                    }

                    if (activeThread.isTyping) {
                        item {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(
                                    text = "✍️ ${activeThread.recipientName} is typing a reply...",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                // Preset Message Selection Bar
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Send a Quick Message (Instant 1-3s Reply):", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))

                        val presets = listOf(
                            "Hey! How are you doing today? 😊",
                            "Can we hang out together this weekend? 🍕",
                            "Could you lend me a quick $50 loan? 💵",
                            "I love and appreciate you so much! ❤️",
                            "Check out my new business & investments! 🚀",
                            "Things have been stressful lately... 🥺"
                        )

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(presets) { preset ->
                                OutlinedButton(
                                    onClick = { onSendMessage(activeThread.id, preset) },
                                    enabled = !activeThread.isTyping,
                                    modifier = Modifier.height(34.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp)
                                ) {
                                    Text(preset, fontSize = 11.sp, maxLines = 1)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (message.isFromPlayer) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 14.dp,
                topEnd = 14.dp,
                bottomStart = if (message.isFromPlayer) 14.dp else 2.dp,
                bottomEnd = if (message.isFromPlayer) 2.dp else 14.dp
            ),
            color = if (message.isFromPlayer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(
                    text = message.text,
                    color = if (message.isFromPlayer) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
                Text(
                    text = message.timestamp,
                    color = if (message.isFromPlayer) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 9.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
