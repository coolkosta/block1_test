package com.coolkosta.simbirsofttestapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.fragment.HelpFragment
import com.coolkosta.simbirsofttestapp.fragment.LoginScreenFragment
import com.coolkosta.simbirsofttestapp.fragment.NewsFragment
import com.coolkosta.simbirsofttestapp.fragment.ProfileFragment
import com.coolkosta.simbirsofttestapp.fragment.SearchFragment
import com.coolkosta.simbirsofttestapp.util.EventFlow
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private val disposables = CompositeDisposable()
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

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
        getUnreadCountEvent(dispatcher)
    }

    private fun getUnreadCountEvent(dispatcher: CoroutineDispatcher) {
        lifecycleScope.launch(dispatcher) {
            EventFlow.listen()
                .collect { count ->
                        updateUnreadCountBadge(count)
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
