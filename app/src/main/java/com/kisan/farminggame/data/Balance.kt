package com.kisan.farminggame.data

// Game ka sara balance ek jagah. Numbers badalne ke liye sirf yahan change karein.
object Balance {
    // Fasal ka daam aur time multiplier: ek khet ke harvest mein kitne units milte hain
    const val UNITS_PER_HARVEST = 7

    // Castle: har tier ka cost = BASE x tier
    const val CASTLE_BASE_COST = 500

    // Machine part upgrade: cost = BASE x current level
    const val PART_UPGRADE_BASE_COST = 50

    // Machine part ka max level
    const val MAX_PART_LEVEL = 7

    // Shop: boost ki max level aur cost
    const val MAX_BOOST_LEVEL = 5
    const val BOOST_CRYSTAL_COST = 7

    // Crystal pack
    const val CRYSTAL_PACK_AMOUNT = 10
    const val CRYSTAL_PACK_COST = 500

    // Har boost level par bonus (0.1 = 10%)
    const val BOOST_STEP = 0.1

    // Tier ka bonus (0.1 = 10% per tier)
    const val TIER_BONUS_STEP = 0.1

    // Offline kamai ki max seconds (12 ghante = 43200)
    const val MAX_OFFLINE_SECONDS = 12L * 3600L

    // Game ka rough time: 7 khet x 7 unit x income ke hisaab se 48 ghante ka target
    const val TARGET_GAME_HOURS = 48
}
