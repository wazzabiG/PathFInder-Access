package com.example.pacaldo_proj.screens.register

import com.example.pacaldo_proj.data.models.MobilityType
import com.example.pacaldo_proj.data.models.User

interface RegisterContract {
    interface View {
        fun showRegistrationSuccess(user: User)
        fun showRegistrationError(message: String)
    }
    interface Presenter {
        fun attemptRegister(
            firstName: String, lastName: String, email: String,
            phone: String, mobilityType: MobilityType,
            emergencyName: String, emergencyPhone: String,
            pass: String, repeatPass: String
        )
    }
}