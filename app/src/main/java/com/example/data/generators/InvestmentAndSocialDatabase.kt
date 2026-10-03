package com.example.data.generators

import com.example.data.model.*

object InvestmentAndSocialDatabase {

    val STOCKS: List<StockListing> = listOf(
        StockListing("AAPL", "Pear Technologies", "Consumer Tech", 195.50, +1.8, 0.55, "Global leader in smartphones, wearables, and ecosystem services.", "🍎"),
        StockListing("GOOG", "Alphabetical Systems", "Internet & AI", 168.20, +2.4, 0.40, "Search engine giant, cloud computing, and advanced neural models.", "🔍"),
        StockListing("MSFT", "MicroSim Software", "Cloud & Enterprise", 425.00, +0.9, 0.70, "Dominant enterprise OS, productivity software, and gaming studio.", "💻"),
        StockListing("NVDA", "TensorCore AI", "Semiconductors", 128.40, +4.5, 0.15, "Cutting-edge graphics processors powering global generative AI infrastructure.", "⚡"),
        StockListing("TSLA", "Volt Motors", "Automotive & Energy", 215.80, -1.2, 0.00, "Electric vehicle pioneer, autonomous driving, and battery gigafactories.", "🚗"),
        StockListing("AMZN", "Jungle Prime Logistics", "E-Commerce & Cloud", 182.10, +1.1, 0.00, "Global e-commerce retail infrastructure and web hosting services.", "📦"),
        StockListing("META", "Metaverse Connect", "Social & Spatial", 510.30, +3.2, 0.45, "Connecting billions of users through social feeds, VR headsets, and ads.", "🌐"),
        StockListing("NFLX", "StreamFlix Studios", "Entertainment", 645.00, +0.6, 0.00, "Subscription streaming entertainment and award-winning movie production.", "🍿"),
        StockListing("JPM", "Morgan Sim Bank", "Banking & Finance", 205.40, +0.8, 2.30, "World's largest diversified commercial and investment banking institution.", "🏦"),
        StockListing("V", "Apex Global Payments", "Fintech", 275.60, +0.4, 0.75, "Worldwide digital payment settlement rails processing trillions in transactions.", "💳"),
        StockListing("XOM", "Titan Energy Resources", "Energy & Oil", 112.50, -0.5, 3.40, "Global exploration, refining, and clean energy transition initiatives.", "⛽"),
        StockListing("PFE", "BioCure Pharmaceuticals", "Healthcare", 28.90, +0.2, 5.80, "Innovative vaccines, oncology treatments, and clinical biotech trials.", "💊"),
        StockListing("UNH", "UnitedHealth Alliance", "Healthcare", 535.00, +1.5, 1.45, "Comprehensive healthcare benefits, insurance networks, and clinical data.", "🩺"),
        StockListing("WMT", "MegaMart Supercenters", "Consumer Retail", 67.20, +0.3, 1.25, "Omnichannel grocery and household goods retail chain with thousands of stores.", "🛒"),
        StockListing("KO", "Classic Cola Beverages", "Consumer Goods", 64.50, +0.1, 3.05, "Iconic refreshing soft drink beverages distributed across 200+ nations.", "🥤"),
        StockListing("MCD", "Golden Burger Franchises", "Restaurants", 270.00, -0.4, 2.45, "World-famous fast food restaurants with legendary real estate holdings.", "🍔"),
        StockListing("DIS", "Magic World Entertainment", "Media & Parks", 98.40, +1.7, 0.90, "Theme parks, animation studios, family franchises, and cruise lines.", "🏰"),
        StockListing("BA", "SkyLiner Aerospace", "Industrial & Defense", 185.30, -2.1, 0.00, "Commercial jetliners, aerospace defense systems, and space technology.", "✈️"),
        StockListing("NKE", "Swoosh Sportswear", "Athletic Apparel", 78.50, +0.7, 1.85, "Footwear and performance athletic gear endorsed by elite athletes.", "👟"),
        StockListing("PLTR", "Sentinel Intelligence", "Defense & Enterprise AI", 27.80, +5.2, 0.00, "Mission-critical data fusion software for defense and commercial enterprises.", "🛡️")
    )

    val BONDS: List<BondListing> = listOf(
        BondListing("bond_treasury_10y", "US 10-Year Government Treasury", "Federal Reserve System", 1000L, 4.25, 10, "AAA", "🏛️"),
        BondListing("bond_muni_clean_water", "Municipal Clean Water & Transit Bond", "Metropolitan Transit Authority", 500L, 3.80, 5, "AA", "💧"),
        BondListing("bond_corp_bluechip", "Bluechip Corporate Investment Grade", "Pear & MicroSim Consortium", 2500L, 6.20, 7, "A", "🏢"),
        BondListing("bond_high_yield_tech", "High-Yield AI Data Center Expansion", "Silicon Valley Infrastructure", 1500L, 9.50, 4, "High Yield", "🚀")
    )

    val ETFS: List<EtfListing> = listOf(
        EtfListing("SPY-Sim", "Sim 500 Large-Cap Index ETF", "Broad Market", 545.20, +1.2, 0.03, "Tracks the performance of the 500 largest publicly traded companies.", "📊"),
        EtfListing("TECH-Sim", "Global Mega-Tech Innovation ETF", "Technology", 480.10, +2.8, 0.20, "Focuses on semiconductor, cloud, AI, and cybersecurity tech leaders.", "⚡"),
        EtfListing("GREEN-Sim", "Clean Energy & ESG Future ETF", "Renewables", 62.40, +0.5, 0.35, "Solar, wind, battery storage, and eco-sustainable industrial pioneers.", "🌱"),
        EtfListing("REIT-Sim", "Global Commercial Real Estate REIT", "Real Estate", 88.50, +0.4, 0.12, "Commercial towers, warehouses, data centers, and multi-family apartments.", "🏙️"),
        EtfListing("DIV-Sim", "World Dividend Aristocrats ETF", "Income & Value", 124.00, +0.6, 0.08, "Companies with 25+ consecutive years of increasing cash dividend payouts.", "💰")
    )

    val CRYPTO_COINS: List<CryptoListing> = listOf(
        CryptoListing("BTC", "SimCoin (Bitcoin Equivalent)", 64250.0, +3.8, 1260.0, "The original decentralized store of value digital gold.", "🪙"),
        CryptoListing("ETH", "BitEther (Ethereum Equivalent)", 3450.0, +4.2, 415.0, "Decentralized programmable smart contract global computer network.", "💎"),
        CryptoListing("SOL", "SolanaX", 152.00, +8.4, 71.0, "High-throughput lightning fast blockchain for dApps and meme tokens.", "☀️"),
        CryptoListing("DOGE", "DogeSim", 0.142, +12.5, 20.5, "The internet's favorite meme coin powered by community enthusiasm.", "🐕"),
        CryptoListing("ADA", "CardanoSim", 0.48, -1.2, 17.2, "Peer-reviewed academic blockchain with formal verification methods.", "📜"),
        CryptoListing("XRP", "RippleNet", 0.58, +0.9, 32.0, "Institutional cross-border remittance settlement digital asset.", "🌊"),
        CryptoListing("SHIB", "ShibaSim", 0.000024, +6.1, 14.2, "Decentralized canine token ecosystem with decentralized exchange.", "🐶"),
        CryptoListing("AVAX", "AvalancheSim", 28.50, +2.3, 11.2, "Subnet customizable blockchain network designed for infinite scale.", "🏔️"),
        CryptoListing("DOT", "PolkaChain", 6.80, -0.4, 9.8, "Interoperable multi-chain relay framework connecting parallel shards.", "🔴"),
        CryptoListing("PEPE", "PepeToken", 0.0000095, +18.4, 4.0, "Viral green frog cultural meme token with parabolic trading swings.", "🐸")
    )

    val LOAN_PLANS: List<LoanPlan> = listOf(
        LoanPlan("loan_personal", "Personal Micro-Loan", "Personal", 1000L, 10000L, 8.5, 580, "Quick cash infusion for personal necessities and everyday expenses.", "💵"),
        LoanPlan("loan_student", "Higher Education Student Loan", "Education", 10000L, 60000L, 4.5, 620, "Low-interest deferred tuition financing for college and university degrees.", "🎓"),
        LoanPlan("loan_auto", "Automotive Vehicle Financing", "Vehicle", 15000L, 80000L, 6.0, 650, "Fixed-rate auto financing to acquire reliable transportation or sports cars.", "🚗"),
        LoanPlan("loan_mortgage", "Residential Real Estate Mortgage", "Mortgage", 100000L, 1500000L, 5.2, 680, "30-year fixed home mortgage to purchase suburban homes and luxury condos.", "🏡"),
        LoanPlan("loan_business", "Commercial Enterprise Business Loan", "Business", 50000L, 1000000L, 7.0, 700, "Working capital and equipment financing to scale up businesses and hiring.", "🏢")
    )

    val BUSINESS_BLUEPRINTS: List<BusinessBlueprint> = listOf(
        BusinessBlueprint(
            id = "biz_lemonade",
            name = "Neighborhood Lemonade Stand",
            industry = "Food & Beverage",
            emoji = "🍋",
            minCapital = 50L,
            description = "A humble front-yard stand selling chilled fresh-squeezed citrus lemonade.",
            defaultProducts = listOf(
                BusinessProduct(name = "Classic Sweet Lemonade", unitCost = 1L, retailPrice = 2L, emoji = "🍋"),
                BusinessProduct(name = "Strawberry Mint Punch", unitCost = 2L, retailPrice = 4L, emoji = "🍓")
            )
        ),
        BusinessBlueprint(
            id = "biz_coffee",
            name = "Specialty Espresso Bar & Café",
            industry = "Food & Beverage",
            emoji = "☕",
            minCapital = 8000L,
            description = "Artisanal coffee roastery serving pour-overs, cold brew, and fresh croissants.",
            defaultProducts = listOf(
                BusinessProduct(name = "Single-Origin Oat Latte", unitCost = 2L, retailPrice = 6L, emoji = "☕"),
                BusinessProduct(name = "Cold Brew Nitro Tap", unitCost = 2L, retailPrice = 7L, emoji = "🥤"),
                BusinessProduct(name = "Almond Butter Croissant", unitCost = 2L, retailPrice = 5L, emoji = "🥐")
            )
        ),
        BusinessBlueprint(
            id = "biz_pizza",
            name = "Wood-Fired Neapolitan Pizzeria",
            industry = "Food & Beverage",
            emoji = "🍕",
            minCapital = 25000L,
            description = "Crispy wood-fired pizzas made with imported San Marzano tomatoes and mozzarella.",
            defaultProducts = listOf(
                BusinessProduct(name = "Margherita Speciale", unitCost = 5L, retailPrice = 18L, emoji = "🍕"),
                BusinessProduct(name = "Spicy Pepperoni & Hot Honey", unitCost = 6L, retailPrice = 22L, emoji = "🌶️"),
                BusinessProduct(name = "Truffle Mushroom Calzone", unitCost = 8L, retailPrice = 26L, emoji = "🍄")
            )
        ),
        BusinessBlueprint(
            id = "biz_streetwear",
            name = "Urban Streetwear & Apparel Label",
            industry = "Fashion & Apparel",
            emoji = "👕",
            minCapital = 15000L,
            description = "Limited-drop oversized hoodies, embroidered denim, and trendsetting sneakers.",
            defaultProducts = listOf(
                BusinessProduct(name = "Heavyweight Boxy Hoodie", unitCost = 18L, retailPrice = 85L, emoji = "🧥"),
                BusinessProduct(name = "Acid-Wash Graphic Tee", unitCost = 9L, retailPrice = 45L, emoji = "👕"),
                BusinessProduct(name = "Cargo Combat Pants", unitCost = 22L, retailPrice = 95L, emoji = "👖")
            )
        ),
        BusinessBlueprint(
            id = "biz_gym",
            name = "24/7 Powerhouse Fitness Center",
            industry = "Health & Wellness",
            emoji = "🏋️",
            minCapital = 60000L,
            description = "Commercial fitness club with olympic barbells, saunas, and personal training.",
            defaultProducts = listOf(
                BusinessProduct(name = "Monthly All-Access Pass", unitCost = 12L, retailPrice = 59L, emoji = "🎟️"),
                BusinessProduct(name = "Personal Training Pack", unitCost = 35L, retailPrice = 120L, emoji = "💪"),
                BusinessProduct(name = "Electrolyte Recovery Shake", unitCost = 2L, retailPrice = 8L, emoji = "🥤")
            )
        ),
        BusinessBlueprint(
            id = "biz_saas",
            name = "Cloud SaaS & AI Software Agency",
            industry = "Technology",
            emoji = "💻",
            minCapital = 45000L,
            description = "B2B enterprise automation platforms, cloud databases, and AI chatbots.",
            defaultProducts = listOf(
                BusinessProduct(name = "SaaS Monthly Enterprise Seat", unitCost = 15L, retailPrice = 99L, emoji = "💼"),
                BusinessProduct(name = "AI Workflow Integration", unitCost = 500L, retailPrice = 2500L, emoji = "⚡"),
                BusinessProduct(name = "24/7 Priority Tech SLA", unitCost = 200L, retailPrice = 950L, emoji = "🛡️")
            )
        ),
        BusinessBlueprint(
            id = "biz_games",
            name = "Indie Video Game Studio",
            industry = "Entertainment",
            emoji = "🎮",
            minCapital = 35000L,
            description = "Developing indie roguelikes, multiplayer games, and viral mobile titles.",
            defaultProducts = listOf(
                BusinessProduct(name = "Pixel Dungeon Crawler (Digital)", unitCost = 3L, retailPrice = 20L, emoji = "🗡️"),
                BusinessProduct(name = "Cosmetic Battle Pass", unitCost = 1L, retailPrice = 10L, emoji = "👑"),
                BusinessProduct(name = "Collector Physical Vinyl OST", unitCost = 10L, retailPrice = 35L, emoji = "🎵")
            )
        ),
        BusinessBlueprint(
            id = "biz_phone_hardware",
            name = "SimPhone Hardware & Chip Foundry",
            industry = "Consumer Electronics",
            emoji = "📱",
            minCapital = 150000L,
            description = "Designing and manufacturing next-generation smartphones and microchips.",
            defaultProducts = listOf(
                BusinessProduct(name = "Flagship Smartphone Model", unitCost = 350L, retailPrice = 899L, emoji = "📱"),
                BusinessProduct(name = "Fast Wireless Charging Hub", unitCost = 12L, retailPrice = 49L, emoji = "🔌"),
                BusinessProduct(name = "Protective Kevlar Case", unitCost = 5L, retailPrice = 35L, emoji = "📦")
            )
        ),
        BusinessBlueprint(
            id = "biz_logistics",
            name = "Nationwide Freight & Trucking",
            industry = "Transportation",
            emoji = "🚚",
            minCapital = 80000L,
            description = "Semi-truck fleet hauling cross-country freight, cold storage, and express cargo.",
            defaultProducts = listOf(
                BusinessProduct(name = "Interstate Pallet Route", unitCost = 450L, retailPrice = 1400L, emoji = "🚚"),
                BusinessProduct(name = "Refrigerated Produce Haul", unitCost = 650L, retailPrice = 2100L, emoji = "❄️"),
                BusinessProduct(name = "Overnight Hazardous Express", unitCost = 850L, retailPrice = 3200L, emoji = "⚡")
            )
        ),
        BusinessBlueprint(
            id = "biz_real_estate",
            name = "Commercial Real Estate Holdings",
            industry = "Real Estate",
            emoji = "🏢",
            minCapital = 300000L,
            description = "Acquiring retail plazas, office complexes, and industrial distribution centers.",
            defaultProducts = listOf(
                BusinessProduct(name = "Office Suite Monthly Lease", unitCost = 600L, retailPrice = 2800L, emoji = "🏢"),
                BusinessProduct(name = "Retail Corner Store Lease", unitCost = 900L, retailPrice = 4200L, emoji = "🏬"),
                BusinessProduct(name = "Industrial Logistics Hub Bay", unitCost = 1800L, retailPrice = 7500L, emoji = "🏭")
            )
        )
    )

    val SCHOOL_CLUBS: List<SchoolClub> = listOf(
        SchoolClub("club_chess", "Chess & Strategy Club", "♟️", "Academic", "Analyze grandmaster openings, calculate endgames, and compete in state tournaments.", smartsBonus = +6, happinessBonus = +3),
        SchoolClub("club_robotics", "Robotics & STEM Guild", "🤖", "Academic", "Code microcontrollers, solder circuits, and build competitive arena robots.", smartsBonus = +8, happinessBonus = +4),
        SchoolClub("club_debate", "Speech & Debate Society", "🗣️", "Academic", "Master rhetoric, cross-examination, and win trophies arguing national policy.", smartsBonus = +7, happinessBonus = +3),
        SchoolClub("club_drama", "Drama & Theater Troupe", "🎭", "Arts & Culture", "Perform Broadway musicals, memorize Shakespeare scripts, and bask under stage lights.", smartsBonus = +3, happinessBonus = +8),
        SchoolClub("club_soccer", "Varsity Soccer Athletics", "⚽", "Athletics", "Daily conditioning, tactical drills, and playoff glory on the stadium pitch.", smartsBonus = +2, happinessBonus = +7),
        SchoolClub("club_council", "Student Council Government", "🗳️", "Leadership", "Organize spirit weeks, lobby the principal, and lead student body initiatives.", smartsBonus = +5, happinessBonus = +6),
        SchoolClub("club_art", "Studio Arts & Manga Club", "🎨", "Arts & Culture", "Digital illustration, acrylic canvases, and self-publishing school fanzines.", smartsBonus = +3, happinessBonus = +7)
    )
}
