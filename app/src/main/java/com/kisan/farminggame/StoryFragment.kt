package com.kisan.farminggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kisan.farminggame.data.GameState
import com.kisan.farminggame.data.StoryList

class StoryFragment : Fragment() {

    private var tvStory: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_story, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvStory = view.findViewById(R.id.tvStoryText)

        val allChapters = GameState.tier >= 7
        tvStory?.text = if (allChapters) {
            StoryList.ending
        } else {
            StoryList.intro.joinToString("\n\n")
        }

        view.findViewById<Button>(R.id.btnStoryContinue).setOnClickListener {
            (activity as? HomeActivity)?.openKhet()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tvStory = null
    }
}
