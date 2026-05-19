package com.example.pacaldo_proj.screens.login

import com.example.pacaldo_proj.data.models.User
import com.example.pacaldo_proj.data.models.UserSession
import com.example.pacaldo_proj.data.repositories.UserRepository

class LoginPresenter(private var view: LoginContract.View?) : LoginContract.Presenter {

    override fun attemptLogin(firstName: String, pass: String) {
        if (firstName.trim().isEmpty() || pass.trim().isEmpty()) {
            view?.showLoginError("Fields cannot be empty")
            return
        }

        // This is the exact line where it likely crashes if UserRepository is broken
        val foundUser = UserRepository.authenticate(firstName, pass)

        if (foundUser != null) {
            UserSession.currentUser = foundUser
            view?.showLoginSuccess(foundUser) // This triggers the move to Dashboard
        } else {
            view?.showLoginError("Account not found. Please register first.")
        }
    }

    override fun onDestroy() {
        view = null // Prevents memory leaks
    }
}