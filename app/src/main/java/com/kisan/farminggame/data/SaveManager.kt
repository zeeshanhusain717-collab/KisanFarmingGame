package com.kisan.farminggame.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object SaveManager {
    private const val FILE_NAME = "save.json"

    fun save(context: Context) {
        val root = JSONObject()
        root.put("credits", GameState.credits)
        root.put("tier", GameState.tier)

        val plots = JSONArray()
        for (i in 0 until 7) {
            val p = JSONObject()
            p.put("unlocked", GameState.plotUnlocked[i])
            p.put("status", GameState.plotStatus[i])
            p.put("start", GameState.plotStartTime[i])
            plots.put(p)
        }
        root.put("plots", plots)

        val parts = JSONArray()
        for (m in 0 until 7) {
            val row = JSONArray()
            for (p in 0 until 7) row.put(GameState.machineParts[m][p])
            parts.put(row)
        }
        root.put("parts", parts)

        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(root.toString().toByteArray())
        }
    }

    fun load(context: Context) {
        val file = context.getFileStreamPath(FILE_NAME)
        if (!file.exists()) return
        try {
            val root = JSONObject(file.readText())
            GameState.credits = root.optInt("credits", 777)
            GameState.tier = root.optInt("tier", 1).coerceIn(1, 7)

            val plots = root.optJSONArray("plots")
            if (plots != null) {
                for (i in 0 until minOf(7, plots.length())) {
                    val p = plots.getJSONObject(i)
                    GameState.plotUnlocked[i] = p.optBoolean("unlocked", i == 0)
                    GameState.plotStatus[i] = p.optInt("status", 0)
                    GameState.plotStartTime[i] = p.optLong("start", 0L)
                }
            }

            val parts = root.optJSONArray("parts")
            if (parts != null) {
                for (m in 0 until minOf(7, parts.length())) {
                    val row = parts.getJSONArray(m)
                    for (p in 0 until minOf(7, row.length())) {
                        GameState.machineParts[m][p] = row.optInt(p, 1)
                    }
                }
            }
        } catch (e: Exception) {
            // Save kharab ho to default values rahengi
        }
    }
}
