package com.kisan.farminggame.data

data class Machine(
    val id: Int,
    val name: String,
    val baseIncome: Double
)

object MachineList {
    val machines = listOf(
        Machine(1, "Flour Mill", 0.5),
        Machine(2, "Juice Press", 0.8),
        Machine(3, "Popcorn Popper", 1.0),
        Machine(4, "Chips Fryer", 1.5),
        Machine(5, "Ketchup Kettle", 2.0),
        Machine(6, "Jam Pot", 3.0),
        Machine(7, "Gold Refiner", 5.0)
    )
}
