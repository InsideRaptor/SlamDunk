package com.uade.slamdunk.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uade.slamdunk.R
import com.uade.slamdunk.ui.viewmodel.MainActivityViewModel
import com.uade.slamdunk.ui.fragment.BookmarkFragment
import com.uade.slamdunk.ui.fragment.HomeFragment
import com.uade.slamdunk.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(HomeFragment())

        bottomNavBar()

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        //viewModel.init()
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
                R.id.menu_search -> {
                    loadFragment(SearchFragment())
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

}