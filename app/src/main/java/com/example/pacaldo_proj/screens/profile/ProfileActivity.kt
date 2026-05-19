package com.example.pacaldo_proj.screens.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.UserSession
import com.example.pacaldo_proj.data.repositories.BarrierRepository
import com.example.pacaldo_proj.screens.login.LoginActivity
import com.example.pacaldo_proj.screens.settings.SettingsActivity
import com.example.pacaldo_proj.utils.BaseActivity
import com.example.pacaldo_proj.utils.NavHelper

class ProfileActivity : BaseActivity() {

    private val PREFS_NAME    = "profile_prefs"
    private val KEY_PHOTO_URI = "photo_uri_"   // suffixed with the user's email

    private lateinit var avatarImage: ImageView

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri == null) return@registerForActivityResult
            try {
                contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) { }
            applyPhoto(uri)
            savePhotoUri(uri)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        avatarImage = findViewById(R.id.profileAvatarImage)
        avatarImage.setColorFilter(
            androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary)
        )

        // Tap anywhere on the circle → open gallery
        findViewById<FrameLayout>(R.id.profileAvatarContainer).setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Edit profile
        findViewById<LinearLayout>(R.id.btnEditProfile).setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        // Settings
        findViewById<LinearLayout>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Logout — also wipe the saved photo so the next user starts fresh
        findViewById<LinearLayout>(R.id.buttonLogout).setOnClickListener {
            clearSavedPhoto()          // ← clears the stale SharedPreferences entry
            UserSession.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        NavHelper.setup(this, R.id.nav_profile)
    }

    // onResume refreshes all displayed data immediately after returning from
    // EditProfileActivity — fixes the "update is delayed until page switch" bug.
    override fun onResume() {
        super.onResume()
        refreshProfileUI()
        NavHelper.setup(this, R.id.nav_profile)
    }

    // ── UI refresh ────────────────────────────────────────────────────────────

    private fun refreshProfileUI() {
        val user = UserSession.currentUser

        val fullName = "${user?.firstName ?: "Guest"} ${user?.lastName ?: ""}".trim()
        findViewById<TextView>(R.id.profileFirstName).text = fullName

        val mobilityLabel = user?.mobilityType?.displayName ?: "Accessibility profile not set"
        findViewById<TextView>(R.id.profileMobilityType).text = "  $mobilityLabel  "

        // Reset tint before loading so the icon placeholder always looks right
        avatarImage.setColorFilter(
            androidx.core.content.ContextCompat.getColor(this, R.color.colorPrimary)
        )
        loadSavedPhoto()

        findViewById<TextView>(R.id.profileEmail).text =
            user?.email ?: "No email provided"
        findViewById<TextView>(R.id.profilePhone).text =
            if (user?.phoneNumber.isNullOrBlank()) "Phone not provided" else user?.phoneNumber

        val emergencyText = when {
            user?.emergencyContactName.isNullOrBlank() ->
                "No emergency contact set.\nAdd one by creating a new account."
            else ->
                "${user?.emergencyContactName}\n${user?.emergencyContactPhone ?: "No number"}"
        }
        findViewById<TextView>(R.id.profileEmergencyContact).text = emergencyText

        val myCount = BarrierRepository.getAllReports()
            .count { it.reportedBy == user?.firstName }
        findViewById<TextView>(R.id.tvMyReports).text =
            if (myCount == 0) "You haven't submitted any reports yet."
            else "You have submitted $myCount community report${if (myCount > 1) "s" else ""}. Thank you!"
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun applyPhoto(uri: Uri) {
        try {
            val bitmap = contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream)
            } ?: return

            val circular = RoundedBitmapDrawableFactory.create(resources, bitmap).apply {
                isCircular = true
            }
            avatarImage.setImageDrawable(circular)
            avatarImage.clearColorFilter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun savePhotoUri(uri: Uri) {
        val key = KEY_PHOTO_URI + (UserSession.currentUser?.email ?: "guest")
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .edit().putString(key, uri.toString()).apply()
    }

    private fun loadSavedPhoto() {
        val key = KEY_PHOTO_URI + (UserSession.currentUser?.email ?: "guest")
        val uriString = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .getString(key, null) ?: return
        try {
            applyPhoto(Uri.parse(uriString))
        } catch (e: Exception) {
            e.printStackTrace()
            getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().remove(key).apply()
        }
    }

    /** Remove the photo preference for the current user on logout. */
    private fun clearSavedPhoto() {
        val key = KEY_PHOTO_URI + (UserSession.currentUser?.email ?: "guest")
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().remove(key).apply()
    }
}