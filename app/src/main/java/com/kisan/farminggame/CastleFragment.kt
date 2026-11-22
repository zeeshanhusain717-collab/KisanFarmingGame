package com.kisan.farminggame

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kisan.farminggame.data.GameState
import com.kisan.farminggame.data.StoryList

class CastleFragment : Fragment() {

    private var tvCredits: TextView? = null
    private var tvTier: TextView? = null
    private var tvChapter: TextView? = null
    private var tvBonus: TextView? = null
    private var btnUpgrade: Button? = null
    private val handler = Handler(Looper.getMainLooper())

    private val ticker = object : Runnable {
        override fun run() {
            refreshScreen()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_castle, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCredits = view.findViewById(R.id.tvCastleCredits)
        tvTier = view.findViewById(R.id.tvCastleTier)
        tvChapter = view.findViewById(R.id.tvCastleChapter)
        tvBonus = view.findViewById(R.id.tvCastleBonus)
        btnUpgrade = view.findViewById(R.id.btnCastleUpgrade)

        btnUpgrade?.setOnClickListener {
            if (GameState.upgradeCastle()) {
                toast("Castle Tier ${GameState.tier} unlock hua! Naya chapter khula.")
            } else if (GameState.tier >= 7) {
                toast("Castle poora ban chuka hai")
            } else {
                toast("Paise kam hain")
            }
            refreshScreen()
        }
        refreshScreen()
    }

    private fun refreshScreen() {
        val t = GameState.tier
        tvCredits?.text = "Credits: ${GameState.credits}"
        tvTier?.text = "Rajput Agri-Palace - Tier $t/7"

        val chapter = StoryList.chapters.getOrNull(t - 1)
        tvChapter?.text = if (chapter != null) {
            "Chapter ${chapter.number}: ${chapter.title}\n\n${chapter.text}"
        } else {
            StoryList.ending
        }

        tvBonus?.text = "Income bonus: +${((GameState.bonus() - 1) * 100).toInt()}%"

        val button = btnUpgrade ?: return
        if (t >= 7) {
            button.text = "Castle MAX"
            button.isEnabled = false
        } else {
            button.text = "Tier ${t + 1} upgrade: ${GameState.castleCost()} credits"
            button.isEnabled = true
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

    override fun onDestroyView() {
        super.onDestroyView()
        tvCredits = null
        tvTier = null
        tvChapter = null
        tvBonus = null
        btnUpgrade = null
    }
}
