package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Repeat
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
import com.example.data.model.Character
import com.example.data.model.SocialPost
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose

@Composable
fun SocialMediaScreen(
    character: Character,
    onPublishPost: (content: String, isVideo: Boolean, tags: List<String>, taggedPerson: String?) -> Unit,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var postText by remember { mutableStateOf("") }
    var isVideoPost by remember { mutableStateOf(false) }
    var selectedTag by remember { mutableStateOf("#LifeUpdate") }
    var selectedTaggedPerson by remember { mutableStateOf<String?>(null) }

    val handle = "@${character.firstName.lowercase()}_${character.lastName.lowercase()}"

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("social_media_screen")
    ) {
        // App Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                if (onBack != null) {
                    TextButton(onClick = onBack, modifier = Modifier.height(32.dp)) {
                        Text("← Back")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🌐 SimSocial Feed", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        if (character.isSocialVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = Color(0xFF1DA1F2), modifier = Modifier.size(18.dp))
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Text(
                            text = "${character.socialFollowers} Followers",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            // Compose Tweet / Video Box
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = character.avatarEmoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = character.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = handle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = postText,
                            onValueChange = { postText = it },
                            placeholder = { Text("What's happening? Share a thought, flex, or update...") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            maxLines = 4
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Topic Tag Chips
                        Text(text = "Choose Topic Hashtag:", style = MaterialTheme.typography.labelSmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("#LifeUpdate", "#Grindset", "#Crypto", "#Tech", "#Flex", "#Foodie", "#Gaming").forEach { tag ->
                                item {
                                    FilterChip(
                                        selected = selectedTag == tag,
                                        onClick = { selectedTag = tag },
                                        label = { Text(tag, fontSize = 10.sp) },
                                        modifier = Modifier.height(28.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Tag a person
                        if (character.relationships.isNotEmpty()) {
                            Text(text = "Tag a Person:", style = MaterialTheme.typography.labelSmall)
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                item {
                                    FilterChip(
                                        selected = selectedTaggedPerson == null,
                                        onClick = { selectedTaggedPerson = null },
                                        label = { Text("No one", fontSize = 10.sp) },
                                        modifier = Modifier.height(28.dp)
                                    )
                                }
                                character.relationships.forEach { rel ->
                                    item {
                                        FilterChip(
                                            selected = selectedTaggedPerson == rel.name,
                                            onClick = { selectedTaggedPerson = rel.name },
                                            label = { Text("@${rel.name} (${rel.role.name})", fontSize = 10.sp) },
                                            modifier = Modifier.height(28.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // Post options row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Switch(
                                    checked = isVideoPost,
                                    onCheckedChange = { isVideoPost = it },
                                    modifier = Modifier.height(24.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = if (isVideoPost) "🎥 Video Post" else "📝 Text Tweet", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }

                            Button(
                                onClick = {
                                    if (postText.isNotBlank()) {
                                        onPublishPost(postText, isVideoPost, listOf(selectedTag), selectedTaggedPerson)
                                        postText = ""
                                    }
                                },
                                enabled = postText.isNotBlank()
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Post", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Timeline Header
            item {
                Text(text = "Latest SimSocial Posts & Comments:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            if (character.socialPosts.isEmpty()) {
                item {
                    Text(
                        text = "Your feed is quiet. Write your very first tweet or video update above to build your following!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(character.socialPosts) { post ->
                    SocialPostCard(post = post)
                }
            }
        }
    }
}

@Composable
private fun SocialPostCard(post: SocialPost) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Author info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = post.authorAvatar, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = post.authorName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = post.authorHandle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text(text = post.timestamp, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                if (post.isVideo) {
                    Surface(shape = RoundedCornerShape(6.dp), color = LifeRose.copy(alpha = 0.2f)) {
                        Text(
                            text = "▶ 4K Video (${post.videoViews} views)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = LifeRose,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Content
            Text(text = post.content, fontSize = 14.sp)

            if (post.tags.isNotEmpty() || post.taggedPerson != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    post.tags.forEach { tag ->
                        Text(text = tag, color = Color(0xFF1DA1F2), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    if (post.taggedPerson != null) {
                        Text(text = "@${post.taggedPerson}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Engagement stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = LifeRose, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${post.likes}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Repeat, contentDescription = null, tint = LifeEmerald, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${post.retweets}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Text(text = "💬 ${post.comments.size} Comments", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            // Comments section (Bots, parents, friends)
            if (post.comments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    post.comments.forEach { comment ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = if (comment.isFamilyOrParent) LifeGold.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(comment.authorAvatar, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = comment.authorName, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = comment.authorHandle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        if (comment.isFamilyOrParent) {
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Surface(shape = RoundedCornerShape(4.dp), color = LifeGold.copy(alpha = 0.3f)) {
                                                Text("Family ❤️", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = LifeGold, modifier = Modifier.padding(horizontal = 3.dp))
                                            }
                                        }
                                    }
                                    Text(text = comment.text, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
