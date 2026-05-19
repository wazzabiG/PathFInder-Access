package com.example.pacaldo_proj.screens.login

import com.example.pacaldo_proj.data.models.User

interface LoginContract {
    interface View {
        fun showLoginSuccess(user: User)
        fun showLoginError(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun attemptLogin(firstName: String, pass: String)
        fun onDestroy()
    }
}