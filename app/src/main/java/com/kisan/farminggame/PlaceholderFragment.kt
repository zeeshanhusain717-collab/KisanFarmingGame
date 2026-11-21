package com.kisan.farminggame

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class PlaceholderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val title = arguments?.getString(ARG_TITLE) ?: ""
        return TextView(requireContext()).apply {
            text = "$title (agle update mein banega)"
            gravity = Gravity.CENTER
            textSize = 20f
        }
    }

    companion object {
        private const val ARG_TITLE = "title"

        fun newInstance(title: String) = PlaceholderFragment().apply {
            arguments = Bundle().apply { putString(ARG_TITLE, title) }
        }
    }
}
