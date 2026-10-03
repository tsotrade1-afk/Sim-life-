package com.example.data.generators

import com.example.data.model.*
import kotlin.random.Random

object LifeEventGenerator {

    private val FIRST_NAMES_MALE = listOf(
        "James", "Alexander", "Liam", "Ethan", "Noah", "Lucas", "Oliver", "Leo",
        "Benjamin", "Mateo", "Kenji", "Arthur", "Marcus", "Julian", "Gabriel", "Kai"
    )

    private val FIRST_NAMES_FEMALE = listOf(
        "Emma", "Sophia", "Olivia", "Mia", "Aria", "Chloe", "Zoe", "Amelia",
        "Isabella", "Maya", "Yuki", "Elena", "Harper", "Nora", "Lily", "Clara"
    )

    private val LAST_NAMES = listOf(
        "Smith", "Johnson", "Miller", "Davis", "Garcia", "Rodriguez", "Chen",
        "Tanaka", "Dubois", "Schmidt", "Silva", "Taylor", "Anderson", "Kim", "Patel"
    )

    private val COUNTRIES_AND_CITIES = listOf(
        Pair("United States", "New York"),
        Pair("United States", "San Francisco"),
        Pair("United Kingdom", "London"),
        Pair("Canada", "Toronto"),
        Pair("Australia", "Sydney"),
        Pair("Japan", "Tokyo"),
        Pair("France", "Paris"),
        Pair("Germany", "Berlin"),
        Pair("Brazil", "Rio de Janeiro"),
        Pair("South Korea", "Seoul")
    )

    private val ZODIACS = listOf(
        "Aries ♈", "Taurus ♉", "Gemini ♊", "Cancer ♋",
        "Leo ♌", "Virgo ♍", "Libra ♎", "Scorpio ♏",
        "Sagittarius ♐", "Capricorn ♑", "Aquarius ♒", "Pisces ♓"
    )

    val GAME_UPDATE_LOGS = listOf(
        UpdateLogEntry(
            version = "v2.4 - The Desktop PC, 10 Business Empires, SimPhone Max & Real Life [LATEST UPDATE]",
            releaseDate = "October 2026",
            highlight = "Year 2003 Desktop PC with R-Team (5 playable games), 10 in-depth businesses with managers & custom phone manufacturing, SimPhone Max, parent meals under 18, and home router requirement!",
            changes = listOf(
                "🖥️ Year 2003 Desktop PC Era: Personal computers launch in 2003! Access the PC Workstation to use desktop tools and the 'R-Team' gaming platform!",
                "🎮 R-Team Gaming Suite: Play 5 real mini-games on PC (Brick Breaker, Cyber Snake, High-Low Guess, Crypto Clicker Miner, and Memory Matrix) to boost happiness and smarts!",
                "🏢 10 Startable Businesses: Launch Coffee Roasteries, Food Trucks, Streetwear Brands, Tech Labs, Gyms, Breweries, Ad Agencies, Game Studios, and Auto Custom shops with manager hiring, warehouse storage, product lines, and finance tracking!",
                "📱 Custom Smartphone Hardware: Found a Game & Tech Studio to design and manufacture your own custom smartphone models with international logistics shipping partners!",
                "⚡ SimPhone Max & Real App Loading: Added titanium SimPhone Max (instant 0s load). SimPhone loads in 3s, SimPhone Pro loads in 2s. All apps unlocked on all models!",
                "📶 WiFi Router & Age 18 Eviction: Under 18s live with parents with free home cooking and family WiFi. On your 18th birthday, you leave the nest and must acquire your own housing and set up a home WiFi router!",
                "🥗 Balanced Food Meter: Hunger decay softened so you don't starve too fast. Under 18s are 100% fed by parents automatically!",
                "📦 Net Worth & Appraisal Market: Total net worth tracking breakdown across liquid cash, physical assets, and corporate equity with instant item selling!",
                "🌟 30+ New Random Life Events: Tons of unexpected career twists, school adventures, neighbor drama, and lottery moments!"
            ),
            isBigUpdate = true
        ),
        UpdateLogEntry(
            version = "v2.3 - Market Category, SimBank Log, Multi-Jobs & Business Expansion",
            releaseDate = "October 2026",
            highlight = "Guaranteed Phone Market category, Bank statement logs, side jobs & categories, fancy supercars, business upgrades, and structured assets roadmap.",
            changes = listOf(
                "📱 Phone Always in Market: Removed all historical delays! SimPhone ($499) and SimPhone Pro ($999) have a dedicated top category in the Market available immediately.",
                "🏦 SimBank Transaction Statement Log: Full transaction history inside the SimBank app displaying all wires, debt payoffs, loans, and salary deposits with dates & amounts!",
                "💸 Wire to Any Contact: Wire money to mother, father, siblings, partners, friends, or custom recipients with relationship boost.",
                "💼 Job Categories & Side Hustles: Hold multiple jobs simultaneously! Browse jobs across 9 categories (Tech, Medicine, Finance, Trades, Service, Aviation, Creative, Executive, and Side Gigs).",
                "🏎️ Fancy Cars & Hypercars: Dealership expanded with Pagani Huayra ($3.2M), McLaren 720S ($395K), Rolls-Royce Phantom ($480K), Lamborghini Huracán ($280K), and BMW M4!",
                "🍋 Business Era (Lemonade Stand): Fund and upgrade your stand from a wooden table to Deluxe Canopy (+30% customer foot traffic), buy bulk supplies, and adjust cup prices!",
                "🥗 Food Hunger & Sustenance: Hunger meter drops each year. Buy groceries, meal boxes, or Michelin dinners to stay nourished and prevent starvation penalties!",
                "🚗 SimRider Uber Driving on Pro: Drive passengers for $25-$60 realistic fares with 5-star ratings, capped per year.",
                "🗺️ Structured Assets Roadmap: Planned feature to track cashflow statements, asset appreciation, and balance sheet capital."
            ),
            isBigUpdate = true
        ),
        UpdateLogEntry(
            version = "v2.2 - Business Era, Bank Debt, Uber Gig & Food System",
            releaseDate = "October 2026",
            highlight = "Lemonade Stand business, Bank debt repayment & transfers, SimPhone Pro Uber rideshare, food hunger meter, and exotic luxury supercars.",
            changes = listOf(
                "🍋 Business Era Begins: Start your very first entrepreneurial venture—a Lemonade Stand ($75)! Set cup prices, buy citrus supplies, and earn realistic yearly profit.",
                "🏦 SimBank Debt & Wire Transfers: Send money to any family/friend in the game and pay down loans/debt directly inside SimBank!",
                "🚗 SimRider (Uber App on SimPhone Pro): Drive passengers on your phone to earn realistic fares ($25-$60) with passenger ratings, capped at 5 trips/year!",
                "🥗 Food & Hunger System: Food decreases naturally each year. Purchase groceries, fast food, meal prep boxes, and Michelin dining in the market to stay healthy.",
                "🏎️ Supercars & Exotic Hypercars: Added Mercedes-Maybach SUV, Ferrari V12 Supercar, Bugatti Chiron, and 1967 Shelby GT500!",
                "💼 Categorized Job Market: Career listings organized by Tech, Medicine, Finance, Creative, Service, and Aviation with expanded salaries!",
                "📱 Guaranteed SimPhone in Market: SimPhones and Tech are permanently available in the Tech Store."
            ),
            isBigUpdate = true
        ),
        UpdateLogEntry(
            version = "v2.1 - Bug Fixes & Tech Store Expansion",
            releaseDate = "October 2026",
            highlight = "Fixed Age 0 bug, new Tech store category, SimBank transfers, and reverse/auto-scroll life feed.",
            changes = listOf(
                "🛠️ Fixed Age Progression: Resolved age update bug! Your character's age now advances seamlessly on every Age Up (+1 Year).",
                "💻 Dedicated Tech & Electronics Category: Added dedicated Tech store section in Assets to buy SimPhone ($499), SimPhone Pro ($999), Pro Laptops, and Smartwatches.",
                "📱 True Assets Tech Integration: SimPhone is cleanly accessed from your owned Assets without cluttering the top header.",
                "📜 Smart Life Feed: Reversed timeline and auto-scrolls directly to your newest events so you never have to scroll all the way back up.",
                "🏦 SimBank App: Transfer money to loved ones with 2-second wire transfer animation and 'Fine' confirmation popup.",
                "📰 Progressive News: 50 world news events now unlock chronologically year-by-year from 1991 to 2026."
            ),
            isBigUpdate = false
        ),
        UpdateLogEntry(
            version = "v2.0 - The Smartphone & Global Wealth Era [BIG UPDATE]",
            releaseDate = "October 2026",
            highlight = "In-game Smartphone with 50 News Articles, 100 Richest People Ladder, Birth Year Picker (1991-2026), and Local Currencies.",
            changes = listOf(
                "📱 In-Game Smartphone Suite: Access a functional phone right from Assets with the News App and Billionaire Ladder!",
                "📰 50 Pre-Built Global News Articles: Read major technological, cultural, and scientific world events spanning 2007 through 2026.",
                "🏆 100 Richest People Leaderboard: Real-world Forbes billionaires (Elon Musk, Jeff Bezos, Bernard Arnault). Watch your character climb the ladder!",
                "⏳ Choose Birth Year (1991 - 2026): Live through modern history! When the year 2007 arrives, a milestone event marks the dawn of the Smartphone Era.",
                "🌍 12 Real Countries & Currencies: Play in USA ($), UK (£), Europe (€), Japan (¥), Switzerland (Fr), UAE (AED), Australia (A$), etc. with localized currency formatting!",
                "⚡ Life Feed Exclusive Age Button: The Age Up button now resides cleanly on the Life Feed only, maximizing screen space on other tabs.",
                "✨ App Launch Sequence: Smooth animated splash screen transition into the game.",
                "🗺️ Future Roadmap & Settings Hub: Preview upcoming phases (Deep Customization Studio, Stock Markets, Romance & Dating) in the Settings Hub!"
            ),
            isBigUpdate = false
        ),
        UpdateLogEntry(
            version = "v1.2 - Age Safety & Client Expansion",
            releaseDate = "October 2026",
            highlight = "Strict age-gated events, freelance client contracts, and 2-step interactive decisions.",
            changes = listOf(
                "🎯 Age-Gating Fix: Explicit age boundaries prevent toddler/elder event cross-over (e.g. no 1-year-olds giving heirlooms).",
                "🤝 Client Gigs System: Take on freelance client contracts across tech, creative, and consulting fields!",
                "⚡ 2-Step Interactive Choices: Depth added to Gym, Doctor, Reading, Client Gigs, and Relationships.",
                "📱 Screen Fit Optimization: Streamlined header, compact stats, and refined layout for all mobile screens.",
                "📜 Live In-Game Update Log: Track newly added features and changelogs directly in-app!"
            )
        ),
        UpdateLogEntry(
            version = "v1.0 - Genesis Release",
            releaseDate = "September 2026",
            highlight = "Initial launch of LifeSim featuring the core +1 Year Age Up mechanic.",
            changes = listOf(
                "⏳ Signature Age Up engine with dynamic milestones.",
                "📊 4 Core Stats: Happiness, Health, Smarts, and Looks.",
                "🪦 Cemetery Hall of Fame with local Room Database persistence."
            )
        )
    )

    fun createRandomCharacter(
        customName: String? = null,
        customGender: Gender? = null,
        customCountry: String? = null,
        customBirthYear: Int? = null
    ): Character {
        val gender = customGender ?: if (Random.nextBoolean()) Gender.MALE else Gender.FEMALE
        val firstName = customName?.split(" ")?.firstOrNull() ?: if (gender == Gender.MALE) FIRST_NAMES_MALE.random() else FIRST_NAMES_FEMALE.random()
        val lastName = customName?.split(" ")?.getOrNull(1) ?: LAST_NAMES.random()
        
        val countryMatch = NewsAndRichDatabase.COUNTRIES.find { it.name.equals(customCountry, ignoreCase = true) } 
            ?: NewsAndRichDatabase.COUNTRIES.random()
        val country = countryMatch.name
        val city = countryMatch.cities.random()
        val currencySymbol = countryMatch.currencySymbol
        val currencyCode = countryMatch.currencyCode
        val zodiac = ZODIACS.random()
        val birthYear = (customBirthYear ?: 2000).coerceIn(1991, 2026)

        val character = Character(
            firstName = firstName,
            lastName = lastName,
            gender = gender,
            country = country,
            city = city,
            zodiac = zodiac,
            birthYear = birthYear,
            currencySymbol = currencySymbol,
            currencyCode = currencyCode,
            age = 0,
            happiness = Random.nextInt(70, 95),
            health = Random.nextInt(85, 100),
            smarts = Random.nextInt(45, 90),
            looks = Random.nextInt(40, 90),
            bankBalance = 0L,
            occupation = Occupation("Baby", "Crib", 0, 50, false),
            education = EducationLevel.NONE,
            degree = Degree.NONE
        )

        // Parents
        val momName = "${FIRST_NAMES_FEMALE.random()} $lastName"
        val dadName = "${FIRST_NAMES_MALE.random()} $lastName"
        character.relationships.add(Relationship(name = momName, role = RelationshipRole.MOTHER, meter = Random.nextInt(80, 100)))
        character.relationships.add(Relationship(name = dadName, role = RelationshipRole.FATHER, meter = Random.nextInt(80, 100)))

        // 50% chance of sibling
        if (Random.nextBoolean()) {
            val sibGender = if (Random.nextBoolean()) Gender.MALE else Gender.FEMALE
            val sibName = if (sibGender == Gender.MALE) "${FIRST_NAMES_MALE.random()} $lastName" else "${FIRST_NAMES_FEMALE.random()} $lastName"
            val sibRole = if (sibGender == Gender.MALE) RelationshipRole.BROTHER else RelationshipRole.SISTER
            character.relationships.add(Relationship(name = sibName, role = sibRole, meter = Random.nextInt(70, 95)))
        }

        return character
    }

    fun getInitialBirthLog(character: Character): LifeLogEntry {
        return LifeLogEntry(
            age = 0,
            title = "A New Life Begins!",
            description = "You were born in ${character.city}, ${character.country}. Your mother is ${character.relationships.firstOrNull { it.role == RelationshipRole.MOTHER }?.name ?: "Mom"} and your father is ${character.relationships.firstOrNull { it.role == RelationshipRole.FATHER }?.name ?: "Dad"}.",
            emoji = "👶",
            tag = LogTag.MILESTONE
        )
    }

    // Yearly narrative events when aging up
    fun generateYearlyEvents(character: Character): List<LifeLogEntry> {
        val entries = mutableListOf<LifeLogEntry>()
        val age = character.age

        // SimPhone announcement - available in the Tech Market
        if ((character.age >= 8 || character.currentYear >= 2000) && !character.phoneNotified) {
            character.phoneNotified = true
            entries.add(
                LifeLogEntry(
                    age = age,
                    title = "📱 Mobile Tech & SimPhone in Store!",
                    description = "Year ${character.currentYear}: The latest SimPhones are in stock! You can now visit the Phone market to buy the SimPhone ($499), SimPhone Pro ($999), or SimPhone Max ($1,499) with SimBank and SimRider Uber driving!",
                    emoji = "📱",
                    tag = LogTag.TECH,
                    happinessDelta = +10,
                    smartsDelta = +4
                )
            )
        }

        // Year 2003 PC Milestone
        if (character.currentYear >= 2003 && !character.pcNotified) {
            character.pcNotified = true
            entries.add(
                LifeLogEntry(
                    age = age,
                    title = "🖥️ The Personal Desktop PC Era (Year 2003)!",
                    description = "Year ${character.currentYear}: Desktop personal computers have officially arrived! You can now buy a Desktop PC Workstation in the store. Access PC mode to use desktop apps and play 5+ real retro mini-games on R-Team!",
                    emoji = "🖥️",
                    tag = LogTag.TECH,
                    happinessDelta = +14,
                    smartsDelta = +8
                )
            )
        }

        // Under 18: parents feed you completely!
        if (age < 18) {
            character.hunger = 100
            entries.add(
                LifeLogEntry(
                    age = age,
                    title = "🍲 Home-Cooked Family Meals",
                    description = "Your parents lovingly cooked healthy home dinners and breakfast for you all year. Hunger is fully refilled at 100%!",
                    emoji = "🍲",
                    tag = LogTag.HEALTH,
                    happinessDelta = +4
                )
            )
        }

        when (age) {
            1 -> entries.add(LifeLogEntry(age, "First Steps!", "You wobbled across the living room carpet and fell gently into your mother's arms.", "👣", LogTag.MILESTONE, happinessDelta = +5))
            2 -> entries.add(LifeLogEntry(age, "First Words!", "You pointed at the kitchen refrigerator and exclaimed your very first word: 'More!'", "🗣️", LogTag.MILESTONE, smartsDelta = +4))
            3 -> entries.add(LifeLogEntry(age, "Preschool Fun", "You made a colorful handprint painting with bright finger paint.", "🎨", LogTag.EDUCATION, happinessDelta = +4))
            4 -> entries.add(LifeLogEntry(age, "Bedtime Story", "Your parents read you a fairy tale book about brave dragons and stars.", "📚", LogTag.GENERAL, smartsDelta = +3))
            5 -> {
                character.education = EducationLevel.ELEMENTARY
                character.occupation = Occupation("Elementary Student", "Primary Academy", 0, 60, false)
                entries.add(LifeLogEntry(age, "Started Elementary School!", "You packed your backpack with colored crayons and met your kindergarten teacher.", "🎒", LogTag.MILESTONE, smartsDelta = +5, happinessDelta = +5))
            }
            6 -> entries.add(LifeLogEntry(age, "Lost First Tooth", "The tooth fairy visited and left a shiny dollar coin under your pillow.", "🦷", LogTag.GENERAL, happinessDelta = +3, moneyDelta = 1))
            7 -> entries.add(LifeLogEntry(age, "Rode a Bicycle", "Your dad took the training wheels off your bicycle and you rode down the driveway without falling!", "🚲", LogTag.HEALTH, healthDelta = +4, happinessDelta = +6))
            8 -> entries.add(LifeLogEntry(age, "Spelling Bee", "You competed in the school district spelling bee and spelled 'A-P-P-L-E-S-A-U-C-E' correctly.", "🏆", LogTag.EDUCATION, smartsDelta = +5))
            9 -> entries.add(LifeLogEntry(age, "Playground Adventures", "You and your classmates spent recess playing an epic game of tag on the jungle gym.", "🏃", LogTag.GENERAL, healthDelta = +3, happinessDelta = +4))
            10 -> entries.add(LifeLogEntry(age, "Double Digits!", "You celebrated turning 10 years old with pizza and a giant chocolate birthday cake.", "🎂", LogTag.MILESTONE, happinessDelta = +8))
            11 -> entries.add(LifeLogEntry(age, "Science Fair", "You built a baking soda volcano that erupted all over the science lab floor.", "🌋", LogTag.EDUCATION, smartsDelta = +6))
            12 -> entries.add(LifeLogEntry(age, "Middle School", "You joined the school middle school band and learned to play musical instruments.", "🎷", LogTag.EDUCATION, smartsDelta = +4, looksDelta = +2))
            13 -> entries.add(LifeLogEntry(age, "Officially a Teenager!", "You got braces and began feeling rebellious about cleaning your bedroom.", "🎸", LogTag.MILESTONE, happinessDelta = +3))
            14 -> entries.add(LifeLogEntry(age, "High School Freshmen", "You started high school! The lockers are tall and the hallways are bustling.", "🏫", LogTag.EDUCATION, smartsDelta = +5))
            15 -> entries.add(LifeLogEntry(age, "First Allowance", "Your parents started giving you a regular weekly allowance for chores.", "💵", LogTag.WEALTH, moneyDelta = 150))
            16 -> entries.add(LifeLogEntry(age, "Driver's License Passed!", "You passed the parallel parking test on your very first try! Freedom awaits.", "🚗", LogTag.MILESTONE, happinessDelta = +12, smartsDelta = +4))
            17 -> entries.add(LifeLogEntry(age, "High School Prom", "You dressed up in formal attire and danced the night away with classmates.", "💃", LogTag.RELATIONSHIP, happinessDelta = +10, looksDelta = +4))
            18 -> {
                character.education = EducationLevel.HIGH_SCHOOL
                entries.add(LifeLogEntry(age, "High School Graduation!", "You threw your cap into the air! You graduated high school.", "🎓", LogTag.MILESTONE, happinessDelta = +15, smartsDelta = +8))
                entries.add(LifeLogEntry(age, "📦 18th Birthday: Left the Nest!", "Your parents congratulated you on adulthood and packed your luggage: 'You're 18 now! Time to build your independent life!' You must now acquire housing and set up your own WiFi router for internet.", "🚪", LogTag.MILESTONE, happinessDelta = +6))
            }
            21 -> entries.add(LifeLogEntry(age, "21st Birthday Bash", "Celebrated your milestone 21st birthday with friends! You feel on top of the world.", "🥂", LogTag.MILESTONE, happinessDelta = +14))
            30 -> entries.add(LifeLogEntry(age, "The Big 3-0!", "You celebrated your 30th birthday. You feel mature, grounded, and ambitious.", "✨", LogTag.MILESTONE, happinessDelta = +8, smartsDelta = +3))
            40 -> entries.add(LifeLogEntry(age, "Fabulous 40s", "Entered your 40s with deep wisdom, seasoned experience, and refined taste.", "🥂", LogTag.MILESTONE, smartsDelta = +5))
            50 -> entries.add(LifeLogEntry(age, "Golden 50th Birthday", "Celebrated half a century of life surrounded by family and close lifelong companions.", "🌟", LogTag.MILESTONE, happinessDelta = +10))
            65 -> entries.add(LifeLogEntry(age, "Senior Citizenship & Retirement Age", "You reached official retirement age! Social security and pensions are now unlocked.", "🏖️", LogTag.MILESTONE, happinessDelta = +15))
            80 -> entries.add(LifeLogEntry(age, "Octogenarian Milestone", "80 years on this earth! You share timeless wisdom with the younger generations.", "📜", LogTag.MILESTONE, smartsDelta = +5))
            100 -> entries.add(LifeLogEntry(age, "CENTENARIAN! 100 Years Old!", "You received a presidential letter congratulating you on living a full century!", "🎊", LogTag.MILESTONE, happinessDelta = +25))
            else -> {
                entries.add(generateRandomYearlyOccurrence(character))
            }
        }

        return entries
    }

    private fun generateRandomYearlyOccurrence(character: Character): LifeLogEntry {
        val age = character.age
        val pool = when {
            age < 18 -> listOf(
                LifeLogEntry(age, "School Field Trip", "Visited the natural history museum and saw giant dinosaur fossils.", "🦕", LogTag.EDUCATION, smartsDelta = +3),
                LifeLogEntry(age, "Summer Vacation", "Spent two sunny weeks swimming at the lake and eating ice cream.", "🍦", LogTag.GENERAL, happinessDelta = +6, healthDelta = +3),
                LifeLogEntry(age, "Library Reading Challenge", "Read five thick mystery novels in a row during the winter break.", "📖", LogTag.EDUCATION, smartsDelta = +5),
                LifeLogEntry(age, "Sports Tournament", "Scored the winning goal for your school team in the championship match!", "⚽", LogTag.HEALTH, healthDelta = +5, happinessDelta = +8),
                LifeLogEntry(age, "Treehouse Construction", "Built an awesome wooden treehouse in the backyard with neighborhood pals.", "🪵", LogTag.GENERAL, happinessDelta = +8, smartsDelta = +3),
                LifeLogEntry(age, "Arcade High Score", "Set the all-time number one high score on the arcade pinball machine at the mall!", "👾", LogTag.GENERAL, happinessDelta = +10, smartsDelta = +3),
                LifeLogEntry(age, "Math Olympiad Bronze", "Placed 3rd in the regional youth mathematics tournament!", "📐", LogTag.EDUCATION, smartsDelta = +8, happinessDelta = +4),
                LifeLogEntry(age, "Adopted a Stray Kitten", "Found a gentle purring kitten on the front porch and convinced parents to give it milk.", "🐱", LogTag.RELATIONSHIP, happinessDelta = +9),
                LifeLogEntry(age, "Campfire Ghost Stories", "Roasted marshmallows over a campfire and told spooky stories late into the night.", "🔥", LogTag.GENERAL, happinessDelta = +6),
                LifeLogEntry(age, "Garage Band Jam Session", "Played loud drums in the garage with your friends until the neighbors knocked.", "🥁", LogTag.GENERAL, happinessDelta = +7, looksDelta = +2),
                LifeLogEntry(age, "Skateboard Trick Mastered", "Landed an ollie down a 4-stair concrete set without skinning your knees.", "🛹", LogTag.HEALTH, healthDelta = +3, looksDelta = +3, happinessDelta = +7),
                LifeLogEntry(age, "Comic Book Collection", "Traded vintage superhero issues at the local comic shop and found a rare mint foil edition.", "🦸", LogTag.GENERAL, happinessDelta = +5, moneyDelta = 25)
            )
            age < 65 -> listOf(
                LifeLogEntry(age, "Healthy Habit", "Started drinking green smoothies and jogging three mornings a week.", "🥗", LogTag.HEALTH, healthDelta = +6, looksDelta = +3),
                LifeLogEntry(age, "Weekend Getaway", "Took a scenic road trip along the coastal highway with great music.", "🛣️", LogTag.GENERAL, happinessDelta = +7),
                LifeLogEntry(age, "Home Improvement", "Repainted your living room and organized your bookshelf.", "🛋️", LogTag.GENERAL, happinessDelta = +4),
                LifeLogEntry(age, "Tax Return Bonus", "The revenue agency sent you a nice unexpected tax refund.", "💰", LogTag.WEALTH, moneyDelta = Random.nextLong(400, 1500)),
                LifeLogEntry(age, "Cooked Gourmet Dinner", "Mastered a delicate French soufflé recipe from scratch without burning it.", "🍳", LogTag.GENERAL, happinessDelta = +5, smartsDelta = +2),
                LifeLogEntry(age, "Gym Transformation", "Hit a personal weightlifting record at the fitness club.", "💪", LogTag.HEALTH, healthDelta = +5, looksDelta = +4),
                LifeLogEntry(age, "Stock Market Dividend", "A blue-chip investment company paid out unexpected shareholder dividends.", "📈", LogTag.WEALTH, moneyDelta = Random.nextLong(350, 1800)),
                LifeLogEntry(age, "Viral Social Post", "A funny picture you took went viral online, netting you thousands of shares!", "📱", LogTag.GENERAL, happinessDelta = +12, looksDelta = +3),
                LifeLogEntry(age, "Neighborhood Block Party", "Organized a summer barbecue on your street with grilled burgers and live music.", "🍔", LogTag.RELATIONSHIP, happinessDelta = +9),
                LifeLogEntry(age, "Stargazing Meteor Shower", "Drove out to the dark countryside and watched 50 shooting stars streak across the milky way.", "🌠", LogTag.GENERAL, happinessDelta = +8, smartsDelta = +2),
                LifeLogEntry(age, "Marathon Finisher", "Trained for 6 months and completed a 26-mile marathon! What an incredible triumph.", "🏅", LogTag.HEALTH, healthDelta = +12, happinessDelta = +15),
                LifeLogEntry(age, "Antique Find at Flea Market", "Bought a vintage dusty oil painting for $20 that turned out to be an authentic collectible!", "🖼️", LogTag.WEALTH, moneyDelta = 850, happinessDelta = +8),
                LifeLogEntry(age, "Concert in the Park", "Listened to an acoustic orchestra concert beneath glowing twilight lanterns.", "🎻", LogTag.GENERAL, happinessDelta = +8),
                LifeLogEntry(age, "Wine Tasting Masterclass", "Learned how to detect oak, berry, and spice notes from a master sommelier.", "🍷", LogTag.GENERAL, happinessDelta = +6, smartsDelta = +3),
                LifeLogEntry(age, "Adopted Golden Retriever", "Brought home an affectionate puppy that instantly greeted you with tail wags.", "🐕", LogTag.RELATIONSHIP, happinessDelta = +14),
                LifeLogEntry(age, "Spontaneous Beach Day", "Spent a breezy Saturday riding ocean waves and reading on a hammock.", "🏖️", LogTag.GENERAL, happinessDelta = +9, healthDelta = +4),
                LifeLogEntry(age, "Won Local Trivia Night", "Led your pub trivia team to victory by knowing the capital city of Madagascar!", "🧠", LogTag.EDUCATION, smartsDelta = +6, moneyDelta = 150)
            )
            else -> listOf(
                LifeLogEntry(age, "Gardening Season", "Your backyard heirloom tomatoes and hydrangeas bloomed beautifully.", "🌻", LogTag.GENERAL, happinessDelta = +6),
                LifeLogEntry(age, "Bingo Night Winner", "Shouted 'BINGO!' at the community recreation center and won a $100 gift basket.", "🎯", LogTag.GENERAL, happinessDelta = +8, moneyDelta = 100),
                LifeLogEntry(age, "Family Reunion", "Hosted a lively weekend barbecue where your relatives shared funny memories.", "👨‍👩‍👧‍👦", LogTag.RELATIONSHIP, happinessDelta = +12),
                LifeLogEntry(age, "Peaceful Walk in the Park", "Fed ducks by the willow pond on a crisp autumn morning.", "🦆", LogTag.HEALTH, healthDelta = +3, happinessDelta = +5),
                LifeLogEntry(age, "Grandchildren Storytime", "Gathered the young ones on your lap and recounted adventurous tales from your youth.", "📖", LogTag.RELATIONSHIP, happinessDelta = +15),
                LifeLogEntry(age, "Golden Memories Album", "Scanned old family photographs into a leather album with handwritten notes.", "📷", LogTag.GENERAL, happinessDelta = +9),
                LifeLogEntry(age, "Morning Tai Chi", "Practiced gentle balance exercises in the garden at sunrise.", "🧘", LogTag.HEALTH, healthDelta = +6, happinessDelta = +5),
                LifeLogEntry(age, "Chess Tournament Champion", "Checkmated the reigning senior chess club champion with a brilliant knight fork!", "♟️", LogTag.EDUCATION, smartsDelta = +7, happinessDelta = +10)
            )
        }
        return pool.random()
    }

    fun getCollegeDecisionEvent(): InteractiveEvent {
        return InteractiveEvent(
            title = "What's Your Next Chapter?",
            description = "You've officially graduated from high school! How will you invest in your future?",
            emoji = "🎓",
            minAge = 18,
            maxAge = 18,
            choices = listOf(
                InteractiveChoice(
                    text = "Apply for University (Computer Science)",
                    emoji = "💻",
                    smartsDelta = +10,
                    moneyDelta = -5000,
                    outcomeNarrative = "Accepted into the School of Computing! Tuition was $5,000 but your tech career potential skyrocketed."
                ),
                InteractiveChoice(
                    text = "Apply for University (Medicine & Biology)",
                    emoji = "🩺",
                    smartsDelta = +15,
                    moneyDelta = -8000,
                    outcomeNarrative = "Accepted into Pre-Med! Long hours of studying anatomy and chemistry begin."
                ),
                InteractiveChoice(
                    text = "Apply for University (Business & Finance)",
                    emoji = "📈",
                    smartsDelta = +8,
                    moneyDelta = -5000,
                    outcomeNarrative = "Accepted into the Business School! You learned corporate strategy and financial markets."
                ),
                InteractiveChoice(
                    text = "Start Working Immediately",
                    emoji = "💼",
                    happinessDelta = +5,
                    moneyDelta = +1000,
                    outcomeNarrative = "You skipped college debt and jumped straight into the workforce to earn independent cash!"
                )
            )
        )
    }

    // ALL DILEMMAS WITH STRICT AGE BOUNDS
    private val ALL_DILEMMAS: List<InteractiveEvent> = listOf(
        // === AGE 0 to 4 (Toddler / Baby) ===
        InteractiveEvent(
            title = "Vegetable Revolt at Dinner",
            description = "Your parents present a steaming spoonful of puréed green broccoli.",
            emoji = "🥦",
            minAge = 1,
            maxAge = 4,
            choices = listOf(
                InteractiveChoice("Blow raspberries and spit it on the wall", "😜", happinessDelta = +5, looksDelta = -2, outcomeNarrative = "You splattered broccoli all over dad's shirt! You giggled hysterically."),
                InteractiveChoice("Swallow it bravely like a big kid", "😋", healthDelta = +5, smartsDelta = +2, outcomeNarrative = "Your mother cheered and awarded you a shiny star sticker!"),
                InteractiveChoice("Drop the spoon on the floor repeatedly", "🥄", smartsDelta = +3, happinessDelta = +3, outcomeNarrative = "You tested Newton's law of gravity 6 times in a row.")
            )
        ),
        InteractiveEvent(
            title = "Crayon Masterpiece on Living Room Wall",
            description = "You discovered an unguarded pack of wax crayons next to a pristine white wall.",
            emoji = "🖍️",
            minAge = 2,
            maxAge = 4,
            choices = listOf(
                InteractiveChoice("Draw a giant purple giraffe across the wallpaper", "🦒", happinessDelta = +8, smartsDelta = +2, outcomeNarrative = "You created abstract art! Your parents sighed and bought stain remover."),
                InteractiveChoice("Draw neatly on scrap paper", "📄", smartsDelta = +5, happinessDelta = +3, outcomeNarrative = "Your parents taped your drawing proudly onto the refrigerator."),
                InteractiveChoice("Take a nibble of the red crayon", "🍽️", healthDelta = -2, outcomeNarrative = "It tasted like waxy disappointment. Lesson learned.")
            )
        ),
        InteractiveEvent(
            title = "Sandbox Toy Dispute",
            description = "Another toddler in the playground sandbox grabs your bright yellow dump truck!",
            emoji = "🏖️",
            minAge = 2,
            maxAge = 5,
            choices = listOf(
                InteractiveChoice("Throw sand and tug the truck back", "🏖️", healthDelta = -3, happinessDelta = +4, outcomeNarrative = "You secured the dump truck, but both of you ended up with sand in your shoes!"),
                InteractiveChoice("Offer to share and build a sandcastle together", "🏰", happinessDelta = +8, smartsDelta = +4, outcomeNarrative = "You made a new preschool playmate! You built a huge moat."),
                InteractiveChoice("Waddle away and cry to your parents", "😭", happinessDelta = -4, outcomeNarrative = "Mom gave you comforting hugs and a juice box.")
            )
        ),

        // === AGE 5 to 12 (Elementary / Child) ===
        InteractiveEvent(
            title = "Playground Bully Confrontation",
            description = "A bigger kid named Buster demands your chocolate pudding cup at lunch!",
            emoji = "🥊",
            minAge = 6,
            maxAge = 12,
            choices = listOf(
                InteractiveChoice("Stand firm and refuse to give it up", "💥", happinessDelta = +8, looksDelta = +2, outcomeNarrative = "You stared him down! Buster backed away and the whole lunch table cheered."),
                InteractiveChoice("Report Buster to the playground supervisor", "👨‍🏫", smartsDelta = +5, outcomeNarrative = "The supervisor intervened and Buster lost his recess privileges."),
                InteractiveChoice("Trade the pudding for a baseball card", "🃏", smartsDelta = +4, happinessDelta = +2, outcomeNarrative = "You turned conflict into a collectible trade deal.")
            )
        ),
        InteractiveEvent(
            title = "Found a Stray Puppy Under the Porch",
            description = "A scruffy little terrier puppy is wagging its tail shivering outside your door.",
            emoji = "🐶",
            minAge = 6,
            maxAge = 12,
            choices = listOf(
                InteractiveChoice("Beg your parents to adopt the puppy", "🐾", happinessDelta = +15, outcomeNarrative = "Your parents agreed! The puppy was named 'Lucky' and became your loyal pal."),
                InteractiveChoice("Take it to the local pet shelter to find its owner", "🏡", happinessDelta = +6, smartsDelta = +4, outcomeNarrative = "The microchip found the frantic owner, who rewarded you with cookies!"),
                InteractiveChoice("Feed it a slice of turkey and leave it outside", "🥪", happinessDelta = +2, outcomeNarrative = "The puppy ate happily and trotted along the neighborhood.")
            )
        ),
        InteractiveEvent(
            title = "School District Science Bee",
            description = "You qualified for the final round of the district elementary science bee!",
            emoji = "🔬",
            minAge = 8,
            maxAge = 12,
            choices = listOf(
                InteractiveChoice("Study astronomy and physics flashcards all weekend", "🌌", smartsDelta = +10, happinessDelta = +6, outcomeNarrative = "You won 1st Place Trophy and a microscope! Your teachers are thrilled."),
                InteractiveChoice("Wing it on common knowledge", "🧠", smartsDelta = +2, outcomeNarrative = "You finished in respectable 4th place and received an honorable ribbon.")
            )
        ),

        // === AGE 13 to 17 (Teenagers) ===
        InteractiveEvent(
            title = "High School Crush Opportunity",
            description = "Your biggest crush since freshman year is sitting right across from you at the library.",
            emoji = "💌",
            minAge = 13,
            maxAge = 17,
            choices = listOf(
                InteractiveChoice("Ask them out for boba smoothies", "🧋", happinessDelta = +15, looksDelta = +4, outcomeNarrative = "They smiled warmly and said YES! You spent the whole evening laughing together."),
                InteractiveChoice("Slip a thoughtful encouraging note into their notebook", "📝", smartsDelta = +3, happinessDelta = +5, outcomeNarrative = "They read it and waved across the library with a glowing smile."),
                InteractiveChoice("Panic, pretend to be deeply studying calculus", "📚", smartsDelta = +4, happinessDelta = -4, outcomeNarrative = "You understood integrals perfectly, but left your heart unsaid.")
            )
        ),
        InteractiveEvent(
            title = "Cheating on the Midterm Exam",
            description = "A classmate hands you a folded cheat sheet right before the final History test.",
            emoji = "📝",
            minAge = 13,
            maxAge = 17,
            choices = listOf(
                InteractiveChoice("Refuse and rely on honest studying", "📖", smartsDelta = +8, happinessDelta = +4, outcomeNarrative = "You earned an honest B+ and kept your academic record crystal clean."),
                InteractiveChoice("Peep at the answers during the exam", "👀", smartsDelta = -4, happinessDelta = -6, outcomeNarrative = "The teacher noticed your wandering eyes and gave you Saturday detention!"),
                InteractiveChoice("Report the cheat sheet to the teacher", "✋", smartsDelta = +3, outcomeNarrative = "The teacher thanked you for protecting academic integrity.")
            )
        ),
        InteractiveEvent(
            title = "High School Garage Band Tryout",
            description = "A group of popular classmates are looking for a new lead guitarist or bassist.",
            emoji = "🎸",
            minAge = 14,
            maxAge = 17,
            choices = listOf(
                InteractiveChoice("Audition with an energetic rock riff", "⚡", looksDelta = +6, happinessDelta = +10, outcomeNarrative = "You nailed the solo! You were voted the band's new lead guitarist!"),
                InteractiveChoice("Offer to be their sound engineer & manager", "🎛️", smartsDelta = +6, moneyDelta = +100, outcomeNarrative = "You organized their gigs and pocketed a cut of the weekend ticket sales!")
            )
        ),

        // === AGE 18 to 59 (Adults) ===
        InteractiveEvent(
            title = "Designer Leather Wallet on Sidewalk",
            description = "You spot an expensive leather wallet on the sidewalk containing $600 cash and an ID.",
            emoji = "👛",
            minAge = 18,
            maxAge = 59,
            choices = listOf(
                InteractiveChoice("Mail it back intact to the address on the ID", "📬", happinessDelta = +12, smartsDelta = +3, outcomeNarrative = "The grateful owner sent a heartfelt thank-you card along with a $100 reward!"),
                InteractiveChoice("Pocket the cash and discard the wallet", "💵", happinessDelta = -8, moneyDelta = +600, outcomeNarrative = "You took the cash, but you feel a lingering twinge of conscience."),
                InteractiveChoice("Turn it over to the nearest police precinct", "👮", happinessDelta = +6, outcomeNarrative = "The desk sergeant filed an official lost property report.")
            )
        ),
        InteractiveEvent(
            title = "Early Stage Tech Startup Equity Pitch",
            description = "An energetic former colleague pitches an angel round in an autonomous drone startup.",
            emoji = "🤖",
            minAge = 22,
            maxAge = 59,
            choices = listOf(
                InteractiveChoice("Invest $1,500 personal savings", "💎", moneyDelta = if (Random.nextBoolean()) +5000 else -1500, happinessDelta = +6, outcomeNarrative = "You invested $1,500! The drone company patented their prototype successfully!"),
                InteractiveChoice("Politely decline and preserve your cash", "🤝", smartsDelta = +2, outcomeNarrative = "You kept your capital safe and sound in high-yield savings.")
            )
        ),
        InteractiveEvent(
            title = "Corporate Workplace Restructuring",
            description = "Your company is reorganizing departments. A senior director asks for your strategic opinion.",
            emoji = "📊",
            minAge = 22,
            maxAge = 59,
            choices = listOf(
                InteractiveChoice("Present a comprehensive data-backed efficiency plan", "📑", smartsDelta = +8, happinessDelta = +8, moneyDelta = +2000, outcomeNarrative = "Executive management was blown away! You received a performance bonus."),
                InteractiveChoice("Keep a low profile and avoid office politics", "🧘", happinessDelta = +2, outcomeNarrative = "You smoothly weathered the restructuring without making enemies.")
            )
        ),

        // === AGE 60+ (Seniors & Elders) ===
        InteractiveEvent(
            title = "Antique Roadshow Discovery",
            description = "While organizing your attic trunk, you discover an authentic 19th-century Swiss pocket watch.",
            emoji = "🕰️",
            minAge = 60,
            maxAge = 120,
            choices = listOf(
                InteractiveChoice("Gift it to your grandchild as an heirloom", "🎁", happinessDelta = +18, outcomeNarrative = "Your grandchild hugged you tightly with tears of joy, promising to cherish it forever."),
                InteractiveChoice("Have it appraised and sell to an antique collector", "🔍", moneyDelta = +3200, happinessDelta = +8, outcomeNarrative = "The collector paid $3,200 on the spot for the rare horological piece!")
            )
        ),
        InteractiveEvent(
            title = "Senior Community Master Gardener Exhibition",
            description = "The botanical conservatory invites you to submit your heirloom rose garden to the festival.",
            emoji = "🌹",
            minAge = 60,
            maxAge = 120,
            choices = listOf(
                InteractiveChoice("Showcase your prized hybrid tea roses", "🥇", happinessDelta = +14, healthDelta = +4, outcomeNarrative = "Your roses took First Place Blue Ribbon! Visitors marveled at the fragrant blooms."),
                InteractiveChoice("Host an afternoon tea garden party for lifelong friends", "🫖", happinessDelta = +16, outcomeNarrative = "You shared warm tea, home-baked scones, and golden memories in the afternoon breeze.")
            )
        )
    )

    // Guaranteed strictly age-checked dilemma
    fun getRandomDilemma(character: Character): InteractiveEvent? {
        val age = character.age
        // 45% chance of dilemma
        if (Random.nextFloat() > 0.45f) return null

        val eligible = ALL_DILEMMAS.filter { age in it.minAge..it.maxAge }
        return if (eligible.isNotEmpty()) eligible.random() else null
    }

    // 2-Step Action definitions
    fun getGymAction(): TwoStepActionData {
        return TwoStepActionData(
            title = "Select Workout Program",
            subtitle = "Choose an exercise routine tailored to your physical goals.",
            emoji = "💪",
            category = "Health & Wellness",
            options = listOf(
                TwoStepChoiceOption(
                    title = "High-Intensity Cardio & Sprinting",
                    subtitle = "Treadmill, rowing machine, and interval sprints.",
                    cost = 25L,
                    emoji = "🏃",
                    healthDelta = +10,
                    happinessDelta = +5,
                    looksDelta = +3,
                    resultTitle = "Endurance Boost!",
                    resultNarrative = "You ran 5 miles and burned 600 calories. Your cardiovascular health improved significantly!"
                ),
                TwoStepChoiceOption(
                    title = "Heavy Powerlifting & Bodybuilding",
                    subtitle = "Bench press, barbell squats, and deadlifts.",
                    cost = 25L,
                    emoji = "🏋️",
                    healthDelta = +8,
                    looksDelta = +8,
                    happinessDelta = +4,
                    resultTitle = "Muscle Hypertrophy!",
                    resultNarrative = "You set a new personal record on bench press! Your physique is looking notably more defined."
                ),
                TwoStepChoiceOption(
                    title = "Vinyasa Yoga & Mobility",
                    subtitle = "Deep stretching, core balance, and breathwork.",
                    cost = 20L,
                    emoji = "🧘",
                    healthDelta = +6,
                    happinessDelta = +12,
                    looksDelta = +4,
                    resultTitle = "Zen & Flexible!",
                    resultNarrative = "You aligned your body, eliminated back pain, and emerged from the studio with profound peace of mind."
                )
            )
        )
    }

    fun getDoctorAction(): TwoStepActionData {
        return TwoStepActionData(
            title = "Medical Consultation Protocol",
            subtitle = "Select medical care with your attending physician.",
            emoji = "🩺",
            category = "Healthcare",
            options = listOf(
                TwoStepChoiceOption(
                    title = "Full Diagnostic Executive Checkup",
                    subtitle = "Complete blood panel, ECG, vitals, and physician review.",
                    cost = 150L,
                    emoji = "📋",
                    healthDelta = +22,
                    happinessDelta = +4,
                    resultTitle = "Clean Bill of Health!",
                    resultNarrative = "Your cholesterol, blood pressure, and resting heart rate are textbook ideal. The doctor praised your lifestyle."
                ),
                TwoStepChoiceOption(
                    title = "Seasonal Immunity Booster & Vaccines",
                    subtitle = "Preventative vaccines and targeted vitamin shots.",
                    cost = 60L,
                    emoji = "💉",
                    healthDelta = +12,
                    happinessDelta = +2,
                    resultTitle = "Immunity Fortified!",
                    resultNarrative = "Your immune system is primed against seasonal viruses and fatigue for the upcoming year."
                ),
                TwoStepChoiceOption(
                    title = "Acupuncture & Physical Therapy",
                    subtitle = "Targeted tension relief for joints and muscles.",
                    cost = 110L,
                    emoji = "💆",
                    healthDelta = +15,
                    happinessDelta = +10,
                    resultTitle = "Pain Relief Achieved!",
                    resultNarrative = "Chronic neck and back tightness vanished under expert physical therapy."
                )
            )
        )
    }

    fun getReadingAction(): TwoStepActionData {
        return TwoStepActionData(
            title = "Choose Literary Focus",
            subtitle = "Select which subject or genre to read.",
            emoji = "📖",
            category = "Education",
            options = listOf(
                TwoStepChoiceOption(
                    title = "Computer Science & Applied AI Systems",
                    subtitle = "Deep learning architectures and algorithms.",
                    cost = 0L,
                    emoji = "💻",
                    smartsDelta = +10,
                    happinessDelta = +3,
                    resultTitle = "Mastered System Design!",
                    resultNarrative = "You digested 400 pages of advanced software engineering patterns. Your technical intellect sharpened!"
                ),
                TwoStepChoiceOption(
                    title = "Bestselling Suspense Mystery Novel",
                    subtitle = "A gripping page-turner of crime and deduction.",
                    cost = 0L,
                    emoji = "🔍",
                    smartsDelta = +5,
                    happinessDelta = +10,
                    resultTitle = "Solved the Whodunit!",
                    resultNarrative = "You couldn't put the book down until 2 AM! The shocking twist ending left you thrilled."
                ),
                TwoStepChoiceOption(
                    title = "Economics & Financial Market Mastery",
                    subtitle = "Value investing, macroeconomics, and wealth creation.",
                    cost = 0L,
                    emoji = "📈",
                    smartsDelta = +8,
                    happinessDelta = +5,
                    resultTitle = "Market Savvy!",
                    resultNarrative = "You gained valuable insights into monetary policy, inflation hedging, and asset allocation."
                )
            )
        )
    }

    fun getRelationshipAction(relation: Relationship): TwoStepActionData {
        return TwoStepActionData(
            title = "Interact with ${relation.name}",
            subtitle = "Choose how to spend meaningful time with your ${relation.role.title}.",
            emoji = relation.role.icon,
            category = "Social Connection",
            options = listOf(
                TwoStepChoiceOption(
                    title = "Cozy Cafe Catch-Up & Pastries",
                    subtitle = "Sip warm lattes and chat about everyday memories.",
                    cost = 15L,
                    emoji = "☕",
                    happinessDelta = +10,
                    resultTitle = "Warm Memories Shared!",
                    resultNarrative = "You spent an hour talking over warm espresso. ${relation.name} felt loved and appreciated."
                ),
                TwoStepChoiceOption(
                    title = "Deep Heart-to-Heart Conversation",
                    subtitle = "Open up about life goals, challenges, and support.",
                    cost = 0L,
                    emoji = "💬",
                    happinessDelta = +8,
                    smartsDelta = +3,
                    resultTitle = "Unbreakable Bond!",
                    resultNarrative = "You listened to each other with empathy. Your relationship reached a new level of trust."
                ),
                TwoStepChoiceOption(
                    title = "Thoughtful Surprise Gift",
                    subtitle = "Handpick a personalized gift they've been eyeing.",
                    cost = 100L,
                    emoji = "🎁",
                    happinessDelta = +15,
                    resultTitle = "Overjoyed Surprise!",
                    resultNarrative = "${relation.name} gasped when unwrapping the present! Their eyes lit up with delight."
                ),
                TwoStepChoiceOption(
                    title = "Theme Park & Roller Coasters",
                    subtitle = "Ride fast loops and share funnel cake together.",
                    cost = 75L,
                    emoji = "🎢",
                    happinessDelta = +18,
                    healthDelta = +3,
                    resultTitle = "Thrills & Laughter!",
                    resultNarrative = "You both screamed on the mega-coaster and took funny souvenir photos!"
                ),
                TwoStepChoiceOption(
                    title = "Scenic Weekend Road Trip & Camping",
                    subtitle = "Drive out to mountain trails and camp under the stars.",
                    cost = 120L,
                    emoji = "🏕️",
                    happinessDelta = +22,
                    healthDelta = +5,
                    resultTitle = "Starlit Memories!",
                    resultNarrative = "You roasted marshmallows by the campfire and talked late into the night."
                ),
                TwoStepChoiceOption(
                    title = "Wire Transfer Financial Gift via SimBank",
                    subtitle = "Send a caring bank transfer to support them.",
                    cost = 250L,
                    emoji = "💸",
                    happinessDelta = +25,
                    resultTitle = "Generous Support Given!",
                    resultNarrative = "${relation.name} received your wire transfer with immense gratitude and relief!"
                )
            )
        )
    }

    // EXPANDED CLIENT GIGS (Freelance & Client Work)
    val CLIENT_GIGS: List<ClientGig> = listOf(
        ClientGig(
            id = "client_dog_walk",
            title = "Dog Walking & Pet Sitting",
            clientName = "Mrs. Gable (Neighbor)",
            clientType = "Neighborhood Client",
            emoji = "🐕",
            basePayout = 90L,
            minAge = 14,
            minSmarts = 20,
            description = "Walk two energetic Golden Retrievers around the neighborhood park for 2 hours.",
            approaches = listOf(
                ClientGigApproach(
                    title = "Take them on an active park run",
                    description = "High energy workout that burns their energy.",
                    successChance = 0.90f,
                    successRewardMultiplier = 1.2f,
                    happinessDelta = +8,
                    healthDelta = +5,
                    successNarrative = "The dogs came home happily tired and clean! Mrs. Gable tipped you $20 extra!",
                    failNarrative = "One dog chased a squirrel into the mud. You spent an hour washing him."
                ),
                ClientGigApproach(
                    title = "Casual sidewalk stroll",
                    description = "Relaxed pace with frequent sniffing stops.",
                    successChance = 0.95f,
                    successRewardMultiplier = 1.0f,
                    happinessDelta = +5,
                    successNarrative = "A smooth and pleasant walk. Client paid promptly in cash.",
                    failNarrative = "The walk was slow but completed without incident."
                )
            )
        ),
        ClientGig(
            id = "client_web_dev",
            title = "Responsive Website for Bakery",
            clientName = "La Petite Croissant Bakery",
            clientType = "Small Business Client",
            emoji = "🥐",
            basePayout = 850L,
            minAge = 18,
            minSmarts = 50,
            description = "Build a modern menu and online ordering landing page for an artisanal bakery.",
            approaches = listOf(
                ClientGigApproach(
                    title = "Deliver a custom animated UI design",
                    description = "Spend extra time creating bespoke animations and fast loading times.",
                    successChance = 0.85f,
                    successRewardMultiplier = 1.35f,
                    happinessDelta = +12,
                    successNarrative = "The bakery owners were ecstatic! Online orders increased 40% and they paid a 35% performance bonus!",
                    failNarrative = "A checkout bug slipped through on launch day. You fixed it, but the client was annoyed."
                ),
                ClientGigApproach(
                    title = "Use a clean reliable template",
                    description = "Deliver quickly within 48 hours.",
                    successChance = 0.95f,
                    successRewardMultiplier = 1.0f,
                    happinessDelta = +6,
                    successNarrative = "Delivered ahead of schedule. The client paid the contract in full.",
                    failNarrative = "The client asked for two minor revisions before releasing payment."
                )
            )
        ),
        ClientGig(
            id = "client_mobile_app",
            title = "Mobile App Bug Bounty & Refactor",
            clientName = "Veloce FinTech Inc.",
            clientType = "Corporate Tech Client",
            emoji = "📱",
            basePayout = 2400L,
            minAge = 20,
            minSmarts = 70,
            description = "Diagnose memory leaks and refactor legacy database queries for a payment processing app.",
            approaches = listOf(
                ClientGigApproach(
                    title = "Write automated regression suites & optimize Room queries",
                    description = "Exhaustive testing and zero downtime migration.",
                    successChance = 0.88f,
                    successRewardMultiplier = 1.25f,
                    happinessDelta = +15,
                    healthDelta = -2,
                    successNarrative = "App query speed increased by 300%! Veloce signed you on an ongoing quarterly retainer.",
                    failNarrative = "A merge conflict delayed production deployment by 12 hours."
                ),
                ClientGigApproach(
                    title = "Patch only the critical hotspots",
                    description = "Focus on the primary bottleneck first.",
                    successChance = 0.92f,
                    successRewardMultiplier = 1.0f,
                    happinessDelta = +8,
                    successNarrative = "Hotspots resolved. The client was satisfied and disbursed the invoice.",
                    failNarrative = "Secondary logs still showed minor warnings, requiring extra review."
                )
            )
        ),
        ClientGig(
            id = "client_photo_shoot",
            title = "Portrait & Product Photography",
            clientName = "Aura Skincare Brand",
            clientType = "Commercial Creative Client",
            emoji = "📸",
            basePayout = 1200L,
            minAge = 18,
            minSmarts = 45,
            description = "Shoot high-end product bottles and model portraits for a national magazine launch.",
            approaches = listOf(
                ClientGigApproach(
                    title = "Studio lighting with dramatic macro lenses",
                    description = "Flawless color grading and cinematic shadows.",
                    successChance = 0.86f,
                    successRewardMultiplier = 1.3f,
                    happinessDelta = +14,
                    successNarrative = "The creative director featured your photos on the magazine front cover! Generous bonus paid.",
                    failNarrative = "Reflections on the glass bottles required hours of tedious retouching."
                ),
                ClientGigApproach(
                    title = "Natural softbox lighting setup",
                    description = "Crisp, clean commercial look.",
                    successChance = 0.94f,
                    successRewardMultiplier = 1.0f,
                    happinessDelta = +8,
                    successNarrative = "Client loved the clean aesthetic. Full fee transferred.",
                    failNarrative = "One photo angle had to be reshot, slightly delaying delivery."
                )
            )
        )
    )

    // JOBS
    val AVAILABLE_JOBS = listOf(
        JobListing(
            id = "job_fast_food",
            title = "Fast Food Crew",
            company = "Burger Kingpin",
            emoji = "🍔",
            salary = 24000L,
            minAge = 16,
            minSmarts = 20,
            requiredDegree = Degree.NONE,
            interviewQuestion = "What would you do if a customer complains their fries aren't crispy enough?",
            interviewChoices = listOf(
                JobInterviewChoice("Apologize politely and fry a fresh piping-hot batch", true, "The manager was impressed by your customer-first attitude! Hired!"),
                JobInterviewChoice("Tell them they ordered 20 minutes ago and it's their fault", false, "The manager shook their head in disbelief. Application rejected.")
            )
        ),
        JobListing(
            id = "job_barista",
            title = "Specialty Barista",
            company = "Bean & Leaf Cafe",
            emoji = "☕",
            salary = 32000L,
            minAge = 18,
            minSmarts = 30,
            category = "Service & Retail",
            requiredDegree = Degree.NONE,
            interviewQuestion = "How do you handle peak rush hour when fifteen orders queue up at once?",
            interviewChoices = listOf(
                JobInterviewChoice("Stay calm, prioritize workflow, and smile at waiting guests", true, "Perfect temperament for our cafe! You're hired!"),
                JobInterviewChoice("Panic and run to the breakroom to hide", false, "Not quite the hospitality mindset we need.")
            )
        ),
        JobListing(
            id = "job_graphic_designer",
            title = "Junior Graphic Designer",
            company = "PixelCraft Studios",
            emoji = "🎨",
            salary = 52000L,
            minAge = 18,
            minSmarts = 50,
            category = "Creative & Media",
            requiredDegree = Degree.NONE,
            interviewQuestion = "A client asks to 'make the logo bigger and pop more'. How do you respond?",
            interviewChoices = listOf(
                JobInterviewChoice("Balance visual hierarchy while providing two polished layout options", true, "Great creative problem solver! You got the job!"),
                JobInterviewChoice("Refuse because clients don't understand real art", false, "Arrogance won't work with our clients.")
            )
        ),
        JobListing(
            id = "job_software_engineer",
            title = "Software Engineer",
            company = "NovaTech Solutions",
            emoji = "💻",
            salary = 95000L,
            minAge = 20,
            minSmarts = 65,
            category = "Tech & Engineering",
            requiredDegree = Degree.COMPUTER_SCIENCE,
            interviewQuestion = "What is the primary advantage of writing automated unit tests?",
            interviewChoices = listOf(
                JobInterviewChoice("Catches regressions early, ensures reliability, and enables fearless refactoring", true, "Spot-on technical answer! Welcome to the engineering team!"),
                JobInterviewChoice("It gives developers an excuse to drink more coffee while tests compile", false, "Funny, but failed the technical screening.")
            )
        ),
        JobListing(
            id = "job_ai_architect",
            title = "Lead AI Systems Architect",
            company = "Cognitive Nexus Labs",
            emoji = "🤖",
            salary = 165000L,
            minAge = 24,
            minSmarts = 80,
            category = "Tech & Engineering",
            requiredDegree = Degree.COMPUTER_SCIENCE,
            interviewQuestion = "How do you prevent catastrophic forgetting in fine-tuning deep transformer models?",
            interviewChoices = listOf(
                JobInterviewChoice("Employ low-rank adaptation (LoRA) and replay regularization buffers", true, "Extraordinary technical depth! You will head our core research division!"),
                JobInterviewChoice("Re-train the model from scratch every time you click enter", false, "Completely unfeasible computational approach.")
            )
        ),
        JobListing(
            id = "job_financial_analyst",
            title = "Financial Analyst",
            company = "Apex Capital Management",
            emoji = "📊",
            salary = 88000L,
            minAge = 21,
            minSmarts = 65,
            category = "Business & Finance",
            requiredDegree = Degree.BUSINESS,
            interviewQuestion = "How do you assess whether a company's dividend payout is sustainable?",
            interviewChoices = listOf(
                JobInterviewChoice("Analyze free cash flow coverage ratio and debt obligations", true, "Impressive quantitative acumen. You're hired!"),
                JobInterviewChoice("Check if their stock ticker symbol has lucky letters", false, "Wall Street is not a casino for superstitions.")
            )
        ),
        JobListing(
            id = "job_hedge_fund_partner",
            title = "Hedge Fund Managing Partner",
            company = "Citadel Crest Partners",
            emoji = "📈",
            salary = 420000L,
            minAge = 28,
            minSmarts = 85,
            category = "Business & Finance",
            requiredDegree = Degree.BUSINESS,
            interviewQuestion = "What strategy hedges geopolitical tail-risk during sudden global currency shocks?",
            interviewChoices = listOf(
                JobInterviewChoice("Diversify into uncorrelated commodities, sovereign short-duration notes, and volatility index straddles", true, "Masterful portfolio risk management! Welcome to the partnership!"),
                JobInterviewChoice("Put all investor capital into meme penny stocks", false, "You would be disbarred within 24 hours.")
            )
        ),
        JobListing(
            id = "job_nurse",
            title = "Registered Emergency Nurse",
            company = "Metropolitan Memorial Hospital",
            emoji = "💉",
            salary = 78000L,
            minAge = 21,
            minSmarts = 65,
            category = "Healthcare & Medicine",
            requiredDegree = Degree.MEDICINE,
            interviewQuestion = "How do you prioritize patient intake in a bustling trauma triage center?",
            interviewChoices = listOf(
                JobInterviewChoice("Follow ABC triage protocol (Airway, Breathing, Circulation) with calm decisiveness", true, "Vital clinical skill. Welcome to the trauma unit!"),
                JobInterviewChoice("Take whoever shouts the loudest first", false, "Dangerous triage failure.")
            )
        ),
        JobListing(
            id = "job_physician",
            title = "General Physician",
            company = "St. Jude Metropolitan Hospital",
            emoji = "🩺",
            salary = 180000L,
            minAge = 26,
            minSmarts = 80,
            category = "Healthcare & Medicine",
            requiredDegree = Degree.MEDICINE,
            interviewQuestion = "A patient comes in with high fever and sudden rashes. What is your initial protocol?",
            interviewChoices = listOf(
                JobInterviewChoice("Conduct comprehensive vitals, isolate potential infections, and order targeted lab panels", true, "Exemplary diagnostic protocol. Welcome to the medical board!"),
                JobInterviewChoice("Tell them to drink tap water and sleep it off", false, "Gross medical negligence! Immediate rejection.")
            )
        ),
        JobListing(
            id = "job_pilot",
            title = "Commercial Airline Pilot",
            company = "Skyline Airways",
            emoji = "✈️",
            salary = 140000L,
            minAge = 23,
            minSmarts = 70,
            category = "Aviation & Transport",
            requiredDegree = Degree.NONE,
            interviewQuestion = "Severe turbulence hits mid-flight with thunderheads ahead. What is your priority?",
            interviewChoices = listOf(
                JobInterviewChoice("Turn on seatbelt signs, notify ATC for altitude rerouting, and ensure passenger safety", true, "Flawless aeronautical decision making. You're our new First Officer!"),
                JobInterviewChoice("Close your eyes and let autopilot handle whatever happens", false, "Flight license revoked instantly.")
            )
        ),
        JobListing(
            id = "job_cyber_security",
            title = "Cyber Security Specialist",
            company = "Sentinel Shield Defense",
            emoji = "🛡️",
            salary = 118000L,
            minAge = 21,
            minSmarts = 70,
            category = "Tech & Engineering",
            requiredDegree = Degree.COMPUTER_SCIENCE,
            interviewQuestion = "How do you mitigate zero-day exploit vulnerabilities in infrastructure?",
            interviewChoices = listOf(
                JobInterviewChoice("Isolate compromised subnetworks, revoke compromised access tokens, and deploy memory patches", true, "Masterclass in incident response! Hired!"),
                JobInterviewChoice("Unplug the server power cord and go home for the weekend", false, "Catastrophic security failure.")
            )
        ),
        JobListing(
            id = "job_electrician",
            title = "Licensed Journeyman Electrician",
            company = "VoltCraft Electrical Co.",
            emoji = "⚡",
            salary = 68000L,
            minAge = 18,
            minSmarts = 40,
            category = "Trades & Construction",
            requiredDegree = Degree.NONE,
            interviewQuestion = "Which safety equipment is non-negotiable before probing a commercial 480V circuit breaker panel?",
            interviewChoices = listOf(
                JobInterviewChoice("Arc-flash rated face shield, voltage meter check, and insulated 1000V gloves", true, "Safety first! Welcome to the crew!"),
                JobInterviewChoice("Touching both copper bus bars with bare hands to see if it tingles", false, "Immediate disqualification.")
            )
        ),
        JobListing(
            id = "job_plumber",
            title = "Master Plumber",
            company = "FlowRight Pipe Systems",
            emoji = "🔧",
            salary = 75000L,
            minAge = 19,
            minSmarts = 40,
            category = "Trades & Construction",
            requiredDegree = Degree.NONE,
            interviewQuestion = "What slope grade is required for standard 3-inch wastewater pipe drainage?",
            interviewChoices = listOf(
                JobInterviewChoice("One-quarter inch per linear foot for steady gravity drainage", true, "Code compliant knowledge! You're hired!"),
                JobInterviewChoice("Plumbing pipes don't need any slope, water flows backwards", false, "Fails basic plumbing physics.")
            )
        ),
        JobListing(
            id = "job_pharmacist",
            title = "Licensed Clinical Pharmacist",
            company = "Apothecary Health Network",
            emoji = "💊",
            salary = 128000L,
            minAge = 24,
            minSmarts = 75,
            category = "Healthcare & Medicine",
            requiredDegree = Degree.MEDICINE,
            interviewQuestion = "How do you handle a suspected contraindication between two prescribed medications?",
            interviewChoices = listOf(
                JobInterviewChoice("Flag the critical interaction, consult clinical pharmacology databases, and call the physician", true, "Exemplary pharmaceutical diligence! Hired!"),
                JobInterviewChoice("Dispense both anyway and let the patient experiment", false, "License revoked for gross malpractice.")
            )
        ),
        JobListing(
            id = "job_cpa",
            title = "Certified Public Accountant",
            company = "KPMG Global Tax Partners",
            emoji = "📑",
            salary = 82000L,
            minAge = 21,
            minSmarts = 65,
            category = "Business & Finance",
            requiredDegree = Degree.BUSINESS,
            interviewQuestion = "What defines GAAP compliance for revenue recognition?",
            interviewChoices = listOf(
                JobInterviewChoice("Recognize revenue when performance obligations are satisfied by transferring goods or services", true, "Flawless audit knowledge! Welcome aboard!"),
                JobInterviewChoice("Count money before the customer even pays", false, "Tax fraud violation.")
            )
        ),
        JobListing(
            id = "job_bartender",
            title = "Night Club Mixologist",
            company = "Velvet Lounge Nightclub",
            emoji = "🍸",
            salary = 46000L,
            minAge = 21,
            minSmarts = 30,
            category = "Service & Hospitality",
            requiredDegree = Degree.NONE,
            interviewQuestion = "A customer has had too many drinks and demands another cocktail. What do you do?",
            interviewChoices = listOf(
                JobInterviewChoice("Cut them off with tactful courtesy, offer water, and arrange a taxi home", true, "Professional and responsible mixology. Welcome to Velvet Lounge!"),
                JobInterviewChoice("Double the tequila shot to get a bigger tip", false, "Liquor law violation.")
            )
        ),
        JobListing(
            id = "job_animator",
            title = "3D Game Character Animator",
            company = "Epic Pixel Interactive",
            emoji = "🎬",
            salary = 74000L,
            minAge = 19,
            minSmarts = 50,
            category = "Creative & Media",
            requiredDegree = Degree.ARTS,
            interviewQuestion = "What is the key principle for giving weight and momentum to a character's run cycle?",
            interviewChoices = listOf(
                JobInterviewChoice("Anticipation, squash-and-stretch, and believable follow-through arcs", true, "Dynamic animation talent! Welcome to the game studio!"),
                JobInterviewChoice("Make the character hover stiffly with no joint movements", false, "Unconvincing robotic motion.")
            )
        ),
        JobListing(
            id = "job_ceo",
            title = "Chief Executive Officer (CEO)",
            company = "Aegis Global Holdings",
            emoji = "👑",
            salary = 580000L,
            minAge = 30,
            minSmarts = 85,
            category = "Executive & Leadership",
            requiredDegree = Degree.BUSINESS,
            interviewQuestion = "The company faces market disruption from new competitors. What is your vision?",
            interviewChoices = listOf(
                JobInterviewChoice("Re-align capital into R&D innovation, streamline operating efficiency, and expand into emergent markets", true, "Visionary leadership! You have been appointed CEO!"),
                JobInterviewChoice("Panic, fire the engineers, and sell corporate assets at clearance prices", false, "Board of Directors vetoed your candidacy.")
            )
        ),
        JobListing(
            id = "job_side_tutor",
            title = "Weekend Academic Tutor",
            company = "Apex Learning Academy",
            emoji = "📚",
            salary = 18000L,
            minAge = 16,
            minSmarts = 55,
            category = "Part-Time & Side Gigs",
            requiredDegree = Degree.NONE,
            interviewQuestion = "A student is struggling with algebra fundamentals. How do you coach them?",
            interviewChoices = listOf(
                JobInterviewChoice("Break complex equations into visual step-by-step building blocks and encourage questions", true, "Patient and inspiring mentorship. Hired for weekend tutoring!"),
                JobInterviewChoice("Tell them they aren't smart enough and should give up", false, "Unacceptable coaching behavior.")
            )
        ),
        JobListing(
            id = "job_side_delivery",
            title = "Evening Courier / Delivery",
            company = "SwiftDrop Logistics",
            emoji = "🛵",
            salary = 22000L,
            minAge = 18,
            minSmarts = 25,
            category = "Part-Time & Side Gigs",
            requiredDegree = Degree.NONE,
            interviewQuestion = "How do you navigate urban deliveries during heavy rain and traffic?",
            interviewChoices = listOf(
                JobInterviewChoice("Use GPS route optimization, secure weather-resistant bags, and drive safely", true, "Reliable courier! Welcome to the SwiftDrop dispatch team!"),
                JobInterviewChoice("Throw packages over fences from the street without stopping", false, "Delivery contract denied.")
            )
        )
    )

    // ASSETS - CARS & LUXURY SUPERCARS
    val CARS_FOR_SALE = listOf(
        Asset(type = AssetType.CAR, name = "Used Hatchback 2012", value = 3500L, annualMaintenance = 300L),
        Asset(type = AssetType.CAR, name = "Economy Sedan 2018", value = 12000L, annualMaintenance = 450L),
        Asset(type = AssetType.CAR, name = "Toyota Camry Sedan 2022", value = 22000L, annualMaintenance = 500L),
        Asset(type = AssetType.CAR, name = "Tesla Model 3 Long Range", value = 48000L, annualMaintenance = 650L),
        Asset(type = AssetType.CAR, name = "BMW M4 Competition Coupe", value = 78000L, annualMaintenance = 1600L),
        Asset(type = AssetType.CAR, name = "Porsche 911 Carrera S", value = 125000L, annualMaintenance = 2400L),
        Asset(type = AssetType.CAR, name = "Mercedes-Maybach Luxury SUV", value = 210000L, annualMaintenance = 3800L),
        Asset(type = AssetType.CAR, name = "1967 Shelby GT500 Classic", value = 260000L, annualMaintenance = 2800L),
        Asset(type = AssetType.CAR, name = "Lamborghini Huracán Evo", value = 280000L, annualMaintenance = 5200L),
        Asset(type = AssetType.CAR, name = "Ferrari V12 Berlinetta", value = 365000L, annualMaintenance = 6800L),
        Asset(type = AssetType.CAR, name = "McLaren 720S Spider", value = 395000L, annualMaintenance = 7500L),
        Asset(type = AssetType.CAR, name = "Rolls-Royce Phantom", value = 480000L, annualMaintenance = 8500L),
        Asset(type = AssetType.CAR, name = "Bugatti Chiron Hypercar", value = 2800000L, annualMaintenance = 28000L),
        Asset(type = AssetType.CAR, name = "Pagani Huayra Exotic", value = 3200000L, annualMaintenance = 32000L)
    )

    val HOUSES_FOR_SALE = listOf(
        Asset(type = AssetType.REAL_ESTATE, name = "Cozy Studio Condo", value = 95000L, annualMaintenance = 1800L),
        Asset(type = AssetType.REAL_ESTATE, name = "Suburban 3-Bedroom Home", value = 280000L, annualMaintenance = 3500L),
        Asset(type = AssetType.REAL_ESTATE, name = "Modern Luxury Penthouse", value = 750000L, annualMaintenance = 8000L),
        Asset(type = AssetType.REAL_ESTATE, name = "Gated Waterfront Mansion", value = 2200000L, annualMaintenance = 20000L)
    )

    val PETS_FOR_ADOPTION = listOf(
        Asset(type = AssetType.PET, name = "Golden Retriever Puppy", value = 350L, annualMaintenance = 500L),
        Asset(type = AssetType.PET, name = "British Shorthair Kitten", value = 250L, annualMaintenance = 400L),
        Asset(type = AssetType.PET, name = "Parrot Companion", value = 150L, annualMaintenance = 200L)
    )

    val PHONES_FOR_SALE = listOf(
        Asset(type = AssetType.PHONE, name = "SimPhone", value = 499L, annualMaintenance = 30L),
        Asset(type = AssetType.PHONE, name = "SimPhone Pro", value = 999L, annualMaintenance = 60L)
    )

    val TECH_FOR_SALE = listOf(
        Asset(type = AssetType.PHONE, name = "SimPhone", value = 499L, annualMaintenance = 30L),
        Asset(type = AssetType.PHONE, name = "SimPhone Pro", value = 999L, annualMaintenance = 60L),
        Asset(type = AssetType.TECH, name = "Pro Developer Laptop", value = 850L, annualMaintenance = 40L),
        Asset(type = AssetType.TECH, name = "High-Performance Tablet", value = 650L, annualMaintenance = 25L),
        Asset(type = AssetType.TECH, name = "Smart Health Watch", value = 299L, annualMaintenance = 15L),
        Asset(type = AssetType.TECH, name = "Ultra 4K Gaming Console", value = 499L, annualMaintenance = 20L),
        Asset(type = AssetType.TECH, name = "VR Spatial Headset", value = 1100L, annualMaintenance = 45L),
        Asset(type = AssetType.TECH, name = "Noise-Cancelling Smart Headphones", value = 350L, annualMaintenance = 15L)
    )

    // FOOD & NUTRITION ITEMS
    val FOOD_ITEMS = listOf(
        FoodItem("food_cart", "Street Cart Pretzels & Soda", 6L, hungerRestore = 15, healthBonus = -1, happinessBonus = +2, "🥨", "Quick street snack to curb morning cravings."),
        FoodItem("food_snack", "Fast Food Value Meal", 14L, hungerRestore = 35, healthBonus = -2, happinessBonus = +3, "🍔", "A quick burger, fries, and cold fountain soda."),
        FoodItem("food_grocery", "Fresh Supermarket Groceries", 48L, hungerRestore = 60, healthBonus = +4, happinessBonus = +3, "🥗", "Farm fresh vegetables, chicken, eggs, pasta, and fruits."),
        FoodItem("food_organic", "Organic Chef Meal Prep Box", 115L, hungerRestore = 85, healthBonus = +8, happinessBonus = +6, "🍱", "Gourmet dietitian-curated balanced meals delivered for the week."),
        FoodItem("food_dining", "Michelin 3-Star Tasting Menu", 290L, hungerRestore = 100, healthBonus = +10, happinessBonus = +20, "🍷", "Multi-course culinary masterpiece with fine dining wine pairing.")
    )

    // UBER RIDESHARE TRIPS FOR SIMPHONE PRO
    val UBER_TRIPS = listOf(
        UberTrip("Sarah K.", "Downtown Financial District", 34L, 6L, 5.0f, "👩‍💼", "Very smooth ride and great music choice!"),
        UberTrip("Marcus T.", "Airport Terminal 3 Departures", 58L, 12L, 4.9f, "🧳", "Arrived right on time for my flight. Awesome driver!"),
        UberTrip("Emily & Dave", "Westside Concert Arena", 42L, 8L, 5.0f, "🎸", "Super friendly and got us through traffic quickly!"),
        UberTrip("Grandma Helen", "Community Botanical Gardens", 24L, 5L, 5.0f, "👵", "A very polite young driver who helped with my purse."),
        UberTrip("Kevin L.", "Tech Innovation Campus", 38L, 7L, 4.8f, "💻", "Great conversation about software and startups!")
    )
}
