package com.example.moodtunes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide action bar for a clean splash
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()

        // Short delay to show splash, then navigate based on auth state
        Handler(Looper.getMainLooper()).postDelayed({
            val destination = if (auth.currentUser != null) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            destination.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(destination)
        }, 1000L)
    }
}
