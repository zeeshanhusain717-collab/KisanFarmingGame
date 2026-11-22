package com.kisan.farminggame

import android.app.AlertDialog
import android.content.Context
import com.kisan.farminggame.data.GameState

object TutorialDialog {

    private const val TEXT =
        "Swagat hai, Veer Singh!\n\n" +
        "1. Khet tab mein khaali khet par tap karke beej bo'o.\n" +
        "2. Fasal tayar hone par Kaato dabakar paisa lo.\n" +
        "3. Factory tab mein machine par tap karke part upgrade karo. Income badhegi.\n" +
        "4. Castle tab mein tier upgrade karo, naya chapter khulega.\n" +
        "5. Dukaan mein boost lo, Settings mein game reset ho sakta hai.\n\n" +
        "Ab khet shuru karein!"

    // Sirf pehli baar dikhega. Dikhne ke baad save mein mark ho jayega.
    fun showIfFirstTime(context: Context) {
        if (GameState.tutorialSeen) return
        GameState.tutorialSeen = true
        AlertDialog.Builder(context)
            .setTitle("Kaise khelein")
            .setMessage(TEXT)
            .setPositiveButton("Samajh gaya") { d, _ -> d.dismiss() }
            .setCancelable(false)
            .show()
    }
}
