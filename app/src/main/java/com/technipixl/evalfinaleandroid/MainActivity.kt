package com.technipixl.evalfinaleandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.technipixl.evalfinaleandroid.databinding.ActivityMainBinding
import com.technipixl.evalfinaleandroid.ui.search.SearchFragment
import com.technipixl.evalfinaleandroid.ui.trending.TrendingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //gestion de la bottombar
        binding.myBottombar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.searchFragment -> {
                    loadFragment(SearchFragment())
                    true
                }

                else -> {
                    loadFragment(TrendingFragment())
                    true
                }
            }
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment,fragment)
        transaction.commit()
    }
}