package com.example.data.model

import java.util.UUID

data class SocialComment(
    val id: String = UUID.randomUUID().toString(),
    val authorName: String,
    val authorHandle: String,
    val authorAvatar: String,
    val text: String,
    val isFamilyOrParent: Boolean = false
)

data class SocialPost(
    val id: String = UUID.randomUUID().toString(),
    val authorName: String,
    val authorHandle: String,
    val authorAvatar: String,
    val content: String,
    val isVideo: Boolean = false,
    val videoViews: Long = 0L,
    var likes: Long = 0L,
    var retweets: Long = 0L,
    val comments: MutableList<SocialComment> = mutableListOf(),
    val timestamp: String = "Just now",
    val tags: List<String> = emptyList(),
    val taggedPerson: String? = null,
    val isPlayerPost: Boolean = true
)

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val senderName: String,
    val text: String,
    val isFromPlayer: Boolean,
    val timestamp: String
)

data class ChatThread(
    val id: String = UUID.randomUUID().toString(),
    val recipientName: String,
    val recipientRole: String,
    val recipientAvatar: String,
    val messages: MutableList<ChatMessage> = mutableListOf(),
    var isTyping: Boolean = false
)
