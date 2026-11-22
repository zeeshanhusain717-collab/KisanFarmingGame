package com.kisan.farminggame

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kisan.farminggame.data.GameState
import com.kisan.farminggame.data.SaveManager

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        SaveManager.load(this)

        val earned = GameState.applyOffline()
        if (earned > 0) {
            Toast.makeText(this, "Offline kamai: +$earned credits", Toast.LENGTH_LONG).show()
        }

        val nav = findViewById<BottomNavigationView>(R.id.bottomNav)

        if (savedInstanceState == null) {
            showFragment(StoryFragment())
        }

        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_khet -> showFragment(KhetFragment())
                R.id.nav_factory -> showFragment(MachineFragment())
                R.id.nav_castle -> showFragment(CastleFragment())
                R.id.nav_dukaan -> showFragment(ShopFragment())
                R.id.nav_settings -> showFragment(SettingsFragment())
                else -> showFragment(PlaceholderFragment.newInstance(item.title.toString()))
            }
            true
        }
    }

    fun openKhet() {
        findViewById<BottomNavigationView>(R.id.bottomNav).selectedItemId = R.id.nav_khet
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

    override fun onStop() {
        super.onStop()
        SaveManager.save(this)
    }
}
