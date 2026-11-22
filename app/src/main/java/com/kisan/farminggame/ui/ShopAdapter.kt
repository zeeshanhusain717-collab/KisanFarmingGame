package com.kisan.farminggame.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kisan.farminggame.R

class ShopAdapter(
    private val items: List<ShopItem>
) : RecyclerView.Adapter<ShopAdapter.VH>() {

    data class ShopItem(
        val title: String,
        val info: String,
        val buttonText: String,
        val onBuy: () -> Boolean
    )

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvShopTitle)
        val info: TextView = view.findViewById(R.id.tvShopInfo)
        val button: Button = view.findViewById(R.id.btnShopBuy)
    }

    var onItemBought: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        return VH(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.info.text = item.info
        holder.button.text = item.buttonText
        holder.button.setOnClickListener {
            if (item.onBuy()) {
                onItemBought?.invoke()
            }
        }
    }
}
