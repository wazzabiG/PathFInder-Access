package com.example.pacaldo_proj.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.screens.dashboard.DashboardActivity
import com.example.pacaldo_proj.screens.emergency.EmergencyActivity
import com.example.pacaldo_proj.screens.profile.ProfileActivity
import com.example.pacaldo_proj.screens.resources.ResourcesActivity

object NavHelper {
    fun setup(activity: AppCompatActivity, currentNavId: Int) {
        val bottomNav = activity.findViewById<BottomNavigationView>(R.id.bottomNavigation) ?: return

        // Always sync the highlighted tab to match the current screen,
        // but suppress the listener while doing so to avoid a recursive launch.
        bottomNav.setOnItemSelectedListener(null)
        bottomNav.selectedItemId = currentNavId

        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == currentNavId) return@setOnItemSelectedListener true
            val targetClass = when (item.itemId) {
                R.id.nav_dashboard -> DashboardActivity::class.java
                R.id.nav_resources -> ResourcesActivity::class.java
                R.id.nav_emergency -> EmergencyActivity::class.java
                R.id.nav_profile   -> ProfileActivity::class.java
                else -> return@setOnItemSelectedListener false
            }
            // CLEAR_TOP + SINGLE_TOP: reuses existing instance and triggers onResume,
            // so setup() re-runs and the highlight is always correct on the way back.
            val intent = Intent(activity, targetClass).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            activity.startActivity(intent)
            true
        }
    }
}