package com.example.pacaldo_proj.screens.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.User
import com.example.pacaldo_proj.data.models.UserSession
import com.example.pacaldo_proj.data.repositories.UserRepository
import com.example.pacaldo_proj.screens.dashboard.DashboardActivity
import com.example.pacaldo_proj.screens.register.RegisterActivity
import com.example.pacaldo_proj.utils.BaseActivity

class LoginActivity : BaseActivity(), LoginContract.View {

    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // If this layout is broken, it crashes here!

        presenter = LoginPresenter(this)

        // These MUST match the IDs in your XML exactly
        val etFirstName = findViewById<EditText>(R.id.etLoginFirstName)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)

        buttonLogin.setOnClickListener {
            presenter.attemptLogin(etFirstName.text.toString(), etPassword.text.toString())
        }

        tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun showLoginSuccess(user: User) {
        UserSession.currentUser = user
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showLoginError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() { /* Optional */ }
    override fun hideLoading() { /* Optional */ }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}