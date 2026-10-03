package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.generators.InvestmentAndSocialDatabase
import com.example.data.model.Character
import com.example.data.model.Degree
import com.example.data.model.EducationLevel
import com.example.data.model.SchoolClub
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose

@Composable
fun SchoolScreen(
    character: Character,
    onStudyHarder: () -> Unit,
    onSlackOff: () -> Unit,
    onAskTeacherForHelp: () -> Unit,
    onHangOutClassmates: () -> Unit,
    onJoinClub: (SchoolClub) -> Unit,
    onApplyUniversity: (Degree) -> Unit,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var showClubsDialog by remember { mutableStateOf(false) }
    var showUniversityDialog by remember { mutableStateOf(false) }

    val schoolName = when {
        character.education == EducationLevel.UNIVERSITY -> "Metropolitan State University"
        character.age in 14..17 -> "Oakridge Comprehensive High School"
        character.age in 11..13 -> "Pine Valley Middle School"
        character.age in 5..10 -> "St. Jude Primary & Elementary School"
        else -> "Independent Studies"
    }

    val letterGrade = when {
        character.schoolGradePercent >= 93 -> "A"
        character.schoolGradePercent >= 90 -> "A-"
        character.schoolGradePercent >= 87 -> "B+"
        character.schoolGradePercent >= 83 -> "B"
        character.schoolGradePercent >= 80 -> "B-"
        character.schoolGradePercent >= 70 -> "C"
        character.schoolGradePercent >= 60 -> "D"
        else -> "F"
    }

    val gpa = when {
        character.schoolGradePercent >= 93 -> 4.0f
        character.schoolGradePercent >= 90 -> 3.7f
        character.schoolGradePercent >= 87 -> 3.3f
        character.schoolGradePercent >= 83 -> 3.0f
        character.schoolGradePercent >= 80 -> 2.7f
        character.schoolGradePercent >= 70 -> 2.0f
        character.schoolGradePercent >= 60 -> 1.0f
        else -> 0.0f
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("school_screen")
    ) {
        // School Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
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
                    Column {
                        Text(
                            text = "🎓 $schoolName",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Current Status: ${character.occupation.title} • Age ${character.age}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = when {
                            character.schoolGradePercent >= 80 -> LifeEmerald.copy(alpha = 0.2f)
                            character.schoolGradePercent >= 70 -> LifeGold.copy(alpha = 0.2f)
                            else -> LifeRose.copy(alpha = 0.2f)
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Grade: $letterGrade",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = when {
                                    character.schoolGradePercent >= 80 -> LifeEmerald
                                    character.schoolGradePercent >= 70 -> LifeGold
                                    else -> LifeRose
                                }
                            )
                            Text(text = "GPA: $gpa", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            // Report Card Bar
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Academic Standing: ${character.schoolGradePercent}%", fontWeight = FontWeight.Bold)
                            Text(
                                text = if (character.schoolGradePercent >= 90) "🏆 Dean's Honor Roll" else if (character.schoolGradePercent >= 75) "Good Standing" else "⚠️ Academic Probation",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (character.schoolGradePercent >= 75) LifeEmerald else LifeRose,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { character.schoolGradePercent / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = if (character.schoolGradePercent >= 75) LifeEmerald else LifeRose
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Teacher Relationship: ${character.teacherRelationship}%", style = MaterialTheme.typography.bodySmall)
                            Text(text = "School Detentions: ${character.schoolDetentions}", style = MaterialTheme.typography.bodySmall, color = if (character.schoolDetentions > 0) LifeRose else MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Student Activities
            item {
                Text(text = "School Actions & Studies:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = onStudyHarder,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("📖 Study Harder (+Grades)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            OutlinedButton(
                                onClick = onAskTeacherForHelp,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("🙋 Ask Teacher (+Help)", fontSize = 11.sp)
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = onHangOutClassmates,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("🎒 Socialize with Class", fontSize = 11.sp)
                            }
                            OutlinedButton(
                                onClick = onSlackOff,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("😴 Slack Off / Skip Class", fontSize = 11.sp, color = LifeRose)
                            }
                        }
                    }
                }
            }

            // Extracurricular Clubs
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Extracurricular Clubs & Sports:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    TextButton(onClick = { showClubsDialog = true }) {
                        Text("+ Join Club")
                    }
                }
            }

            if (character.joinedClubs.isEmpty()) {
                item {
                    Text(
                        text = "You haven't joined any school clubs or sports teams yet. Click '+ Join Club' to participate!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(character.joinedClubs) { clubName ->
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("⭐", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(clubName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Active Member • Boosts Smarts & Popularity", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }

            // University Higher Education Option
            if (character.age >= 18 && character.education != EducationLevel.UNIVERSITY) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = LifeGold.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = "🏛️ University Higher Education", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Apply to prestigious academic majors: Computer Science, Medicine, Law, Business, or Arts & Humanities to unlock top-tier careers!",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { showUniversityDialog = true },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = LifeGold)
                            ) {
                                Text("Apply for College Degree", fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showClubsDialog) {
        AlertDialog(
            onDismissRequest = { showClubsDialog = false },
            title = { Text("School Clubs & Sports Teams", fontWeight = FontWeight.Bold) },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(InvestmentAndSocialDatabase.SCHOOL_CLUBS) { club ->
                        val alreadyJoined = character.joinedClubs.contains(club.name)
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                if (!alreadyJoined) {
                                    onJoinClub(club)
                                    showClubsDialog = false
                                }
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(club.emoji, fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(club.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(club.description, style = MaterialTheme.typography.bodySmall, fontSize = 11.sp, maxLines = 2)
                                }
                                if (alreadyJoined) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = LifeEmerald)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showClubsDialog = false }) { Text("Close") }
            }
        )
    }

    if (showUniversityDialog) {
        AlertDialog(
            onDismissRequest = { showUniversityDialog = false },
            title = { Text("Apply for University Degree", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        Degree.COMPUTER_SCIENCE to "💻 Computer Science (Software Engineer, AI Specialist)",
                        Degree.BUSINESS to "📊 Business Administration (Finance, Manager, Executive)",
                        Degree.MEDICINE to "🩺 Medicine & Pre-Med (Physician, Surgeon, Nurse)",
                        Degree.LAW to "⚖️ Law & Jurisprudence (Corporate Attorney, Judge)",
                        Degree.ARTS to "🎨 Fine Arts & Literature (Author, Creative Director)"
                    ).forEach { (degree, desc) ->
                        OutlinedButton(
                            onClick = {
                                onApplyUniversity(degree)
                                showUniversityDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(desc, fontSize = 11.sp, maxLines = 1)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showUniversityDialog = false }) { Text("Cancel") }
            }
        )
    }
}
