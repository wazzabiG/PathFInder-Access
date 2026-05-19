package com.example.pacaldo_proj.screens.emergency

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.UserSession
import com.example.pacaldo_proj.utils.BaseActivity
import com.example.pacaldo_proj.utils.NavHelper

class EmergencyActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        val user = UserSession.currentUser

        // Personal emergency contact
        val tvContactName  = findViewById<TextView>(R.id.tvEmergencyContactName)
        val tvContactPhone = findViewById<TextView>(R.id.tvEmergencyContactPhone)
        val btnCallPersonal = findViewById<LinearLayout>(R.id.btnCallPersonal)

        if (!user?.emergencyContactName.isNullOrBlank()) {
            tvContactName.text  = user?.emergencyContactName
            tvContactPhone.text = user?.emergencyContactPhone ?: "No number provided"
            val hasPhone = !user?.emergencyContactPhone.isNullOrBlank()
            btnCallPersonal.alpha = if (hasPhone) 1f else 0.5f
            if (hasPhone) {
                btnCallPersonal.setOnClickListener {
                    callNumber(user?.emergencyContactPhone!!)
                }
            }
        } else {
            tvContactName.text  = "No emergency contact set"
            tvContactPhone.text = "Add one in your Profile screen"
            btnCallPersonal.alpha = 0.5f
        }

        // National hotlines — now LinearLayouts acting as buttons
        findViewById<LinearLayout>(R.id.btnCall911).setOnClickListener { callNumber("911") }
        findViewById<LinearLayout>(R.id.btnCallRed).setOnClickListener { callNumber("143") }
        findViewById<LinearLayout>(R.id.btnCallPNP).setOnClickListener { callNumber("117") }
        findViewById<LinearLayout>(R.id.btnCallBFP).setOnClickListener { callNumber("160") }

        NavHelper.setup(this, R.id.nav_emergency)
    }

    private fun callNumber(number: String) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")))
    }

    override fun onResume() {
        super.onResume()
        NavHelper.setup(this, R.id.nav_emergency)
    }
}
