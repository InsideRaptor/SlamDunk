package com.uade.slamdunk.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.uade.slamdunk.R
import com.uade.slamdunk.ui.viewmodel.MainActivityViewModel
import com.uade.slamdunk.ui.fragment.BookmarkFragment
import com.uade.slamdunk.ui.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        loadFragment(HomeFragment())
        bottomNavBar()

        bindViewModel()

    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        viewModel.isLoading.observe(this) { isLoading ->
            // Show or hide the progress bar based on the isLoading flag
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.teams.observe(this) { teams ->
            // Do something with the fetched teams if needed
        }

    }

    private fun bottomNavBar() {
        bottomNav = findViewById(R.id.bottomNav)!!
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.menu_bookmark -> {
                    loadFragment(BookmarkFragment())
                    true
                }
                else -> false
            }
        }
        bottomNav.selectedItemId = R.id.menu_home
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container,fragment)
            .commit()
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}