package com.coolkosta.simbirsofttestapp.presentation.screen.activity

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.coolkosta.core.util.Constants.EVENT_EXTRA
import com.coolkosta.help.presentation.screen.HelpFragment
import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.news.presentation.screen.eventDetailFragment.EventDetailFragment
import com.coolkosta.news.presentation.screen.newsFragment.NewsFragmentComposable
import com.coolkosta.news.util.EventFlow
import com.coolkosta.profile.presentation.screen.ProfileFragment
import com.coolkosta.search.presentation.screen.SearchFragment
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.presentation.screen.loginFragment.LoginFragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            openDetailNewsFragment(
                IntentCompat.getParcelableExtra(
                    it,
                    EVENT_EXTRA,
                    EventEntity::class.java
                ) as EventEntity
            )

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.help
            bottomNavigationView.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    LoginFragment.newInstance()
                ).commit()
        }

        IntentCompat.getParcelableExtra(intent, EVENT_EXTRA, EventEntity::class.java)?.let {
            openDetailNewsFragment(it)
        }


        getRequestPermissions()

        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.news -> NewsFragmentComposable.newInstance()
                R.id.search -> SearchFragment.newInstance()
                R.id.help -> HelpFragment.newInstance()
                R.id.profile -> ProfileFragment.newInstance()
                else -> null
            }

            fragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }
        getUnreadCountEvent()
    }

    private fun getRequestPermissions() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        1
                    )
                }
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val alarmManager =
                    applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                if (!alarmManager.canScheduleExactAlarms()) {
                    Intent().also { intent ->
                        intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                        this.startActivity(intent)
                    }
                }
            }
        }
    }

    private fun openDetailNewsFragment(event: EventEntity) {
        bottomNavigationView.selectedItemId = R.id.news
        bottomNavigationView.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, EventDetailFragment.newInstance(event))
            .commit()
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
}

