package com.kisan.farminggame.data

data class Crop(
    val id: Int,
    val name: String,
    val growSeconds: Int,
    val pricePerUnit: Int,
    val unlockCost: Int
)

object CropList {
    // growSeconds: fasal ugne ka time (seconds mein). 48 ghante ke liye baad mein badhaya ja sakta hai.
    val crops = listOf(
        Crop(1, "Gehu", 5, 10, 0),
        Crop(2, "Gajar", 8, 15, 300),
        Crop(3, "Makka", 12, 25, 600),
        Crop(4, "Aalu", 16, 35, 900),
        Crop(5, "Tamatar", 20, 50, 1200),
        Crop(6, "Strawberry", 25, 70, 1500),
        Crop(7, "Sone ki fasal", 40, 150, 1800)
    )
}
