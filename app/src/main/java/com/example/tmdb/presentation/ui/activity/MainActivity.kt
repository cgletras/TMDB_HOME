package com.example.tmdb.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import com.example.tmdb.presentation.ui.fragment.FavoriteFragment
import com.example.tmdb.presentation.ui.fragment.MenuFragment
import com.example.tmdb.presentation.ui.fragment.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navigationListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_menu -> {
                    goToFragment(MenuFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_search -> {
                    goToFragment(SearchFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_favorites -> {
                    goToFragment(FavoriteFragment.newInstance("Param1", "Param2"))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bnvMain.setOnNavigationItemSelectedListener(navigationListener)

        goToFragment(MenuFragment.newInstance())
    }

    private fun goToFragment(newInstance: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flMain, newInstance).commit()
    }
}