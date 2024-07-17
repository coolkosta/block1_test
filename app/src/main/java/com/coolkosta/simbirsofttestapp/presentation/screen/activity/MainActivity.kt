package com.coolkosta.simbirsofttestapp.presentation.screen.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.coolkosta.profile.presentation.screen.ProfileFragment
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.presentation.screen.helpFragment.HelpFragment
import com.coolkosta.simbirsofttestapp.presentation.screen.loginFragment.LoginScreenFragment
import com.coolkosta.simbirsofttestapp.presentation.screen.newsFragment.NewsFragment
import com.coolkosta.simbirsofttestapp.presentation.screen.searchByEventFragment.SearchFragment
import com.coolkosta.simbirsofttestapp.util.EventFlow
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.help
            bottomNavigationView.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginScreenFragment.newInstance()).commit()
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
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }
        getUnreadCountEvent()
    }

    private fun getUnreadCountEvent() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                EventFlow.getEvents().collect {
                    updateUnreadCountBadge(it)
                }
            }
        }
    }

    private fun updateUnreadCountBadge(unreadCount: Int) {
        val badge: BadgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.news)
        badge.number = unreadCount
        badge.isVisible = unreadCount > 0
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
