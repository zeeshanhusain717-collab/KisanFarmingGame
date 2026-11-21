package com.kisan.farminggame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private var moved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // "Game Start" par tap karne par turant home par jao
        findViewById<TextView>(R.id.tvStart).setOnClickListener { goHome() }

        // Nahi to 2.5 second baad apne aap home par jao
        Handler(Looper.getMainLooper()).postDelayed({ goHome() }, 2500)
    }

    private fun goHome() {
        if (moved) return
        moved = true
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
