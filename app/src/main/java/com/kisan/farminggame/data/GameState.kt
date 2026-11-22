package com.kisan.farminggame.data

object GameState {
    var credits: Int = 777
    var crystals: Int = 77
    var tier: Int = 1

    var cropBoostLevel: Int = 0
    var machineBoostLevel: Int = 0

    // Tutorial sirf ek baar dikhega
    var tutorialSeen: Boolean = false

    val plotUnlocked = BooleanArray(7) { it == 0 }
    val plotStatus = IntArray(7)
    val plotStartTime = LongArray(7)

    val machineParts = Array(7) { IntArray(7) { 1 } }

    var lastSavedTime: Long = System.currentTimeMillis()

    const val MAX_CREDITS = 9_999_999

    fun clampCredits() {
        if (credits > MAX_CREDITS) credits = MAX_CREDITS
        if (credits < 0) credits = 0
    }

    fun bonus(): Double = 1.0 + Balance.TIER_BONUS_STEP * (tier - 1)

    fun cropBoost(): Double = 1.0 + Balance.BOOST_STEP * cropBoostLevel

    fun machineBoost(): Double = 1.0 + Balance.BOOST_STEP * machineBoostLevel

    fun castleCost(): Int = Balance.CASTLE_BASE_COST * tier

    fun upgradeCastle(): Boolean {
        if (tier >= 7) return false
        val cost = castleCost()
        if (credits < cost) return false
        credits -= cost
        tier += 1
        clampCredits()
        return true
    }

    fun elapsedSeconds(index: Int): Long =
        (System.currentTimeMillis() - plotStartTime[index]) / 1000

    fun secondsLeft(index: Int): Long {
        val crop = CropList.crops[index]
        return (crop.growSeconds - elapsedSeconds(index)).coerceAtLeast(0)
    }

    fun refreshStatus(index: Int) {
        if (plotStatus[index] == 1 && secondsLeft(index) == 0L) {
            plotStatus[index] = 2
        }
    }

    fun isReady(index: Int): Boolean {
        refreshStatus(index)
        return plotStatus[index] == 2
    }

    fun plant(index: Int): Boolean {
        if (!plotUnlocked[index] || plotStatus[index] != 0) return false
        plotStatus[index] = 1
        plotStartTime[index] = System.currentTimeMillis()
        return true
    }

    fun harvest(index: Int): Int {
        if (!isReady(index)) return 0
        val crop = CropList.crops[index]
        val gain = (crop.pricePerUnit * Balance.UNITS_PER_HARVEST * bonus() * cropBoost()).toInt()
        credits += gain
        clampCredits()
        plotStatus[index] = 0
        return gain
    }

    fun unlock(index: Int): Boolean {
        val crop = CropList.crops[index]
        if (plotUnlocked[index]) return false
        if (index > 0 && !plotUnlocked[index - 1]) return false
        if (credits < crop.unlockCost) return false
        credits -= crop.unlockCost
        plotUnlocked[index] = true
        return true
    }

    fun machineIncome(index: Int): Double {
        val machine = MachineList.machines[index]
        val partsSum = machineParts[index].sum()
        return machine.baseIncome * partsSum / 7.0 * bonus() * machineBoost()
    }

    fun totalIncomePerSecond(): Double =
        MachineList.machines.indices.sumOf { machineIncome(it) }

    fun partUpgradeCost(machineIndex: Int, partIndex: Int): Int =
        Balance.PART_UPGRADE_BASE_COST * machineParts[machineIndex][partIndex]

    fun upgradePart(machineIndex: Int, partIndex: Int): Boolean {
        val level = machineParts[machineIndex][partIndex]
        if (level >= Balance.MAX_PART_LEVEL) return false
        val cost = partUpgradeCost(machineIndex, partIndex)
        if (credits < cost) return false
        credits -= cost
        machineParts[machineIndex][partIndex] = level + 1
        return true
    }

    fun tick(secondsPassed: Double) {
        credits += (totalIncomePerSecond() * secondsPassed).toInt()
        clampCredits()
        for (i in plotStatus.indices) refreshStatus(i)
    }

    fun applyOffline(): Int {
        val now = System.currentTimeMillis()
        val secs = ((now - lastSavedTime) / 1000L).coerceIn(0L, Balance.MAX_OFFLINE_SECONDS)
        lastSavedTime = now
        if (secs <= 0L) return 0
        val before = credits
        credits += (totalIncomePerSecond() * secs).toInt()
        clampCredits()
        for (i in plotStatus.indices) refreshStatus(i)
        return credits - before
    }

    fun resetGame() {
        credits = 777
        crystals = 77
        tier = 1
        cropBoostLevel = 0
        machineBoostLevel = 0
        tutorialSeen = false
        for (i in 0 until 7) {
            plotUnlocked[i] = i == 0
            plotStatus[i] = 0
            plotStartTime[i] = 0L
            for (p in 0 until 7) machineParts[i][p] = 1
        }
        lastSavedTime = System.currentTimeMillis()
    }

    fun buyCrystals(): Boolean {
        val cost = Balance.CRYSTAL_PACK_COST
        if (credits < cost) return false
        credits -= cost
        crystals += Balance.CRYSTAL_PACK_AMOUNT
        return true
    }

    fun buyCropBoost(): Boolean {
        if (cropBoostLevel >= Balance.MAX_BOOST_LEVEL) return false
        val cost = Balance.BOOST_CRYSTAL_COST
        if (crystals < cost) return false
        crystals -= cost
        cropBoostLevel += 1
        return true
    }

    fun buyMachineBoost(): Boolean {
        if (machineBoostLevel >= Balance.MAX_BOOST_LEVEL) return false
        val cost = Balance.BOOST_CRYSTAL_COST
        if (crystals < cost) return false
        crystals -= cost
        machineBoostLevel += 1
        return true
    }
}
