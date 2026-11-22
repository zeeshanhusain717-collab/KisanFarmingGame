package com.kisan.farminggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kisan.farminggame.data.Balance
import com.kisan.farminggame.data.GameState
import com.kisan.farminggame.data.SaveManager

class SettingsFragment : Fragment() {

    private var tvInfo: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvInfo = view.findViewById(R.id.tvSettingsInfo)

        view.findViewById<Button>(R.id.btnReset).setOnClickListener {
            GameState.resetGame()
            SaveManager.save(requireContext())
            Toast.makeText(requireContext(), "Game reset ho gaya", Toast.LENGTH_SHORT).show()
            refreshInfo()
        }

        refreshInfo()
    }

    private fun refreshInfo() {
        tvInfo?.text =
            "Credits: ${GameState.credits}\n" +
            "E-Crystals: ${GameState.crystals}\n" +
            "Castle Tier: ${GameState.tier}/7\n\n" +
            "Offline kamai max: ${Balance.MAX_OFFLINE_SECONDS / 3600} ghante tak.\n" +
            "App band karne par progress apne aap save hoti hai."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tvInfo = null
    }
}
