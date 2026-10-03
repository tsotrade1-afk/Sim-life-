package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.generators.LifeEventGenerator
import com.example.data.model.Character
import com.example.data.model.ClientGig
import com.example.data.model.Degree
import com.example.data.model.JobListing
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose

@Composable
fun CareerScreen(
    character: Character,
    onWorkHarder: () -> Unit,
    onAskForRaise: () -> Unit,
    onResign: () -> Unit,
    onApplyJob: (JobListing) -> Unit,
    onSelectGig: (ClientGig) -> Unit,
    modifier: Modifier = Modifier
) {
    val jobs = LifeEventGenerator.AVAILABLE_JOBS
    val clientGigs = LifeEventGenerator.CLIENT_GIGS

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
            .testTag("career_screen_list"),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 4.dp, bottom = 90.dp)
    ) {
        // Current Status Card
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("current_job_card"),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Text(
                        text = "Current Status",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = character.occupation.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = character.occupation.workplaceOrSchool,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (character.occupation.isJob) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = LifeGold.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "$${character.occupation.annualSalary}/yr",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = LifeGold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Performance Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (character.occupation.isJob) "Job Performance" else "Academic Record",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = "${character.occupation.performance}%",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = LifeEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    LinearProgressIndicator(
                        progress = { (character.occupation.performance.coerceIn(0, 100)) / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = LifeEmerald,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    // Action Buttons
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onWorkHarder,
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .testTag("work_harder_button"),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Work Harder", fontSize = 11.sp)
                        }

                        if (character.occupation.isJob) {
                            OutlinedButton(
                                onClick = onAskForRaise,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .testTag("ask_raise_button"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Ask for Raise", fontSize = 11.sp)
                            }

                            IconButton(
                                onClick = onResign,
                                modifier = Modifier
                                    .size(38.dp)
                                    .testTag("resign_button")
                            ) {
                                Text("🚪", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }

        // Section: Client & Freelance Gigs
        item {
            Column {
                Text(
                    text = "🤝 Client & Freelance Contracts",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Deliver custom services for clients to earn fast money & build experience.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(clientGigs, key = { it.id }) { gig ->
            val meetsAge = character.age >= gig.minAge
            val meetsSmarts = character.smarts >= gig.minSmarts
            val eligible = meetsAge && meetsSmarts

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("client_gig_${gig.id}"),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = gig.emoji, fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = gig.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Client: ${gig.clientName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Base Fee: $${gig.basePayout}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = LifeGold
                        )
                    }

                    Button(
                        onClick = { onSelectGig(gig) },
                        enabled = eligible,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald),
                        modifier = Modifier.testTag("take_gig_${gig.id}")
                    ) {
                        Text(
                            text = if (eligible) "Accept" else "Age ${gig.minAge}+",
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Section: Job Board Openings
        item {
            Text(
                text = "💼 Job Board / Permanent Careers",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        items(jobs, key = { it.id }) { job ->
            val meetsAge = character.age >= job.minAge
            val meetsSmarts = character.smarts >= job.minSmarts
            val meetsDegree = job.requiredDegree == Degree.NONE || character.degree == job.requiredDegree
            val canApply = meetsAge && meetsSmarts && meetsDegree

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("job_item_${job.id}"),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = job.emoji, fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = job.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${job.company} • $${job.salary}/yr",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (job.requiredDegree != Degree.NONE) {
                            Text(
                                text = "Req: ${job.requiredDegree.displayName}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (meetsDegree) LifeEmerald else LifeRose
                            )
                        }
                    }

                    Button(
                        onClick = { onApplyJob(job) },
                        enabled = canApply,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald),
                        modifier = Modifier.testTag("apply_job_${job.id}")
                    ) {
                        Text(
                            text = if (canApply) "Apply" else "Locked",
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
