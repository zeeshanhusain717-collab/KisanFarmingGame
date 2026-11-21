package com.kisan.farminggame.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kisan.farminggame.R
import com.kisan.farminggame.data.GameState
import com.kisan.farminggame.data.MachineList

class MachineAdapter(
    private val onPartTap: (machine: Int, part: Int) -> Unit
) : RecyclerView.Adapter<MachineAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvMachineTitle)
        val info: TextView = view.findViewById(R.id.tvMachineInfo)
        val parts: TextView = view.findViewById(R.id.tvParts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_machine, parent, false)
        return VH(v)
    }

    override fun getItemCount() = MachineList.machines.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val machine = MachineList.machines[position]
        val income = GameState.machineIncome(position)
        val totalParts = GameState.machineParts[position].sum()

        holder.title.text = "${machine.id}. ${machine.name}"
        holder.info.text = "Income: %.1f per sec | Parts: %d/49".format(income, totalParts)

        val lines = GameState.machineParts[position].mapIndexed { p, level ->
            "Part ${p + 1}: Level $level (upgrade: ${GameState.partUpgradeCost(position, p)})"
        }
        holder.parts.text = lines.joinToString("\n")

        holder.itemView.setOnClickListener {
            val parts = GameState.machineParts[position]
            val weakest = parts.indices.minByOrNull { parts[it] } ?: 0
            onPartTap(position, weakest)
        }
    }
}
