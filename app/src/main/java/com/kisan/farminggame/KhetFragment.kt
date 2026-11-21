package com.kisan.farminggame

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kisan.farminggame.data.GameState
import com.kisan.farminggame.ui.KhetAdapter

class KhetFragment : Fragment() {

    private lateinit var adapter: KhetAdapter
    private lateinit var tvCredits: android.widget.TextView
    private val handler = Handler(Looper.getMainLooper())

    private val ticker = object : Runnable {
        override fun run() {
            adapter.notifyDataSetChanged()
            updateCredits()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_khet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCredits = view.findViewById(R.id.tvCredits)

        val rv = view.findViewById<RecyclerView>(R.id.rvKhet)
        rv.layoutManager = LinearLayoutManager(requireContext())

        adapter = KhetAdapter { index -> onKhetTap(index) }
        rv.adapter = adapter

        updateCredits()
    }

    private fun onKhetTap(index: Int) {
        val crop = com.kisan.farminggame.data.CropList.crops[index]
        when {
            !GameState.plotUnlocked[index] -> {
                if (GameState.unlock(index)) {
                    toast("${crop.name} khet unlock hua!")
                } else {
                    toast("Paise kam hain ya pichla khet unlock karo")
                }
            }
            GameState.plotStatus[index] == 0 -> {
                GameState.plant(index)
                toast("${crop.name} ka beej bo diya")
            }
            GameState.isReady(index) -> {
                val gain = GameState.harvest(index)
                toast("+$gain credits mile!")
            }
            else -> toast("Abhi ug rahi hai")
        }
        adapter.notifyDataSetChanged()
        updateCredits()
    }

    private fun updateCredits() {
        if (::tvCredits.isInitialized) {
            tvCredits.text = "Credits: ${GameState.credits}"
        }
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
}
