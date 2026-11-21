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
import com.kisan.farminggame.ui.MachineAdapter

class MachineFragment : Fragment() {

    private var adapter: MachineAdapter? = null
    private var tvCredits: TextView? = null
    private val handler = Handler(Looper.getMainLooper())

    private val ticker = object : Runnable {
        override fun run() {
            refreshScreen()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_machine, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCredits = view.findViewById(R.id.tvMachineCredits)

        val rv = view.findViewById<RecyclerView>(R.id.rvMachine)
        rv.layoutManager = LinearLayoutManager(requireContext())

        val newAdapter = MachineAdapter { machine, part ->
            if (GameState.upgradePart(machine, part)) {
                toast("Part upgrade hua!")
            } else {
                toast("Paise kam hain ya part max level par hai")
            }
            refreshScreen()
        }
        adapter = newAdapter
        rv.adapter = newAdapter
        refreshScreen()
    }

    private fun refreshScreen() {
        adapter?.notifyDataSetChanged()
        tvCredits?.text = "Credits: ${GameState.credits}"
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
    }
}
