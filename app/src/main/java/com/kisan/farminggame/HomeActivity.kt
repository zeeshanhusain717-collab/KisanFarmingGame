package com.kisan.farminggame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)

        if (savedInstanceState == null) {
            showFragment(KhetFragment())
        }

        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_khet -> showFragment(KhetFragment())
                else -> showPlaceholder(item.title.toString())
            }
            true
        }
    }

    private fun showFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun showPlaceholder(title: String) {
        val tv = android.widget.TextView(this).apply {
            text = "$title (agle update mein banega)"
            gravity = android.view.Gravity.CENTER
            textSize = 20f
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, PlaceholderFragment.newInstance(title))
            .commit()
    }
}
