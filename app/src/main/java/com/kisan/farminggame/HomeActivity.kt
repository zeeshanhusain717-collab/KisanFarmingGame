package com.kisan.farminggame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kisan.farminggame.data.SaveManager

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        SaveManager.load(this)

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)

        if (savedInstanceState == null) {
            showFragment(KhetFragment())
        }

        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_khet -> showFragment(KhetFragment())
                R.id.nav_factory -> showFragment(MachineFragment())
                R.id.nav_castle -> showFragment(CastleFragment())
                else -> showFragment(PlaceholderFragment.newInstance(item.title.toString()))
            }
            true
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onPause() {
        super.onPause()
        SaveManager.save(this)
    }
}
