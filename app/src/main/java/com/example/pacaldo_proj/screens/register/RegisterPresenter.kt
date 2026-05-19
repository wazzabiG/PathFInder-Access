package com.example.pacaldo_proj.screens.register

import com.example.pacaldo_proj.data.models.MobilityType
import com.example.pacaldo_proj.data.models.User
import com.example.pacaldo_proj.data.models.UserSession
import com.example.pacaldo_proj.data.repositories.UserRepository

class RegisterPresenter(private var view: RegisterContract.View?) : RegisterContract.Presenter {

    override fun attemptRegister(
        firstName: String, lastName: String, email: String,
        phone: String, mobilityType: MobilityType,
        emergencyName: String, emergencyPhone: String,
        pass: String, repeatPass: String
    ) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            view?.showRegistrationError("Please fill in all required fields.")
            return
        }
        if (!email.contains("@") || !email.contains(".")) {
            view?.showRegistrationError("Please enter a valid email address.")
            return
        }
        if (pass.length < 6) {
            view?.showRegistrationError("Password must be at least 6 characters.")
            return
        }
        if (pass != repeatPass) {
            view?.showRegistrationError("Passwords do not match.")
            return
        }
        val newUser = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phone.ifBlank { null },
            username = firstName,
            password = pass,
            mobilityType = mobilityType,
            emergencyContactName = emergencyName.ifBlank { null },
            emergencyContactPhone = emergencyPhone.ifBlank { null }
        )
        UserRepository.addUser(newUser)
        UserSession.currentUser = newUser
        view?.showRegistrationSuccess(newUser)
    }
}