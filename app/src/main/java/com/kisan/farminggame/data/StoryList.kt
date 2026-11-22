package com.kisan.farminggame.data

data class StoryChapter(
    val number: Int,
    val title: String,
    val text: String,
    val unlockTier: Int
)

object StoryList {
    val intro = listOf(
        "Bahut pehle ki baat hai. Veer Singh ko apne dada ji se sirf ek chhota khet mila.",
        "Unhone kasam khayi ki wo 7 sector wala Rajput Agri-Palace banayenge.",
        "Har sector ek nayi fasal, ek nayi machine aur ek naya raaz laata hai.",
        "Kya aap Veer Singh ki madad karenge?"
    )

    val chapters = listOf(
        StoryChapter(
            1, "Ek chhota khet",
            "Veer Singh ne pehla beej bo diya. Gehu ki pehli fasal ugi, aur gaon ne pehli baar uski mehnat dekhi.",
            unlockTier = 1
        ),
        StoryChapter(
            2, "Gaon ki chakki",
            "Gaon ki chakki chalu hui. Ab aata bikne laga, aur Veer Singh ke paas pehli baar sach mein kamai aayi.",
            unlockTier = 2
        ),
        StoryChapter(
            3, "Mela aur makka",
            "Makka ki fasal aayi, aur mele mein uska naam hua. Dur-dur ke gaon se log uske khet dekhne aaye.",
            unlockTier = 3
        ),
        StoryChapter(
            4, "Aalu aur chips",
            "Aloo aur chips ki factory ne gaon roshan kiya. Ab naukri bhi thi aur naye sapne bhi.",
            unlockTier = 4
        ),
        StoryChapter(
            5, "Ketchup ka raasta",
            "Tamatar ki ketchup se shahar tak raasta bana. Veer Singh ka naam door tak pahunch gaya.",
            unlockTier = 5
        ),
        StoryChapter(
            6, "Raja ka darbar",
            "Strawberry jam ki khushboo se raja ka darbar mehka. Raja ne Veer Singh ko darbar mein bulaya.",
            unlockTier = 6
        ),
        StoryChapter(
            7, "Sone ki fasal",
            "Sone ki fasal ugi! Rajput Agri-Palace poora bana, aur Veer Singh ki kahani amar ho gayi.",
            unlockTier = 7
        )
    )

    // Ending: jab sabhi chapter khul jaayein
    val ending = "Veer Singh ne dada ji ka sapna poora kiya. Chhota khet ab ek shaandar Agri-Palace hai, aur gaon ke har bachche ki zubaan par unki kahani hai. Aap bhi ek Rajput Agri-Palace ke maalik bane."

    fun chapterFor(tier: Int): StoryChapter? =
        chapters.firstOrNull { it.unlockTier == tier }
}
