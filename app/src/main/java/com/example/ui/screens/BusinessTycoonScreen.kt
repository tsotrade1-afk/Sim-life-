package com.example.ui.screens

import androidx.compose.foundation.background
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
import com.example.data.model.BusinessBlueprint
import com.example.data.model.BusinessEntity
import com.example.data.model.BusinessProduct
import com.example.data.model.Character
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BusinessTycoonScreen(
    character: Character,
    onStartBusiness: (BusinessBlueprint) -> Unit,
    onHireEmployee: (businessId: String, count: Int) -> Unit,
    onHireManager: (businessId: String) -> Unit,
    onUpgradeStorage: (businessId: String) -> Unit,
    onAddProduct: (businessId: String, name: String, unitCost: Long, price: Long, emoji: String) -> Unit,
    onTakePublicIPO: (businessId: String) -> Unit,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var showStartBizDialog by remember { mutableStateOf(false) }
    var selectedBizForProduct by remember { mutableStateOf<BusinessEntity?>(null) }

    val currencyFormatter = remember(character.currencySymbol) {
        NumberFormat.getNumberInstance(Locale.US).apply {
            maximumFractionDigits = 0
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("business_tycoon_screen")
    ) {
        // Top Header
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
                            text = "🏢 Tycoon Empire & Enterprises",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Enterprises: ${character.businesses.size} • Total Valuation: ${character.currencySymbol}${currencyFormatter.format(character.totalBusinessValue)}",
                            style = MaterialTheme.typography.labelMedium,
                            color = LifeGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick = { showStartBizDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Start Business", fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
            if (character.businesses.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "🏭", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "You don't own any commercial enterprises yet!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Choose from 10 distinct industries ranging from coffee shops to smartphone foundries. Hire staff, optimize supply chain storage, and launch new products!",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(onClick = { showStartBizDialog = true }) {
                                Text("Browse 10 Business Blueprints")
                            }
                        }
                    }
                }
            } else {
                items(character.businesses) { business ->
                    BusinessEntityCard(
                        business = business,
                        currencySymbol = character.currencySymbol,
                        bankBalance = character.bankBalance,
                        onHireEmployee = { onHireEmployee(business.id, 1) },
                        onHireManager = { onHireManager(business.id) },
                        onUpgradeStorage = { onUpgradeStorage(business.id) },
                        onAddProduct = { selectedBizForProduct = business },
                        onTakePublicIPO = { onTakePublicIPO(business.id) }
                    )
                }
            }
        }
    }

    if (showStartBizDialog) {
        StartBusinessDialog(
            currencySymbol = character.currencySymbol,
            bankBalance = character.bankBalance,
            onDismiss = { showStartBizDialog = false },
            onSelect = { blueprint ->
                onStartBusiness(blueprint)
                showStartBizDialog = false
            }
        )
    }

    if (selectedBizForProduct != null) {
        val biz = selectedBizForProduct!!
        AddProductDialog(
            businessName = biz.name,
            currencySymbol = character.currencySymbol,
            onDismiss = { selectedBizForProduct = null },
            onAdd = { name, cost, price, emoji ->
                onAddProduct(biz.id, name, cost, price, emoji)
                selectedBizForProduct = null
            }
        )
    }
}

@Composable
private fun BusinessEntityCard(
    business: BusinessEntity,
    currencySymbol: String,
    bankBalance: Long,
    onHireEmployee: () -> Unit,
    onHireManager: () -> Unit,
    onUpgradeStorage: () -> Unit,
    onAddProduct: () -> Unit,
    onTakePublicIPO: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = business.emoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = business.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = "Industry: ${business.industry}", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Valuation: $currencySymbol${business.valuation}",
                        fontWeight = FontWeight.Bold,
                        color = LifeGold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Cash: $currencySymbol${business.cashBalance}",
                        style = MaterialTheme.typography.labelSmall,
                        color = LifeEmerald
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Management metrics
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "👥 Workforce: ${business.employees} Employees", style = MaterialTheme.typography.bodySmall)
                        Text(text = "👔 Managers: ${business.managers} (+25% Efficiency)", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "📦 Warehouse Storage: ${business.storageCapacity} units", style = MaterialTheme.typography.bodySmall)
                        Text(text = "🛍️ Products Listed: ${business.products.size}", style = MaterialTheme.typography.bodySmall)
                    }
                    if (business.annualRevenue > 0) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Annual Revenue: $currencySymbol${business.annualRevenue}", style = MaterialTheme.typography.labelSmall, color = LifeEmerald)
                            Text(text = "Annual Expenses: $currencySymbol${business.annualExpenses}", style = MaterialTheme.typography.labelSmall, color = LifeRose)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Products list
            Text(text = "Product Lineup & Catalog:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(business.products) { prod ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(prod.emoji, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(prod.name, fontWeight = FontWeight.SemiBold, fontSize = 11.sp)
                                Text(
                                    text = "Cost: $currencySymbol${prod.unitCost} → Price: $currencySymbol${prod.retailPrice}",
                                    fontSize = 10.sp,
                                    color = LifeEmerald
                                )
                            }
                        }
                    }
                }
                item {
                    OutlinedButton(
                        onClick = onAddProduct,
                        modifier = Modifier.height(44.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("+ New Product", fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onHireEmployee,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text("+ Hire Staff ($500)", fontSize = 10.sp)
                }
                OutlinedButton(
                    onClick = onHireManager,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text("+ Manager ($5K)", fontSize = 10.sp)
                }
                OutlinedButton(
                    onClick = onUpgradeStorage,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    Text("+ Storage ($3K)", fontSize = 10.sp)
                }
            }

            if (business.valuation >= 1000000L) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onTakePublicIPO,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = LifeGold)
                ) {
                    Text("🔔 Ring The Bell: Take Public (IPO)", fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }
    }
}

@Composable
private fun StartBusinessDialog(
    currencySymbol: String,
    bankBalance: Long,
    onDismiss: () -> Unit,
    onSelect: (BusinessBlueprint) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("🏢 Select Business Industry (10 Models)", fontWeight = FontWeight.Bold) },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(InvestmentAndSocialDatabase.BUSINESS_BLUEPRINTS) { blueprint ->
                    val canAfford = bankBalance >= blueprint.minCapital
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (canAfford) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        ),
                        onClick = {
                            if (canAfford) onSelect(blueprint)
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                Text(blueprint.emoji, fontSize = 28.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(blueprint.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(blueprint.description, style = MaterialTheme.typography.bodySmall, fontSize = 11.sp, maxLines = 2)
                                }
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Min: $currencySymbol${blueprint.minCapital}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = if (canAfford) LifeEmerald else LifeRose
                                )
                                if (canAfford) {
                                    Text("Available", color = LifeEmerald, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                } else {
                                    Text("Need Funds", color = LifeRose, fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        }
    )
}

@Composable
private fun AddProductDialog(
    businessName: String,
    currencySymbol: String,
    onDismiss: () -> Unit,
    onAdd: (name: String, unitCost: Long, price: Long, emoji: String) -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var unitCostStr by remember { mutableStateOf("10") }
    var retailPriceStr by remember { mutableStateOf("30") }
    var selectedEmoji by remember { mutableStateOf("📦") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Launch Product for $businessName", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Product Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = unitCostStr,
                        onValueChange = { unitCostStr = it.filter { c -> c.isDigit() } },
                        label = { Text("Cost ($currencySymbol)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = retailPriceStr,
                        onValueChange = { retailPriceStr = it.filter { c -> c.isDigit() } },
                        label = { Text("Price ($currencySymbol)") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Text("Pick Product Icon:", style = MaterialTheme.typography.labelSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("📦", "💎", "⭐", "🍕", "☕", "📱", "🎮", "👕", "⚡").forEach { emoji ->
                        FilterChip(
                            selected = selectedEmoji == emoji,
                            onClick = { selectedEmoji = emoji },
                            label = { Text(emoji, fontSize = 16.sp) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            val cost = unitCostStr.toLongOrNull() ?: 1L
            val price = retailPriceStr.toLongOrNull() ?: 2L
            Button(
                onClick = { onAdd(productName, cost, price, selectedEmoji) },
                enabled = productName.isNotBlank() && price > cost
            ) {
                Text("Add to Catalog")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
