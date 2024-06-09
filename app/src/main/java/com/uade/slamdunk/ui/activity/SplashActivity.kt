package com.uade.slamdunk.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.uade.slamdunk.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Check if this is the first launch
        val isFirstLaunch = isFirstLaunch()

        // If it's the first launch, show the splash screen with delay
        if (isFirstLaunch) {
            navigateToNextActivityDelayed()
        } else {
            // If it's not the first launch, directly navigate to the next activity
            navigateToNextActivity()
        }

    }

    private fun navigateToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }

    private fun navigateToMainContent() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }

    private fun navigateToNextActivity() {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            // User is already logged in, navigate to main content
            navigateToMainContent()
        } else {
            // User is not logged in, show login screen
            navigateToLogin()
        }
    }

    private fun navigateToNextActivityDelayed() {
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextActivity()
        }, 4000)
    }

    private fun isFirstLaunch(): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
        if (isFirstLaunch) {
            // Set the flag to false to indicate that it's not the first launch anymore
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
        }
        return isFirstLaunch
    }
}