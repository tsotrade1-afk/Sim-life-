package com.example.data.generators

import com.example.data.model.BillionaireEntry
import com.example.data.model.Character
import com.example.data.model.CountryInfo
import com.example.data.model.NewsArticle
import com.example.data.model.RoadmapMilestone

object NewsAndRichDatabase {

    val COUNTRIES: List<CountryInfo> = listOf(
        CountryInfo("United States", "🇺🇸", "USD", "$", listOf("New York", "San Francisco", "Los Angeles", "Chicago", "Miami")),
        CountryInfo("United Kingdom", "🇬🇧", "GBP", "£", listOf("London", "Manchester", "Edinburgh", "Birmingham", "Oxford")),
        CountryInfo("Germany", "🇩🇪", "EUR", "€", listOf("Berlin", "Munich", "Frankfurt", "Hamburg", "Cologne")),
        CountryInfo("France", "🇫🇷", "EUR", "€", listOf("Paris", "Lyon", "Marseille", "Bordeaux", "Nice")),
        CountryInfo("Japan", "🇯🇵", "JPY", "¥", listOf("Tokyo", "Osaka", "Kyoto", "Yokohama", "Sapporo")),
        CountryInfo("Canada", "🇨🇦", "CAD", "C$", listOf("Toronto", "Vancouver", "Montreal", "Calgary", "Ottawa")),
        CountryInfo("Australia", "🇦🇺", "AUD", "A$", listOf("Sydney", "Melbourne", "Brisbane", "Perth", "Adelaide")),
        CountryInfo("Switzerland", "🇨🇭", "CHF", "Fr", listOf("Zurich", "Geneva", "Basel", "Bern", "Lausanne")),
        CountryInfo("United Arab Emirates", "🇦🇪", "AED", "AED", listOf("Dubai", "Abu Dhabi", "Sharjah")),
        CountryInfo("Brazil", "🇧🇷", "BRL", "R$", listOf("São Paulo", "Rio de Janeiro", "Brasília", "Salvador")),
        CountryInfo("South Korea", "🇰🇷", "KRW", "₩", listOf("Seoul", "Busan", "Incheon", "Daegu")),
        CountryInfo("Singapore", "🇸🇬", "SGD", "S$", listOf("Singapore City", "Marina Bay", "Sentosa"))
    )

    val ROADMAP_MILESTONES: List<RoadmapMilestone> = listOf(
        RoadmapMilestone(
            phase = "PHASE 1 (COMPLETED)",
            title = "v2.1 Bug Fixes & Tech Category Expansion",
            status = "Completed",
            icon = "🛠️",
            description = "Fixed Age 0 bug, added dedicated Tech category in Asset store, SimBank transfers, and reverse life feed with auto-scroll.",
            highlights = listOf(
                "Age 0 bug completely fixed: age advances every year (+1 Year)",
                "Dedicated Tech & Electronics store category (SimPhones, Laptops, Watches)",
                "Smart Life Feed with reverse chronology and auto-scroll to latest age",
                "SimPhone cleanly accessed from Assets tab without header clutter"
            )
        ),
        RoadmapMilestone(
            phase = "PHASE 2 (COMPLETED)",
            title = "v2.0 SimPhone, Banking & Wealth Era",
            status = "Completed",
            icon = "📱",
            description = "SimPhone ($499) & SimPhone Pro ($999) released in 2001, SimBank with wire transfers, year-by-year news unlocking, and 100 Richest People ladder.",
            highlights = listOf(
                "Buy SimPhone ($499) or SimPhone Pro ($999) with player savings",
                "SimBank app: Send money to family/friends with 2-second wire transfer animation",
                "50 World News stories unlocking progressively year-by-year (1991 - 2026)",
                "Real-world 100 Richest People ladder on SimPhone Pro"
            )
        ),
        RoadmapMilestone(
            phase = "PHASE 3 (NEXT UP)",
            title = "Deep Customization Studio",
            status = "Next Up",
            icon = "🎨",
            description = "Extensive character creation studio requested by the community.",
            highlights = listOf(
                "Bespoke hairstyles, hair dyes, facial hair, and eye colors",
                "Fashion wardrobe: Casual, Business Formal, Streetwear, Haute Couture",
                "Personality trait matrix (Genius, Charismatic, Workaholic, Daredevil)",
                "Custom family heritage & starting parent wealth background"
            )
        ),
        RoadmapMilestone(
            phase = "PHASE 4 (PLANNED)",
            title = "Stock Market, Crypto & Business Empire",
            status = "Planned",
            icon = "📈",
            description = "Live simulated financial markets and company founding.",
            highlights = listOf(
                "Buy/Sell shares of tech giants, index funds, and commodity ETFs",
                "Crypto exchange with meme coins and blockchain volatility",
                "Found your own startup: Hire employees, run ad campaigns, go public (IPO)",
                "Commercial real estate developments: Hotels, malls, and skyscrapers"
            )
        ),
        RoadmapMilestone(
            phase = "PHASE 5 (PLANNED)",
            title = "Romance, Dating & Royal Family",
            status = "Planned",
            icon = "💍",
            description = "In-depth dating mechanics, extravagant weddings, and royal lineages.",
            highlights = listOf(
                "Dating app with profile matching and blind dates",
                "Marriage proposals, prenup agreements, and honeymoon getaways",
                "Having children, naming, schooling, and passing on family heirlooms",
                "Chance of dating foreign royalty or high-society aristocracy"
            )
        )
    )

    // 50 Diverse News Articles distributed chronologically from 1991 to 2026
    val PREBUILT_NEWS: List<NewsArticle> = listOf(
        NewsArticle("news_1991", 1991, "World Wide Web Code Opens to the Global Public", "Tim Berners-Lee publishes the first public files describing the World Wide Web, opening a hyperlinked global information space.", "Tech", "Global Net Bulletin", "🌐", "Began the digital interconnected information age."),
        NewsArticle("news_1992", 1992, "First SMS Mobile Text Message Sent Across Telecom Network", "An engineer transmits 'Merry Christmas' from a computer terminal to a mobile handset, establishing Short Message Service.", "Tech", "Telecom Dispatch", "💬", "Pioneered short-form mobile digital messaging."),
        NewsArticle("news_1993", 1993, "Mosaic Web Browser Launches with Graphical Images", "The NCSA releases the first graphical web browser supporting inline photos, sparking mass consumer internet adoption.", "Tech", "Computing World", "🖥️", "Transformed the web into a visual medium."),
        NewsArticle("news_1994", 1994, "First Online Secure Credit Card Purchase Completed", "A music lover purchases a Sting CD over an encrypted internet browser connection.", "Economy", "Digital Commerce", "💳", "Launched the modern trillion-dollar e-commerce industry."),
        NewsArticle("news_1995", 1995, "Digital Versatile Disc (DVD) Format Standardized", "Electronics giants agree on optical disc storage packing full-length movies with digital audio.", "Entertainment", "Media Review", "💿", "Replaced magnetic VHS tapes across homes worldwide."),
        NewsArticle("news_1996", 1996, "Dolly the Sheep Cloned from Adult Mammary Cell", "Scottish scientists successfully clone the first mammal from adult cells, sparking bioethical debates worldwide.", "Science", "Nature Gazette", "🐑", "Proved somatic cell nuclear transfer was viable."),
        NewsArticle("news_1997", 1997, "IBM Supercomputer Defeats World Chess Champion", "Deep Blue wins an official six-game match against Garry Kasparov, computing 200 million positions per second.", "Tech", "Grandmaster Journal", "♟️", "Milestone for algorithmic computation over human intuition."),
        NewsArticle("news_1998", 1998, "Search Engine Startup 'Google' Founded in Garage", "Two Stanford PhD students incorporate an algorithmic search engine prioritizing web page backlinks.", "Tech", "Silicon Valley Wire", "🔍", "Organized the world's information for decades to come."),
        NewsArticle("news_1999", 1999, "Global Tech Scramble Over Looming Y2K Millennium Bug", "Software developers worldwide audit millions of lines of date code before midnight bells strike.", "Tech", "Global Times", "⏰", "Catalyzed global software modernization."),
        NewsArticle("news_2000", 2000, "International Space Station Welcomes First Resident Crew", "Expedition 1 astronauts dock aboard the orbital laboratory for permanent scientific research.", "Science", "Astro Gazette", "🛰️", "Began continuous human presence in low Earth orbit."),
        NewsArticle("news_2001", 2001, "SimPhone Debuts Worldwide: The Mobile Era Arrives!", "Tech visionary manufacturers officially launch the SimPhone! Featuring built-in News and SimBank mobile banking.", "Tech", "Global Tech Times", "📱", "SimPhone ($499) and SimPhone Pro ($999) hit retail stores!"),
        NewsArticle("news_2001b", 2001, "Free Online Encyclopedia 'Wikipedia' Founded", "A collaborative open-source knowledge repository invites the world to edit and share verified human knowledge.", "Culture", "World Knowledge", "📖", "Democratized educational access across the globe."),
        NewsArticle("news_2002", 2002, "Euro Physical Banknotes and Coins Enter Circulation", "12 European nations officially replace their national currencies with unified Euro cash.", "Economy", "European Finance", "💶", "Created one of the world's most traded reserve currencies."),
        NewsArticle("news_2003", 2003, "Human Genome Project Complete: 3 Billion DNA Pairs Mapped", "International consortium successfully sequences 99% of human genetic code with 99.99% accuracy.", "Science", "Medical Lancet", "🧬", "Paved the way for personalized genomic medicine."),
        NewsArticle("news_2004", 2004, "Social Networking Sites Expand Across Universities", "Digital campus directories enable students to share photo albums, relationship status, and status updates.", "Culture", "Campus Courier", "👥", "Shifted social interaction into persistent digital profiles."),
        NewsArticle("news_2005", 2005, "Online Video Sharing Platform Launches First Clip", "A 19-second video of an elephant zoo visit marks the arrival of user-uploaded streaming video.", "Entertainment", "Video Frontier", "🎥", "Turned everyday creators into global media publishers."),
        NewsArticle("news_2006", 2006, "High-Definition Blu-ray Discs Hit Living Rooms", "1080p full HD home cinema discs deliver theater-grade video and lossless surround sound.", "Entertainment", "Home Cinema Digest", "📀", "Elevated home television displays to full HD fidelity."),
        NewsArticle("news_2007", 2007, "Capacitive Multi-Touch Screens Sweep Consumer Electronics", "Glass touch interfaces replace plastic physical styluses and tactile numeric keyboards.", "Tech", "Gadget Pulse", "👆", "Established smooth touch gestures as the universal UX standard."),
        NewsArticle("news_2008", 2008, "Mobile App Store Ecosystem Opens to Independent Coders", "Developers worldwide distribute software directly to millions of pockets with one tap.", "Tech", "App World", "🛍️", "Created the modern multimillion-job app developer industry."),
        NewsArticle("news_2009", 2009, "Cryptographic Digital Cash 'Bitcoin' Launches", "A decentralized proof-of-work blockchain issues its genesis block of 50 digital coins.", "Economy", "Cryptographic Review", "🪙", "Spawned the international cryptocurrency financial ecosystem."),
        NewsArticle("news_2010", 2010, "Lightweight Tablet Computers Revolutionize Digital Reading", "Thin glass tablets bring interactive textbooks, newspapers, and streaming video to millions of bedsides.", "Tech", "Device Weekly", "📟", "Reshaped modern digital publishing and school classrooms."),
        NewsArticle("news_2011", 2011, "Global Space Shuttle Fleet Concludes Final Mission", "After 135 missions over three decades, space agencies pivot to commercial orbital cargo partnerships.", "Science", "Cosmos Report", "🚀", "Opened the door for commercial aerospace startups."),
        NewsArticle("news_2012", 2012, "Higgs Boson Confirmed at Large Hadron Collider in Geneva", "Physicists announce discovery of the elementary particle that gives mass to matter in the cosmos.", "Science", "Nature Bulletin", "⚛️", "Validated the cornerstone of modern particle physics."),
        NewsArticle("news_2013", 2013, "Electric Sports Sedans Prove Battery Power Can Outrun Gas", "High-performance electric vehicles demonstrate 0-60 mph in under 3 seconds with zero tailpipe emissions.", "Economy", "Clean Wheels", "⚡", "Accelerated global commitments to green vehicle fleets."),
        NewsArticle("news_2014", 2014, "Smartwatches Track Heart Rate and Daily Activity on Wrists", "Wearable computers measure steps, heart rhythms, and notify users of incoming messages.", "Tech", "Wearable Daily", "⌚", "Spurred the worldwide quantified self health movement."),
        NewsArticle("news_2015", 2015, "Orbital Rocket Booster Achieves Historic Vertical Landing", "An orbital rocket stages engines in mid-air and touches down softly upright on a coastal concrete pad.", "Science", "Space Horizon", "🎯", "Proved rocket reusability, slashing orbital launch costs."),
        NewsArticle("news_2015b", 2015, "On-Demand Video Streaming Passes 100 Million Subscribers", "Binge-watching entire season premieres replaces traditional scheduled television broadcasting.", "Entertainment", "Media Digest", "🎬", "Hollywood shifts major production budgets to streaming platforms."),
        NewsArticle("news_2016", 2016, "Augmented Reality Mobile Game Fills Parks Worldwide", "Millions of players walk neighborhood parks catching virtual pocket monsters projected onto camera feeds.", "Culture", "Gaming Observer", "👾", "Proved real-world spatial augmented reality appeal."),
        NewsArticle("news_2016b", 2016, "Deep Neural Network Defeats Human World Go Champion", "An AI algorithm creates creative, counter-intuitive moves that stun the world's greatest Go grandmasters.", "Science", "AI Frontier", "🤖", "Showcased deep reinforcement learning solving intuition problems."),
        NewsArticle("news_2017", 2017, "3D Facial Recognition Replaces Fingerprint Scanners", "Structured infrared dot projectors scan biometric face depth in fractions of a second to unlock devices.", "Tech", "Cyber Security", "👁️", "Set the standard for seamless biometric device authentication."),
        NewsArticle("news_2018", 2018, "First Direct Image of a Supermassive Black Hole Shadow", "The Event Horizon Telescope array synthesizes global radio dishes to capture the glowing ring of M87*.", "Science", "Deep Sky Bulletin", "🌌", "Provided visual confirmation of general relativity."),
        NewsArticle("news_2019", 2019, "Foldable Flexible Glass Displays Hit Consumer Handsets", "Smartphones unfold like books into 8-inch mini tablets without visible seams.", "Tech", "Display Tech", "📱", "Reimagined pocket computer mobility and multitasking."),
        NewsArticle("news_2019b", 2019, "5G High-Speed Cellular Networks Deployed Across Metro Centers", "Gigabit mobile data speeds enable instant cloud gaming, remote telesurgery, and connected cars.", "Tech", "Telecom World", "📶", "Expanded bandwidth for dense urban sensor networks."),
        NewsArticle("news_2020", 2020, "Work From Home Becomes Universal Standard for Knowledge Workers", "Millions of professionals transition to home video calls, digital whiteboards, and flexible hours.", "World", "Global Workforce", "💻", "Decentralized corporate offices and revitalized suburban towns."),
        NewsArticle("news_2020b", 2020, "Synthetic mRNA Vaccine Technology Developed in 11 Months", "Programmable lipid nanoparticle genetic platforms deliver targeted pathogen immunity with 95% efficacy.", "Science", "Medical Lancet", "💉", "Revolutionized molecular biology and future antiviral defense."),
        NewsArticle("news_2021", 2021, "Civilian Astronauts Complete First All-Private Orbit Flight", "Ordinary teachers, doctors, and philanthropists spend three days orbiting Earth in a commercial capsule.", "World", "Orbital Courier", "🧑‍🚀", "Inaugurated commercial private civilian spaceflight."),
        NewsArticle("news_2021b", 2021, "Solar and Wind Power Become Cheapest Electricity Generation", "Falling module costs make renewable installations less expensive than coal and gas generation.", "Economy", "Energy Economist", "☀️", "Tipped global infrastructure investment decisively toward clean energy."),
        NewsArticle("news_2022", 2022, "James Webb Space Telescope Delivers Deepest Infrared Views", "Gold-mirrored space observatory captures infant galaxies shining just 300 million years after the Big Bang.", "Science", "Cosmos Report", "🔭", "Rewrote textbooks on early star formation and cosmic dawn."),
        NewsArticle("news_2022b", 2022, "Conversational Generative AI Achieves 100 Million Users", "Large language models demonstrate creative writing, software coding, and nuanced contextual reasoning.", "Tech", "Future Intelligence", "✨", "Marked the beginning of the worldwide Generative AI revolution."),
        NewsArticle("news_2023", 2023, "Commercial Driverless Robotaxis Launch in Major Cities", "Sensor-packed autonomous vehicles navigate busy downtown traffic with empty driver seats.", "Tech", "Autonomous World", "🚗", "Disrupted traditional urban transportation and parking models."),
        NewsArticle("news_2023b", 2023, "Global Semiconductor Designers Reach Multi-Trillion Valuations", "Massive demand for AI training cluster chips propels hardware manufacturers to historic market caps.", "Economy", "Wall Street Wire", "📊", "Sparked an unprecedented global race in computing infrastructure."),
        NewsArticle("news_2024", 2024, "Spatial Computing Headsets Merge Holograms with Living Rooms", "High-resolution micro-OLED passthrough displays render crisp floating application windows in mid-air.", "Tech", "Spatial Reality", "🥽", "Pushed virtual workspace and spatial entertainment into reality."),
        NewsArticle("news_2024b", 2024, "Brain-Computer Interface Allows Paralyzed Patient to Browse Web", "Neural implant electrodes translate motor cortex brainwaves into fluid mouse clicks and typing.", "Science", "Neural Bio Review", "🧠", "Restored digital independence to individuals with motor paralysis."),
        NewsArticle("news_2024c", 2024, "Global Electric Car Sales Cross 20% of All New Purchases", "Broad charging networks and budget electric city models establish EVs as the primary family vehicle.", "Economy", "Clean Wheels", "🔋", "Spurred national grid upgrades and battery recycling pipelines."),
        NewsArticle("news_2025", 2025, "Humanoid Bipedal Robots Assist in Factory Logistics", "Vision-guided walking robots handle heavy boxes and sort warehouse pallets alongside human teams.", "Tech", "Robotics Today", "🤖", "Automated repetitive industrial lifting and fulfillment jobs."),
        NewsArticle("news_2025b", 2025, "Cultivated Lab-Grown Steaks Enter Supermarket Shelves", "Cellular agriculture produces genuine beef and poultry without livestock grazing or slaughterhouses.", "Economy", "Agri-Tech Journal", "🥩", "Cut agricultural greenhouse emissions and spared millions of animals."),
        NewsArticle("news_2025c", 2025, "Quantum Computing Simulates Complex Molecular Enzymes", "1,000-qubit quantum processors model catalytic chemical reactions beyond classical supercomputers.", "Science", "Quantum Review", "💻", "Accelerated clean battery chemistry and novel drug discovery."),
        NewsArticle("news_2026", 2026, "Solid-State Ceramic Batteries Deliver 1,000 km EV Range", "Solid ceramic electrolytes eliminate thermal fire hazards and allow full 10-minute highway recharge.", "Tech", "Battery Chemist", "⚡", "Completely eliminated vehicle range anxiety worldwide."),
        NewsArticle("news_2026b", 2026, "Tokamak Fusion Pilot Reactor Sustains Net Clean Power for 30 Min", "Superconducting magnetic coils produce more megawatts of electricity than required to heat plasma.", "Science", "Fusion Power", "🔥", "Brought virtually limitless clean power to the edge of reality."),
        NewsArticle("news_2026c", 2026, "Universal Wireless Earbuds Translate 85 Spoken Languages Live", "Near-zero latency neural translation earbuds let travelers converse effortlessly across the globe.", "Tech", "Global Polyglot", "🎧", "Eradicated language barriers in international commerce and travel.")
    )

    // Helper: Returns only the news unlocked up to the player's current calendar year!
    fun getUnlockedNews(currentYear: Int): List<NewsArticle> {
        return PREBUILT_NEWS.filter { it.year <= currentYear }.sortedByDescending { it.year }
    }

    // The World's 100 Richest People Database
    val REAL_BILLIONAIRES: List<BillionaireEntry> = listOf(
        BillionaireEntry(1, "Elon Musk", 245_000_000_000L, "Tesla, SpaceX & xAI", "United States", emoji = "🚀"),
        BillionaireEntry(2, "Bernard Arnault & Family", 195_000_000_000L, "LVMH Luxury Goods", "France", emoji = "👜"),
        BillionaireEntry(3, "Jeff Bezos", 190_000_000_000L, "Amazon & Blue Origin", "United States", emoji = "📦"),
        BillionaireEntry(4, "Mark Zuckerberg", 175_000_000_000L, "Meta Platforms", "United States", emoji = "📱"),
        BillionaireEntry(5, "Larry Ellison", 155_000_000_000L, "Oracle Cloud", "United States", emoji = "☁️"),
        BillionaireEntry(6, "Warren Buffett", 135_000_000_000L, "Berkshire Hathaway", "United States", emoji = "📈"),
        BillionaireEntry(7, "Bill Gates", 130_000_000_000L, "Microsoft & Foundation", "United States", emoji = "💻"),
        BillionaireEntry(8, "Steve Ballmer", 125_000_000_000L, "Microsoft & LA Clippers", "United States", emoji = "🏀"),
        BillionaireEntry(9, "Larry Page", 120_000_000_000L, "Alphabet Google", "United States", emoji = "🔍"),
        BillionaireEntry(10, "Sergey Brin", 115_000_000_000L, "Alphabet Google", "United States", emoji = "🌐"),
        BillionaireEntry(11, "Jensen Huang", 110_000_000_000L, "Nvidia AI Chips", "United States", emoji = "⚡"),
        BillionaireEntry(12, "Mukesh Ambani", 105_000_000_000L, "Reliance Industries", "India", emoji = "🛢️"),
        BillionaireEntry(13, "Amancio Ortega", 100_000_000_000L, "Zara & Inditex", "Spain", emoji = "👗"),
        BillionaireEntry(14, "Gautam Adani", 85_000_000_000L, "Adani Group Infrastructure", "India", emoji = "🏗️"),
        BillionaireEntry(15, "Carlos Slim Helu", 82_000_000_000L, "América Móvil Telecom", "Mexico", emoji = "📞"),
        BillionaireEntry(16, "Michael Bloomberg", 80_000_000_000L, "Bloomberg LP", "United States", emoji = "📰"),
        BillionaireEntry(17, "Françoise Bettencourt Meyers", 78_000_000_000L, "L'Oréal Cosmetics", "France", emoji = "💄"),
        BillionaireEntry(18, "Jim Walton", 74_000_000_000L, "Walmart Retail", "United States", emoji = "🛒"),
        BillionaireEntry(19, "Rob Walton", 73_000_000_000L, "Walmart Retail", "United States", emoji = "🛒"),
        BillionaireEntry(20, "Alice Walton", 72_000_000_000L, "Walmart Retail", "United States", emoji = "🛒"),
        BillionaireEntry(21, "David Thomson & Family", 65_000_000_000L, "Thomson Reuters", "Canada", emoji = "📡"),
        BillionaireEntry(22, "Julia Flesher Koch & Family", 62_000_000_000L, "Koch Industries", "United States", emoji = "🏭"),
        BillionaireEntry(23, "Charles Koch", 61_000_000_000L, "Koch Industries", "United States", emoji = "🏭"),
        BillionaireEntry(24, "Zhong Shanshan", 60_000_000_000L, "Nongfu Spring Water", "China", emoji = "💧"),
        BillionaireEntry(25, "Mark Mateschitz", 40_000_000_000L, "Red Bull Energy", "Austria", emoji = "🥫"),
        BillionaireEntry(26, "MacKenzie Scott", 38_000_000_000L, "Philanthropy & Tech", "United States", emoji = "🤝"),
        BillionaireEntry(27, "Colin Huang", 37_000_000_000L, "PDD Holdings & Temu", "China", emoji = "🛍️"),
        BillionaireEntry(28, "Ma Huateng (Pony Ma)", 36_000_000_000L, "Tencent & WeChat", "China", emoji = "🐧"),
        BillionaireEntry(29, "Tadashi Yanai & Family", 35_000_000_000L, "Uniqlo Fast Retailing", "Japan", emoji = "👕"),
        BillionaireEntry(30, "Klaus-Michael Kuehne", 34_000_000_000L, "Kuehne + Nagel Logistics", "Germany", emoji = "🚢"),
        BillionaireEntry(31, "Ken Griffin", 33_000_000_000L, "Citadel Hedge Fund", "United States", emoji = "🏛️"),
        BillionaireEntry(32, "Giovanni Ferrero", 32_500_000_000L, "Nutella & Ferrero Chocolates", "Italy", emoji = "🍫"),
        BillionaireEntry(33, "Gianluigi & Rafaela Aponte", 32_000_000_000L, "MSC Shipping Lines", "Switzerland", emoji = "⚓"),
        BillionaireEntry(34, "Dieter Schwarz", 31_000_000_000L, "Lidl & Kaufland", "Germany", emoji = "🥖"),
        BillionaireEntry(35, "Li Ka-shing", 30_000_000_000L, "CK Hutchison Holdings", "Hong Kong", emoji = "🏙️"),
        BillionaireEntry(36, "Alain Wertheimer", 29_000_000_000L, "Chanel Haute Couture", "France", emoji = "✨"),
        BillionaireEntry(37, "Gerard Wertheimer", 29_000_000_000L, "Chanel Haute Couture", "France", emoji = "✨"),
        BillionaireEntry(38, "Len Blavatnik", 28_000_000_000L, "Access Industries & Music", "United Kingdom", emoji = "🎵"),
        BillionaireEntry(39, "Dan Gilbert", 27_000_000_000L, "Rocket Mortgage", "United States", emoji = "🏡"),
        BillionaireEntry(40, "Masayoshi Son", 26_000_000_000L, "SoftBank Vision Fund", "Japan", emoji = "💡"),
        BillionaireEntry(41, "Stephen Schwarzman", 25_500_000_000L, "Blackstone Private Equity", "United States", emoji = "🏢"),
        BillionaireEntry(42, "Takemitsu Takizaki", 25_000_000_000L, "Keyence Automation", "Japan", emoji = "🔬"),
        BillionaireEntry(43, "Gina Rinehart", 24_500_000_000L, "Hancock Prospecting Mining", "Australia", emoji = "⛏️"),
        BillionaireEntry(44, "Shiv Nadar", 24_000_000_000L, "HCL Enterprise Tech", "India", emoji = "🖥️"),
        BillionaireEntry(45, "Michael Dell", 23_500_000_000L, "Dell Technologies", "United States", emoji = "💻"),
        BillionaireEntry(46, "Thomas Peterffy", 23_000_000_000L, "Interactive Brokers", "United States", emoji = "📊"),
        BillionaireEntry(47, "Lukas Walton", 22_500_000_000L, "Walmart Holdings", "United States", emoji = "🛒"),
        BillionaireEntry(48, "Robin Zeng", 22_000_000_000L, "CATL EV Batteries", "China", emoji = "🔋"),
        BillionaireEntry(49, "Zhang Yiming", 21_500_000_000L, "ByteDance & TikTok", "China", emoji = "🎬"),
        BillionaireEntry(50, "Jack Ma", 21_000_000_000L, "Alibaba Group", "China", emoji = "🛍️"),
        BillionaireEntry(51, "William Ding", 20_500_000_000L, "NetEase Online Games", "China", emoji = "🎮"),
        BillionaireEntry(52, "Eric Schmidt", 20_000_000_000L, "Tech Investments", "United States", emoji = "🌐"),
        BillionaireEntry(53, "Abigail Johnson", 19_500_000_000L, "Fidelity Investments", "United States", emoji = "📈"),
        BillionaireEntry(54, "Savitri Jindal & Family", 19_000_000_000L, "Jindal Steel & Power", "India", emoji = "🏗️"),
        BillionaireEntry(55, "Susanne Klatten", 18_500_000_000L, "BMW & Altana Pharma", "Germany", emoji = "🏎️"),
        BillionaireEntry(56, "Stefan Quandt", 18_000_000_000L, "BMW Automotive", "Germany", emoji = "🏎️"),
        BillionaireEntry(57, "Leonid Mikhelson", 17_800_000_000L, "Novatek Natural Gas", "Russia", emoji = "⛽"),
        BillionaireEntry(58, "Cyrus Poonawalla", 17_500_000_000L, "Serum Institute Vaccines", "India", emoji = "💉"),
        BillionaireEntry(59, "Emmanuel Besnier", 17_200_000_000L, "Lactalis Dairy Group", "France", emoji = "🧀"),
        BillionaireEntry(60, "Lee Jae-yong", 17_000_000_000L, "Samsung Electronics", "South Korea", emoji = "📱"),
        BillionaireEntry(61, "Stefan Persson", 16_800_000_000L, "H&M Fashion Retail", "Sweden", emoji = "👕"),
        BillionaireEntry(62, "He Xiangjian", 16_500_000_000L, "Midea Smart Appliances", "China", emoji = "❄️"),
        BillionaireEntry(63, "Ray Dalio", 16_200_000_000L, "Bridgewater Associates", "United States", emoji = "🌊"),
        BillionaireEntry(64, "David Tepper", 16_000_000_000L, "Appaloosa Management", "United States", emoji = "🏈"),
        BillionaireEntry(65, "Jim Simons Estate", 15_800_000_000L, "Renaissance Technologies", "United States", emoji = "📐"),
        BillionaireEntry(66, "Prajogo Pangestu", 15_500_000_000L, "Barito Pacific Energy", "Indonesia", emoji = "⚡"),
        BillionaireEntry(67, "Low Tuck Kwong", 15_200_000_000L, "Bayan Resources Coal", "Indonesia", emoji = "⛏️"),
        BillionaireEntry(68, "Li Xiting", 15_000_000_000L, "Mindray Medical Devices", "Singapore", emoji = "🩺"),
        BillionaireEntry(69, "Goh Cheng Liang", 14_800_000_000L, "Nippon Paint Coatings", "Singapore", emoji = "🎨"),
        BillionaireEntry(70, "Renata Kellnerova & Family", 14_500_000_000L, "PPF Financial Group", "Czech Republic", emoji = "🏦"),
        BillionaireEntry(71, "Robert Kuok", 14_200_000_000L, "Shangri-La Hotels & Palm", "Malaysia", emoji = "🏨"),
        BillionaireEntry(72, "Vagit Alekperov", 14_000_000_000L, "Lukoil Oil & Energy", "Russia", emoji = "🛢️"),
        BillionaireEntry(73, "Alexey Mordashov", 13_800_000_000L, "Severstal Mining", "Russia", emoji = "⛏️"),
        BillionaireEntry(74, "Iris Fontbona & Family", 13_500_000_000L, "Antofagasta Copper", "Chile", emoji = "🥉"),
        BillionaireEntry(75, "Vicky Safra & Family", 13_200_000_000L, "Safra Banking Group", "Brazil", emoji = "💳"),
        BillionaireEntry(76, "Eduardo Saverin", 13_000_000_000L, "B Capital & Early Meta", "Singapore", emoji = "💻"),
        BillionaireEntry(77, "Harry Triguboff", 12_800_000_000L, "Meriton High-Rise Living", "Australia", emoji = "🏢"),
        BillionaireEntry(78, "Anthony Bamford & Family", 12_500_000_000L, "JCB Heavy Machinery", "United Kingdom", emoji = "🚜"),
        BillionaireEntry(79, "Kushal Pal Singh", 12_200_000_000L, "DLF Real Estate India", "India", emoji = "🏙️"),
        BillionaireEntry(80, "Melanie Perkins & Cliff Obrecht", 12_000_000_000L, "Canva Online Design", "Australia", emoji = "🎨"),
        BillionaireEntry(81, "Brian Chesky", 11_800_000_000L, "Airbnb Home Stays", "United States", emoji = "🏡"),
        BillionaireEntry(82, "Nathan Blecharczyk", 11_500_000_000L, "Airbnb Home Stays", "United States", emoji = "🏡"),
        BillionaireEntry(83, "Joe Gebbia", 11_500_000_000L, "Airbnb Home Stays", "United States", emoji = "🏡"),
        BillionaireEntry(84, "Jan Koum", 11_200_000_000L, "WhatsApp Messenger", "United States", emoji = "💬"),
        BillionaireEntry(85, "Bobby Murphy", 11_000_000_000L, "Snapchat & AR Glasses", "United States", emoji = "👻"),
        BillionaireEntry(86, "Evan Spiegel", 10_800_000_000L, "Snapchat & Camera Co", "United States", emoji = "👻"),
        BillionaireEntry(87, "Marc Benioff", 10_500_000_000L, "Salesforce CRM", "United States", emoji = "☁️"),
        BillionaireEntry(88, "Reed Hastings", 10_200_000_000L, "Netflix Entertainment", "United States", emoji = "🎬"),
        BillionaireEntry(89, "Gabe Newell", 10_000_000_000L, "Valve Steam Gaming", "United States", emoji = "🕹️"),
        BillionaireEntry(90, "Patrick Collison", 9_800_000_000L, "Stripe Global Payments", "Ireland", emoji = "💳"),
        BillionaireEntry(91, "John Collison", 9_800_000_000L, "Stripe Global Payments", "Ireland", emoji = "💳"),
        BillionaireEntry(92, "Pavel Durov", 9_500_000_000L, "Telegram Messenger", "United Arab Emirates", emoji = "✈️"),
        BillionaireEntry(93, "Garrett Camp", 9_200_000_000L, "Uber Technologies", "Canada", emoji = "🚕"),
        BillionaireEntry(94, "Travis Kalanick", 9_000_000_000L, "CloudKitchens & Early Uber", "United States", emoji = "🍳"),
        BillionaireEntry(95, "Jack Dorsey", 8_800_000_000L, "Block Square & Cash App", "United States", emoji = "📱"),
        BillionaireEntry(96, "Palmer Luckey", 8_500_000_000L, "Anduril Defense & Oculus", "United States", emoji = "🥽"),
        BillionaireEntry(97, "Sam Altman", 8_200_000_000L, "OpenAI & Hydrazine Investments", "United States", emoji = "✨"),
        BillionaireEntry(98, "Vitalik Buterin", 8_000_000_000L, "Ethereum Blockchain", "Canada", emoji = "💠"),
        BillionaireEntry(99, "Brian Armstrong", 7_800_000_000L, "Coinbase Global Crypto", "United States", emoji = "🪙"),
        BillionaireEntry(100, "Cathie Wood", 7_500_000_000L, "ARK Invest Disruptive Tech", "United States", emoji = "🚀")
    )

    fun getRichestLadder(character: Character): List<BillionaireEntry> {
        val playerNetWorth = character.bankBalance + character.assets.sumOf { it.value }

        val playerEntry = BillionaireEntry(
            rank = 0,
            name = "${character.fullName} (YOU)",
            netWorth = playerNetWorth,
            industry = character.occupation.title,
            country = character.country,
            isPlayer = true,
            emoji = character.avatarEmoji
        )

        // Combine and sort descending by net worth
        val fullList = (REAL_BILLIONAIRES + playerEntry).sortedByDescending { it.netWorth }

        // Find player's position
        val playerIndex = fullList.indexOfFirst { it.isPlayer }
        val playerRank = playerIndex + 1

        val result = fullList.mapIndexed { index, entry ->
            entry.copy(rank = index + 1)
        }

        return if (playerRank <= 100) {
            result.take(100)
        } else {
            result.take(98) + result[playerIndex]
        }
    }
}
