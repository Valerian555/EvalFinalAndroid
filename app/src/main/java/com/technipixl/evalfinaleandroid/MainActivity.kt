package com.technipixl.evalfinaleandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.technipixl.evalfinaleandroid.databinding.ActivityMainBinding
import com.technipixl.evalfinaleandroid.ui.search.SearchFragment
import com.technipixl.evalfinaleandroid.ui.trending.TrendingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        }

        override fun onStart() {
            super.onStart()

            //gestion de ma bottombar
            val bottomNavigationView = binding.myBottombar
            val navController = findNavController(R.id.nav_host_fragment)
            NavigationUI.setupWithNavController(bottomNavigationView, navController)
        }
    }
