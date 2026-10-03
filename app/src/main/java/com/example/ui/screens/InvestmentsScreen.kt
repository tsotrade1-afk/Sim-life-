package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.generators.InvestmentAndSocialDatabase
import com.example.data.model.*
import com.example.ui.theme.LifeEmerald
import com.example.ui.theme.LifeGold
import com.example.ui.theme.LifeRose
import java.text.NumberFormat
import java.util.Locale

enum class InvestmentTab {
    STOCKS,
    BONDS,
    ETFS,
    CRYPTO,
    CUSTOM_CRYPTO
}

@Composable
fun InvestmentsScreen(
    character: Character,
    onBuyStock: (StockListing, Long) -> Unit,
    onSellStock: (StockListing, Long) -> Unit,
    onBuyBond: (BondListing, Long) -> Unit,
    onSellBond: (BondListing, Long) -> Unit,
    onBuyEtf: (EtfListing, Long) -> Unit,
    onSellEtf: (EtfListing, Long) -> Unit,
    onBuyCrypto: (CryptoListing, Double) -> Unit,
    onSellCrypto: (CryptoListing, Double) -> Unit,
    onCreateCustomCrypto: (name: String, symbol: String, funding: Long) -> Unit,
    onBoostCustomCrypto: (coinId: String, actionType: String) -> Unit,
    onSellCustomCrypto: (coinId: String, amount: Double) -> Unit,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(InvestmentTab.STOCKS) }
    var showCreateCoinDialog by remember { mutableStateOf(false) }

    val currencyFormatter = remember(character.currencySymbol) {
        NumberFormat.getNumberInstance(Locale.US).apply {
            maximumFractionDigits = 0
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("investments_screen")
    ) {
        // Header
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
                    Column {
                        Text(
                            text = "📈 SimTrade Exchange",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Portfolio: ${character.currencySymbol}${currencyFormatter.format(character.totalInvestmentsValue)} • Bank: ${character.currencySymbol}${currencyFormatter.format(character.bankBalance)}",
                            style = MaterialTheme.typography.labelMedium,
                            color = LifeGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tab Selector
                ScrollableTabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    edgePadding = 0.dp,
                    containerColor = Color.Transparent,
                    divider = {}
                ) {
                    Tab(
                        selected = selectedTab == InvestmentTab.STOCKS,
                        onClick = { selectedTab = InvestmentTab.STOCKS },
                        text = { Text("Stocks (20)", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == InvestmentTab.BONDS,
                        onClick = { selectedTab = InvestmentTab.BONDS },
                        text = { Text("Bonds", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == InvestmentTab.ETFS,
                        onClick = { selectedTab = InvestmentTab.ETFS },
                        text = { Text("ETFs", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == InvestmentTab.CRYPTO,
                        onClick = { selectedTab = InvestmentTab.CRYPTO },
                        text = { Text("Crypto (10)", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == InvestmentTab.CUSTOM_CRYPTO,
                        onClick = { selectedTab = InvestmentTab.CUSTOM_CRYPTO },
                        text = { Text("My Coins 🚀", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                    )
                }
            }
        }

        // Tab Content
        when (selectedTab) {
            InvestmentTab.STOCKS -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    item {
                        Text(
                            text = "Bluechip & High-Growth Equities (20 Listed)",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    items(InvestmentAndSocialDatabase.STOCKS) { stock ->
                        val sharesOwned = character.stockHoldings[stock.symbol] ?: 0L
                        StockCard(
                            stock = stock,
                            sharesOwned = sharesOwned,
                            currencySymbol = character.currencySymbol,
                            bankBalance = character.bankBalance,
                            onBuy = { onBuyStock(stock, 1) },
                            onBuyTen = { onBuyStock(stock, 10) },
                            onSell = { onSellStock(stock, 1) },
                            onSellAll = { onSellStock(stock, sharesOwned) }
                        )
                    }
                }
            }

            InvestmentTab.BONDS -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    item {
                        Text(
                            text = "Guaranteed Yield Government & Corporate Bonds",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    items(InvestmentAndSocialDatabase.BONDS) { bond ->
                        val countOwned = character.bondHoldings[bond.id] ?: 0L
                        BondCard(
                            bond = bond,
                            countOwned = countOwned,
                            currencySymbol = character.currencySymbol,
                            bankBalance = character.bankBalance,
                            onBuy = { onBuyBond(bond, 1) },
                            onSell = { onSellBond(bond, 1) }
                        )
                    }
                }
            }

            InvestmentTab.ETFS -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    item {
                        Text(
                            text = "Diversified Index & Thematic ETFs",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    items(InvestmentAndSocialDatabase.ETFS) { etf ->
                        val sharesOwned = character.etfHoldings[etf.symbol] ?: 0L
                        EtfCard(
                            etf = etf,
                            sharesOwned = sharesOwned,
                            currencySymbol = character.currencySymbol,
                            bankBalance = character.bankBalance,
                            onBuy = { onBuyEtf(etf, 1) },
                            onBuyFive = { onBuyEtf(etf, 5) },
                            onSell = { onSellEtf(etf, 1) }
                        )
                    }
                }
            }

            InvestmentTab.CRYPTO -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    item {
                        Text(
                            text = "Top 10 Cryptocurrencies (High Volatility)",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    items(InvestmentAndSocialDatabase.CRYPTO_COINS) { coin ->
                        val coinsOwned = character.cryptoHoldings[coin.symbol] ?: 0.0
                        CryptoCard(
                            coin = coin,
                            coinsOwned = coinsOwned,
                            currencySymbol = character.currencySymbol,
                            bankBalance = character.bankBalance,
                            onBuyUsd = { usd -> onBuyCrypto(coin, usd) },
                            onSellAll = { onSellCrypto(coin, coinsOwned * coin.price) }
                        )
                    }
                }
            }

            InvestmentTab.CUSTOM_CRYPTO -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "🚀 Launch & Manage Your Own Crypto Coins",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Found multiple custom tokens, manage whitepapers, burn supply, push developer updates, and cash out profits year by year!",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = { showCreateCoinDialog = true },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Found New Crypto Token (Min $200)")
                                }
                            }
                        }
                    }

                    if (character.customCryptoCoins.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No custom crypto coins created yet.\nClick 'Found New Crypto Token' above to begin!",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    } else {
                        items(character.customCryptoCoins) { coin ->
                            CustomCoinCard(
                                coin = coin,
                                currencySymbol = character.currencySymbol,
                                bankBalance = character.bankBalance,
                                onBoost = { action -> onBoostCustomCrypto(coin.id, action) },
                                onCashOut = { amount -> onSellCustomCrypto(coin.id, amount) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showCreateCoinDialog) {
        CreateCustomCoinDialog(
            currencySymbol = character.currencySymbol,
            bankBalance = character.bankBalance,
            onDismiss = { showCreateCoinDialog = false },
            onCreate = { name, ticker, funding ->
                onCreateCustomCrypto(name, ticker, funding)
                showCreateCoinDialog = false
            }
        )
    }
}

@Composable
private fun StockCard(
    stock: StockListing,
    sharesOwned: Long,
    currencySymbol: String,
    bankBalance: Long,
    onBuy: () -> Unit,
    onBuyTen: () -> Unit,
    onSell: () -> Unit,
    onSellAll: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Text(text = stock.emoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stock.symbol, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = stock.sector,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = stock.name,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$currencySymbol${String.format(Locale.US, "%.2f", stock.price)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    val isPositive = stock.changePercent >= 0
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isPositive) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                            contentDescription = null,
                            tint = if (isPositive) LifeEmerald else LifeRose,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "${if (isPositive) "+" else ""}${String.format(Locale.US, "%.1f", stock.changePercent)}%",
                            color = if (isPositive) LifeEmerald else LifeRose,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Owned: $sharesOwned shares ($currencySymbol${String.format(Locale.US, "%.0f", sharesOwned * stock.price)})",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (sharesOwned > 0) LifeGold else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (sharesOwned > 0) {
                        OutlinedButton(
                            onClick = onSell,
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text("Sell 1", fontSize = 11.sp)
                        }
                        OutlinedButton(
                            onClick = onSellAll,
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text("Sell All", fontSize = 11.sp)
                        }
                    }

                    Button(
                        onClick = onBuy,
                        enabled = bankBalance >= stock.price.toLong(),
                        modifier = Modifier.height(32.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp)
                    ) {
                        Text("+ Buy 1", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onBuyTen,
                        enabled = bankBalance >= (stock.price * 10).toLong(),
                        modifier = Modifier.height(32.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp)
                    ) {
                        Text("+ 10", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun BondCard(
    bond: BondListing,
    countOwned: Long,
    currencySymbol: String,
    bankBalance: Long,
    onBuy: () -> Unit,
    onSell: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Text(text = bond.emoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = bond.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "Issuer: ${bond.issuer} • Rating: ${bond.riskRating}", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "$currencySymbol${bond.price}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(
                        text = "${bond.annualYield}% Annual Yield",
                        color = LifeEmerald,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Holding: $countOwned bonds (Earns $currencySymbol${String.format(Locale.US, "%.0f", countOwned * bond.price * (bond.annualYield / 100.0))}/yr)",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (countOwned > 0) LifeGold else MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (countOwned > 0) {
                        OutlinedButton(onClick = onSell, modifier = Modifier.height(32.dp)) {
                            Text("Redeem 1", fontSize = 11.sp)
                        }
                    }
                    Button(
                        onClick = onBuy,
                        enabled = bankBalance >= bond.price,
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Buy Bond", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun EtfCard(
    etf: EtfListing,
    sharesOwned: Long,
    currencySymbol: String,
    bankBalance: Long,
    onBuy: () -> Unit,
    onBuyFive: () -> Unit,
    onSell: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Text(text = etf.emoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "${etf.symbol} - ${etf.name}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = etf.description, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "$currencySymbol${String.format(Locale.US, "%.2f", etf.price)}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(
                        text = "+${String.format(Locale.US, "%.1f", etf.changePercent)}%",
                        color = LifeEmerald,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Owned: $sharesOwned shares ($currencySymbol${String.format(Locale.US, "%.0f", sharesOwned * etf.price)})",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (sharesOwned > 0) LifeGold else MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (sharesOwned > 0) {
                        OutlinedButton(onClick = onSell, modifier = Modifier.height(32.dp)) {
                            Text("Sell 1", fontSize = 11.sp)
                        }
                    }
                    Button(
                        onClick = onBuy,
                        enabled = bankBalance >= etf.price.toLong(),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("+ Buy 1", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = onBuyFive,
                        enabled = bankBalance >= (etf.price * 5).toLong(),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("+ 5", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun CryptoCard(
    coin: CryptoListing,
    coinsOwned: Double,
    currencySymbol: String,
    bankBalance: Long,
    onBuyUsd: (Double) -> Unit,
    onSellAll: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Text(text = coin.emoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "${coin.name} (${coin.symbol})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "Market Cap: $${coin.marketCapBillions}B", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    val priceStr = if (coin.price < 0.01) String.format(Locale.US, "%.6f", coin.price) else String.format(Locale.US, "%.2f", coin.price)
                    Text(text = "$currencySymbol$priceStr", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    val isPos = coin.changePercent >= 0
                    Text(
                        text = "${if (isPos) "+" else ""}${String.format(Locale.US, "%.1f", coin.changePercent)}%",
                        color = if (isPos) LifeEmerald else LifeRose,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val ownedUsd = coinsOwned * coin.price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Owned: ${String.format(Locale.US, "%.4f", coinsOwned)} ($currencySymbol${String.format(Locale.US, "%.0f", ownedUsd)})",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (ownedUsd > 1) LifeGold else MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (ownedUsd > 1.0) {
                        OutlinedButton(onClick = onSellAll, modifier = Modifier.height(32.dp)) {
                            Text("Sell All", fontSize = 11.sp)
                        }
                    }
                    Button(
                        onClick = { onBuyUsd(100.0) },
                        enabled = bankBalance >= 100L,
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("+ $100", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { onBuyUsd(1000.0) },
                        enabled = bankBalance >= 1000L,
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("+ $1K", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomCoinCard(
    coin: CustomCryptoCoin,
    currencySymbol: String,
    bankBalance: Long,
    onBoost: (String) -> Unit,
    onCashOut: (Double) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = coin.emoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = coin.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(shape = RoundedCornerShape(4.dp), color = LifeGold.copy(alpha = 0.2f)) {
                                Text(
                                    text = "$${coin.symbol}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LifeGold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Hype: ${coin.hypeRating}/100 • Community: ${coin.communityMembers} • GitHub Updates: ${coin.devUpdatesCount}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$currencySymbol${String.format(Locale.US, "%.5f", coin.price)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Market Cap: $currencySymbol${coin.marketCap}",
                        style = MaterialTheme.typography.labelSmall,
                        color = LifeEmerald
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            val myHoldingsValue = (coin.playerCoinsHeld * coin.price).toLong()
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Your Founder Holdings:", style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = "${String.format(Locale.US, "%.0f", coin.playerCoinsHeld)} tokens ($currencySymbol$myHoldingsValue)",
                            fontWeight = FontWeight.Bold,
                            color = LifeGold,
                            fontSize = 13.sp
                        )
                    }
                    if (myHoldingsValue > 10L) {
                        Button(
                            onClick = { onCashOut(coin.playerCoinsHeld * 0.25) },
                            modifier = Modifier.height(30.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = LifeEmerald)
                        ) {
                            Text("Cash Out 25%", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Grow & Pump Token Value:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    OutlinedButton(
                        onClick = { onBoost("marketing") },
                        enabled = bankBalance >= 500L,
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("📢 Viral Marketing ($500)", fontSize = 11.sp)
                    }
                }
                item {
                    OutlinedButton(
                        onClick = { onBoost("whitepaper") },
                        enabled = !coin.hasWhitepaper && bankBalance >= 200L,
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(if (coin.hasWhitepaper) "✅ Whitepaper Published" else "📜 Publish Whitepaper ($200)", fontSize = 11.sp)
                    }
                }
                item {
                    OutlinedButton(
                        onClick = { onBoost("token_burn") },
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("🔥 Burn 15% Supply", fontSize = 11.sp)
                    }
                }
                item {
                    OutlinedButton(
                        onClick = { onBoost("dev_commits") },
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("💻 Push Code Commits", fontSize = 11.sp)
                    }
                }
                item {
                    OutlinedButton(
                        onClick = { onBoost("influencer") },
                        enabled = bankBalance >= 1500L,
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("🌟 Influencer Promo ($1.5K)", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun CreateCustomCoinDialog(
    currencySymbol: String,
    bankBalance: Long,
    onDismiss: () -> Unit,
    onCreate: (name: String, ticker: String, funding: Long) -> Unit
) {
    var tokenName by remember { mutableStateOf("") }
    var tokenTicker by remember { mutableStateOf("") }
    var initialLiquidity by remember { mutableStateOf("500") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("🚀 Create Your Own Crypto Token", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Launch a brand new cryptocurrency on the SimChain! You will receive 50% of the initial circulating supply as the founder.",
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedTextField(
                    value = tokenName,
                    onValueChange = { tokenName = it },
                    label = { Text("Token Name (e.g. MoonRocket)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = tokenTicker,
                    onValueChange = { tokenTicker = it.uppercase().take(6) },
                    label = { Text("Ticker Symbol (e.g. MOON)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = initialLiquidity,
                    onValueChange = { initialLiquidity = it.filter { char -> char.isDigit() } },
                    label = { Text("Initial Liquidity Pool ($currencySymbol)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                val fundingLong = initialLiquidity.toLongOrNull() ?: 0L
                if (fundingLong > bankBalance) {
                    Text("Insufficient bank funds (Need $currencySymbol$fundingLong)", color = LifeRose, fontSize = 11.sp)
                }
            }
        },
        confirmButton = {
            val fundingLong = initialLiquidity.toLongOrNull() ?: 0L
            Button(
                onClick = { onCreate(tokenName, tokenTicker, fundingLong) },
                enabled = tokenName.isNotBlank() && tokenTicker.isNotBlank() && fundingLong in 200..bankBalance
            ) {
                Text("Deploy Token", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
