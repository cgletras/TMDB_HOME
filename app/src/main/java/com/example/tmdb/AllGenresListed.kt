package com.example.tmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_all_genres_listed.*

class AllGenresListed : AppCompatActivity() {

    val imagens = arrayOf("R.drawable.landscape-192987_640", "R.drawable.ic_menu_white_24dp", "R.drawable.ic_menu_white_24dp")

    private val mNavigationListener =
        BottomNavigationView.OnNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.navigation_menu -> {
                    goToFragment(MenuFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_search -> {
                    goToFragment(SearchFragment.newInstance("Param1", "Param2"))
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
        setContentView(R.layout.activity_all_genres_listed)
        navigationView.setOnNavigationItemSelectedListener(mNavigationListener)

        goToFragment(MenuFragment.newInstance())
    }

    private fun goToFragment(newInstance: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, newInstance).commit()
    }
}
