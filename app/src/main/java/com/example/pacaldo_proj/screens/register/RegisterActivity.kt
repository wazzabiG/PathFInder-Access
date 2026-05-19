package com.example.pacaldo_proj.screens.register

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.MobilityType
import com.example.pacaldo_proj.data.models.User
import com.example.pacaldo_proj.data.repositories.UserRepository
import com.example.pacaldo_proj.screens.dashboard.DashboardActivity
import com.example.pacaldo_proj.screens.login.LoginActivity
import com.example.pacaldo_proj.utils.BaseActivity

class RegisterActivity : BaseActivity(), RegisterContract.View {

    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this)

        val mobilityTypes = MobilityType.values()
        val spinnerMobility = findViewById<Spinner>(R.id.spinnerMobilityType)
        spinnerMobility.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, mobilityTypes.map { it.displayName }
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        findViewById<Button>(R.id.buttonRegisterSubmit).setOnClickListener {
            presenter.attemptRegister(
                firstName     = findViewById<EditText>(R.id.etFirstName).text.toString(),
                lastName      = findViewById<EditText>(R.id.etLastName).text.toString(),
                email         = findViewById<EditText>(R.id.etEmail).text.toString(),
                phone         = findViewById<EditText>(R.id.etPhone).text.toString(),
                mobilityType  = mobilityTypes[spinnerMobility.selectedItemPosition],
                emergencyName = findViewById<EditText>(R.id.etEmergencyName).text.toString(),
                emergencyPhone= findViewById<EditText>(R.id.etEmergencyPhone).text.toString(),
                pass          = findViewById<EditText>(R.id.etPassword).text.toString(),
                repeatPass    = findViewById<EditText>(R.id.etRepeatPassword).text.toString()
            )
        }
        findViewById<TextView>(R.id.tvGoToLogin).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun showRegistrationSuccess(user: User) {
        Toast.makeText(this, "Welcome, ${user.firstName}! Your account is ready.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun showRegistrationError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}