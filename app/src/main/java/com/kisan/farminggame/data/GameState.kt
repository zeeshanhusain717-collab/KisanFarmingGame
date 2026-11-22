package com.kisan.farminggame.data

object GameState {
    var credits: Int = 777
    var crystals: Int = 77
    var tier: Int = 1

    // Shop ke boosts: kitni baar kharide gaye
    var cropBoostLevel: Int = 0      // har level par fasal ka daam +10%
    var machineBoostLevel: Int = 0   // har level par machine income +10%

    val plotUnlocked = BooleanArray(7) { it == 0 }
    val plotStatus = IntArray(7)
    val plotStartTime = LongArray(7)

    val machineParts = Array(7) { IntArray(7) { 1 } }

    // Final balance: maximum credits limit (game ko achanak bahut bada na hone dein)
    const val MAX_CREDITS = 9_999_999

    fun clampCredits() {
        if (credits > MAX_CREDITS) credits = MAX_CREDITS
        if (credits < 0) credits = 0
    }

    fun bonus(): Double = 1.0 + 0.1 * (tier - 1)

    fun cropBoost(): Double = 1.0 + 0.1 * cropBoostLevel

    fun machineBoost(): Double = 1.0 + 0.1 * machineBoostLevel

    fun castleCost(): Int = 500 * tier

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
        val gain = (crop.pricePerUnit * 7 * bonus() * cropBoost()).toInt()
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
        50 * machineParts[machineIndex][partIndex]

    fun upgradePart(machineIndex: Int, partIndex: Int): Boolean {
        val level = machineParts[machineIndex][partIndex]
        if (level >= 7) return false
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

    // Dukaan: E-Crystals credits se kharidna
    fun buyCrystals(): Boolean {
        val cost = ShopList.crystalPackCost
        if (credits < cost) return false
        credits -= cost
        crystals += ShopList.crystalPackAmount
        return true
    }

    // Dukaan: fasal boost (crystals se)
    fun buyCropBoost(): Boolean {
        if (cropBoostLevel >= 5) return false
        val cost = ShopList.boostCost
        if (crystals < cost) return false
        crystals -= cost
        cropBoostLevel += 1
        return true
    }

    // Dukaan: machine boost (crystals se)
    fun buyMachineBoost(): Boolean {
        if (machineBoostLevel >= 5) return false
        val cost = ShopList.boostCost
        if (crystals < cost) return false
        crystals -= cost
        machineBoostLevel += 1
        return true
    }
}
