package com.kisan.farminggame.data

object GameState {
    var credits: Int = 777

    // Har khet ki state: unlocked, aur fasal ka status
    // status: 0 = khaali, 1 = ug rahi hai, 2 = tayar hai
    val plotUnlocked = BooleanArray(7) { it == 0 }
    val plotStatus = IntArray(7)
    val plotStartTime = LongArray(7)

    fun isReady(index: Int): Boolean {
        if (plotStatus[index] != 1) return false
        val crop = CropList.crops[index]
        val elapsed = (System.currentTimeMillis() - plotStartTime[index]) / 1000
        return elapsed >= crop.growSeconds
    }

    fun secondsLeft(index: Int): Long {
        val crop = CropList.crops[index]
        val elapsed = (System.currentTimeMillis() - plotStartTime[index]) / 1000
        return (crop.growSeconds - elapsed).coerceAtLeast(0)
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
        val gain = crop.pricePerUnit * 7
        credits += gain
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
}
