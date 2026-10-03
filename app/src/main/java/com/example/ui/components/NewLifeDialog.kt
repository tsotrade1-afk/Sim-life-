package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.generators.NewsAndRichDatabase
import com.example.data.model.Gender
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewLifeDialog(
    onConfirm: (name: String?, gender: Gender?, country: String?, birthYear: Int?) -> Unit,
    onDismiss: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.MALE) }
    var selectedCountry by remember { mutableStateOf(NewsAndRichDatabase.COUNTRIES.first()) }
    var birthYear by remember { mutableIntStateOf(2000) }

    val countries = NewsAndRichDatabase.COUNTRIES
    var countryMenuExpanded by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
                .testTag("new_life_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "🌱 Start a New Life",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Customize your character, choose birth year (1991 - 2026), and select your nation & currency.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Name Inputs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("new_life_first_name_input")
                    )

                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("new_life_last_name_input")
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Gender Chips
                Text(
                    text = "Gender",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Gender.values().forEach { g ->
                        FilterChip(
                            selected = selectedGender == g,
                            onClick = { selectedGender = g },
                            label = { Text("${g.icon} ${g.displayName}", fontSize = 11.sp) },
                            modifier = Modifier.testTag("gender_chip_${g.name}")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Birth Year Selector (1991 to 2026)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Birth Year",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "Year $birthYear",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Slider(
                    value = birthYear.toFloat(),
                    onValueChange = { birthYear = it.toInt() },
                    valueRange = 1991f..2026f,
                    steps = 34,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("birth_year_slider")
                )

                Text(
                    text = if (birthYear < 2007) "Born in $birthYear: You will experience the 2007 Smartphone Revolution!" else "Born in $birthYear: Modern touchscreen smartphone era.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Country & Currency Selection
                Text(
                    text = "Country of Birth & Local Currency",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))

                ExposedDropdownMenuBox(
                    expanded = countryMenuExpanded,
                    onExpandedChange = { countryMenuExpanded = it }
                ) {
                    OutlinedTextField(
                        value = "${selectedCountry.flag} ${selectedCountry.name} (${selectedCountry.currencySymbol} ${selectedCountry.currencyCode})",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = countryMenuExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .testTag("country_select_field")
                    )

                    ExposedDropdownMenu(
                        expanded = countryMenuExpanded,
                        onDismissRequest = { countryMenuExpanded = false }
                    ) {
                        countries.forEach { country ->
                            DropdownMenuItem(
                                text = { Text("${country.flag} ${country.name} (${country.currencySymbol} ${country.currencyCode})") },
                                onClick = {
                                    selectedCountry = country
                                    countryMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Future Roadmap Announcement Card
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "🗺️ Phase 2 Roadmap Preview",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = LifeGold
                        )
                        Text(
                            text = "Deep Customization Studio (Hairstyles, Wardrobe, DNA genetics) is in development! Track progress in the Settings Hub.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            val finalName = if (firstName.isNotBlank() || lastName.isNotBlank()) {
                                "${firstName.trim()} ${lastName.trim()}".trim()
                            } else null
                            onConfirm(finalName, selectedGender, selectedCountry.name, birthYear)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("confirm_new_life_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                    ) {
                        Text("Begin Life", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
