package com.example.pacaldo_proj.screens.settings

import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.utils.BaseActivity
import com.example.pacaldo_proj.utils.ThemeHelper

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val prefs = getSharedPreferences("pathfinder_prefs", Context.MODE_PRIVATE)

        // --- Text Size ---
        val tvLabel = findViewById<TextView>(R.id.tvTextSizeLabel)
        val sizeLabels = listOf("Small", "Normal", "Large", "Extra Large")
        val seekbar = findViewById<SeekBar>(R.id.seekbarTextSize)

        seekbar.max = 3
        seekbar.progress = prefs.getInt("text_size", 1)
        tvLabel.text = "Text Size: ${sizeLabels[seekbar.progress]}"

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                tvLabel.text = "Text Size: ${sizeLabels[progress]}"
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {
                // Save the preference
                prefs.edit().putInt("text_size", seekbar.progress).apply()
                // Just recreate() - BaseActivity.onResume will handle the scale check
                recreate()
            }
        })

        // --- Dark Mode ---
        val switchDarkMode = findViewById<Switch>(R.id.switchDarkMode)
        switchDarkMode.isChecked = prefs.getBoolean("dark_mode", false)

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            // This applies the theme globally to the app process
            ThemeHelper.applyDarkMode(this)
            // Recreate this screen immediately to show the change
            recreate()
        }

        // --- Notifications ---
        val switchNotifications = findViewById<Switch>(R.id.switchNotifications)
        switchNotifications.isChecked = prefs.getBoolean("notifications", true)

        switchNotifications.setOnCheckedChangeListener { _, checked ->
            prefs.edit().putBoolean("notifications", checked).apply()
        }
    }
}