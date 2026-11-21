package com.kisan.farminggame

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val title = findViewById<TextView>(R.id.tvHomeTitle)
        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // Abhi har tab sirf apna naam dikhata hai. Asli screens Update 2 se aayengi.
        title.text = getString(R.string.nav_khet)

        nav.setOnItemSelectedListener { item ->
            title.text = when (item.itemId) {
                R.id.nav_khet -> getString(R.string.nav_khet)
                R.id.nav_factory -> getString(R.string.nav_factory)
                R.id.nav_castle -> getString(R.string.nav_castle)
                R.id.nav_dukaan -> getString(R.string.nav_dukaan)
                else -> title.text
            }
            true
        }
    }
}
