package com.coolkosta.simbirsofttestapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.fragment.HelpFragment
import com.coolkosta.simbirsofttestapp.fragment.NewsFragment
import com.coolkosta.simbirsofttestapp.fragment.ProfileFragment
import com.coolkosta.simbirsofttestapp.fragment.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.help
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelpFragment.newInstance())
                .commit()
        }
        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.news -> NewsFragment.newInstance()
                R.id.search -> SearchFragment.newInstance()
                R.id.help -> HelpFragment.newInstance()
                R.id.profile -> ProfileFragment.newInstance()
                else -> null
            }
            // Переключение фрагмента
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }
    }
}
