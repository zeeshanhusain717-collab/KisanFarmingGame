package com.kisan.farminggame

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kisan.farminggame.data.GameState
import com.kisan.farminggame.data.ShopList
import com.kisan.farminggame.ui.ShopAdapter

class ShopFragment : Fragment() {

    private var adapter: ShopAdapter? = null
    private var tvCredits: TextView? = null
    private var tvCrystals: TextView? = null
    private val handler = Handler(Looper.getMainLooper())

    private val ticker = object : Runnable {
        override fun run() {
            refreshScreen()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_shop, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCredits = view.findViewById(R.id.tvShopCredits)
        tvCrystals = view.findViewById(R.id.tvShopCrystals)

        val rv = view.findViewById<RecyclerView>(R.id.rvShop)
        rv.layoutManager = LinearLayoutManager(requireContext())

        val items = listOf(
            ShopAdapter.ShopItem(
                title = "E-Crystals pack",
                info = "${ShopList.crystalPackAmount} crystals, ${ShopList.crystalPackCost} credits mein",
                buttonText = "Kharido (${ShopList.crystalPackCost} credits)",
                onBuy = { GameState.buyCrystals() }
            ),
            ShopAdapter.ShopItem(
                title = "Fasal Boost",
                info = "Fasal ka daam +10%. Abhi level: ${GameState.cropBoostLevel}/5",
                buttonText = "Boost lo (${ShopList.boostCost} crystals)",
                onBuy = { GameState.buyCropBoost() }
            ),
            ShopAdapter.ShopItem(
                title = "Machine Boost",
                info = "Machine income +10%. Abhi level: ${GameState.machineBoostLevel}/5",
                buttonText = "Boost lo (${ShopList.boostCost} crystals)",
                onBuy = { GameState.buyMachineBoost() }
            )
        )

        val newAdapter = ShopAdapter(items)
        newAdapter.onItemBought = {
            toast("Kharid safal!")
            refreshScreen()
        }
        adapter = newAdapter
        rv.adapter = newAdapter
        refreshScreen()
    }

    private fun refreshScreen() {
        tvCredits?.text = "Credits: ${GameState.credits}"
        tvCrystals?.text = "E-Crystals: ${GameState.crystals}"
    }

    private fun toast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        handler.post(ticker)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(ticker)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        tvCredits = null
        tvCrystals = null
    }
}
