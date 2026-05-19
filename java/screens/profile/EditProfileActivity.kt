package com.example.pacaldo_proj.screens.profile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.MobilityType
import com.example.pacaldo_proj.data.models.UserSession
import com.example.pacaldo_proj.utils.BaseActivity

class EditProfileActivity : BaseActivity() {

    // Track the currently selected MobilityType (not just its display name)
    private var selectedMobilityType: MobilityType = MobilityType.UNSPECIFIED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val user = UserSession.currentUser

        val editFirst          = findViewById<EditText>(R.id.editFirstName)
        val editLast           = findViewById<EditText>(R.id.editLastName)
        val editEmail          = findViewById<EditText>(R.id.editEmail)
        val editPhone          = findViewById<EditText>(R.id.editPhone)
        val editEmergencyName  = findViewById<EditText>(R.id.editEmergencyName)
        val editEmergencyPhone = findViewById<EditText>(R.id.editEmergencyPhone)
        val tvMobility         = findViewById<TextView>(R.id.editMobilityType)

        // 1. Populate current data
        editFirst.setText(user?.firstName)
        editLast.setText(user?.lastName)
        editEmail.setText(user?.email)
        editPhone.setText(user?.phoneNumber)
        editEmergencyName.setText(user?.emergencyContactName)
        editEmergencyPhone.setText(user?.emergencyContactPhone)

        // Initialise the selected mobility from the current user value
        selectedMobilityType = user?.mobilityType ?: MobilityType.UNSPECIFIED
        tvMobility.text = selectedMobilityType.displayName

        // 2. Mobility picker — all categories including new options
        tvMobility.setOnClickListener {
            val allTypes = MobilityType.entries.toTypedArray()
            val options  = allTypes.map { it.displayName }.toTypedArray()

            AlertDialog.Builder(this)
                .setTitle("Select Accessibility Profile")
                .setItems(options) { _, which ->
                    selectedMobilityType = allTypes[which]
                    tvMobility.text = selectedMobilityType.displayName
                }
                .show()
        }

        // 3. Save — write all fields including mobilityType back to UserSession
        findViewById<Button>(R.id.btnSaveChanges).setOnClickListener {
            if (user != null) {
                user.firstName             = editFirst.text.toString().trim()
                user.lastName              = editLast.text.toString().trim()
                user.email                 = editEmail.text.toString().trim()
                user.phoneNumber           = editPhone.text.toString().trim()
                user.emergencyContactName  = editEmergencyName.text.toString().trim()
                user.emergencyContactPhone = editEmergencyPhone.text.toString().trim()
                user.mobilityType          = selectedMobilityType   // ← was missing before

                Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
