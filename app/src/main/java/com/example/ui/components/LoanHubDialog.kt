package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.generators.InvestmentAndSocialDatabase
import com.example.data.model.Character
import com.example.data.model.LoanPlan
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose
import java.text.NumberFormat
import java.util.Locale

@Composable
fun LoanHubDialog(
    character: Character,
    onTakeLoan: (plan: LoanPlan, amount: Long) -> Unit,
    onPayDebt: (amount: Long) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedPlan by remember { mutableStateOf<LoanPlan?>(null) }
    var loanAmountInput by remember { mutableStateOf("5000") }
    var payAmountInput by remember { mutableStateOf("1000") }

    val currencyFormatter = remember(character.currencySymbol) {
        NumberFormat.getNumberInstance(Locale.US).apply {
            maximumFractionDigits = 0
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🏦 SimBank Credit & Loans", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    TextButton(onClick = onDismiss) { Text("Close") }
                }

                // Credit Score Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            character.creditScore >= 740 -> LifeEmerald.copy(alpha = 0.15f)
                            character.creditScore >= 640 -> LifeGold.copy(alpha = 0.15f)
                            else -> LifeRose.copy(alpha = 0.15f)
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Your Credit Score Rating:", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "${character.creditScore} / 850",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = when {
                                    character.creditScore >= 740 -> LifeEmerald
                                    character.creditScore >= 640 -> LifeGold
                                    else -> LifeRose
                                }
                            )
                            Text(
                                text = when {
                                    character.creditScore >= 740 -> "⭐ Excellent (Lowest Interest Rates)"
                                    character.creditScore >= 640 -> "Good Standing"
                                    else -> "Fair / Subprime"
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Current Debt Balance:", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "${character.currencySymbol}${currencyFormatter.format(character.debt)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = if (character.debt > 0) LifeRose else LifeEmerald
                            )
                            Text(
                                text = "Bank: ${character.currencySymbol}${currencyFormatter.format(character.bankBalance)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = LifeGold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Pay Debt Section
                if (character.debt > 0) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Pay Down Outstanding Loan Balance:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { onPayDebt(minOf(1000L, character.debt, character.bankBalance)) },
                                    enabled = character.bankBalance >= 1000L,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Pay $1,000", fontSize = 11.sp)
                                }
                                Button(
                                    onClick = { onPayDebt(minOf(character.debt, character.bankBalance)) },
                                    enabled = character.bankBalance > 0,
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                                ) {
                                    Text("Pay Max Possible", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            Text(
                                text = "💡 Paying debt on time boosts your credit score by +5 to +15 points!",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 10.sp,
                                color = LifeGold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Text(text = "Available Loan Plans (5 Types):", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(InvestmentAndSocialDatabase.LOAN_PLANS) { plan ->
                        val qualified = character.creditScore >= plan.minCreditScore
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (qualified) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(plan.emoji, fontSize = 22.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(plan.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Text("Interest: ${plan.interestRate}% APR • Min Credit: ${plan.minCreditScore}", style = MaterialTheme.typography.bodySmall, fontSize = 11.sp)
                                        }
                                    }

                                    Button(
                                        onClick = { selectedPlan = plan },
                                        enabled = qualified,
                                        modifier = Modifier.height(32.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp)
                                    ) {
                                        Text(if (qualified) "Borrow" else "Locked", fontSize = 11.sp)
                                    }
                                }
                                Text(
                                    text = "Limit: ${character.currencySymbol}${currencyFormatter.format(plan.minAmount)} - ${character.currencySymbol}${currencyFormatter.format(plan.maxAmount)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (selectedPlan != null) {
        val plan = selectedPlan!!
        AlertDialog(
            onDismissRequest = { selectedPlan = null },
            title = { Text("Borrow ${plan.title}", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Interest Rate: ${plan.interestRate}% APR.\nSelect how much you wish to borrow:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    OutlinedTextField(
                        value = loanAmountInput,
                        onValueChange = { loanAmountInput = it.filter { c -> c.isDigit() } },
                        label = { Text("Loan Amount (${character.currencySymbol})") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    val amountLong = loanAmountInput.toLongOrNull() ?: 0L
                    if (amountLong !in plan.minAmount..plan.maxAmount) {
                        Text(
                            text = "Amount must be between ${character.currencySymbol}${plan.minAmount} and ${character.currencySymbol}${plan.maxAmount}",
                            color = LifeRose,
                            fontSize = 11.sp
                        )
                    }
                }
            },
            confirmButton = {
                val amountLong = loanAmountInput.toLongOrNull() ?: 0L
                Button(
                    onClick = {
                        onTakeLoan(plan, amountLong)
                        selectedPlan = null
                    },
                    enabled = amountLong in plan.minAmount..plan.maxAmount
                ) {
                    Text("Confirm Loan")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedPlan = null }) { Text("Cancel") }
            }
        )
    }
}
