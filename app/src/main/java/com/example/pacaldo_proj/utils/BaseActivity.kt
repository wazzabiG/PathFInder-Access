package com.example.pacaldo_proj.utils

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        // Applies the font scale when the activity is first created
        super.attachBaseContext(ThemeHelper.applyFontScale(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.applyDarkMode(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        // Check if the current font scale matches the user's preferred scale
        val currentScale = resources.configuration.fontScale
        val preferredScale = ThemeHelper.getPreferredFontScale(this)

        // If the user changed the setting while this activity was in the background,
        // recreate it now so the new text size appears immediately.
        if (currentScale != preferredScale) {
            recreate()
        }
    }
}