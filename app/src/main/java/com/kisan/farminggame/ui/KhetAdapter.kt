package com.kisan.farminggame.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kisan.farminggame.R
import com.kisan.farminggame.data.CropList
import com.kisan.farminggame.data.GameState

class KhetAdapter(
    private val onAction: (Int) -> Unit
) : RecyclerView.Adapter<KhetAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val info: TextView = view.findViewById(R.id.tvInfo)
        val action: Button = view.findViewById(R.id.btnAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_khet, parent, false)
        return VH(v)
    }

    override fun getItemCount() = CropList.crops.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val crop = CropList.crops[position]
        holder.title.text = "Khet ${crop.id}: ${crop.name}"
        holder.action.setOnClickListener { onAction(position) }

        if (!GameState.plotUnlocked[position]) {
            holder.info.text = "LOCKED. Unlock: ${crop.unlockCost} credits"
            holder.action.text = "Unlock"
            holder.action.isEnabled = true
            return
        }

        GameState.refreshStatus(position)
        when (GameState.plotStatus[position]) {
            0 -> {
                holder.info.text = "Khaali. Daam: ${crop.pricePerUnit} per unit"
                holder.action.text = "Beej bo'o"
                holder.action.isEnabled = true
            }
            1 -> {
                val left = GameState.secondsLeft(position)
                holder.info.text = "Ug rahi hai. Baaki: $left sec"
                holder.action.text = "Ruko"
                holder.action.isEnabled = false
            }
            else -> {
                holder.info.text = "Tayar hai! Kaato aur paisa lo"
                holder.action.text = "Kaato"
                holder.action.isEnabled = true
            }
        }
    }
}
