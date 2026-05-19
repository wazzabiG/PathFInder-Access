package com.example.pacaldo_proj.utils

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {

    fun applyDarkMode(context: Context) {
        val prefs = context.getSharedPreferences("pathfinder_prefs", Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    // Helper to get the target scale based on user preference
    fun getPreferredFontScale(context: Context): Float {
        val prefs = context.getSharedPreferences("pathfinder_prefs", Context.MODE_PRIVATE)
        val level = prefs.getInt("text_size", 1)
        return when (level) {
            0 -> 0.85f
            1 -> 1.0f
            2 -> 1.15f
            3 -> 1.30f
            else -> 1.0f
        }
    }

    fun applyFontScale(context: Context): Context {
        val scale = getPreferredFontScale(context)
        val config = Configuration(context.resources.configuration)
        config.fontScale = scale
        return context.createConfigurationContext(config)
    }
}