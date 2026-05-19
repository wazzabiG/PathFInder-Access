package com.example.pacaldo_proj.data.repositories

import com.example.pacaldo_proj.data.models.User

object UserRepository {
    // This list resets to empty every time the app is fully closed
    private val registeredUsers = mutableListOf<User>()

    fun addUser(user: User) {
        registeredUsers.add(user)
    }

    // Authenticate using the firstName and password
    fun authenticate(firstName: String, pass: String): User? {
        val searchName = firstName.trim()
        val searchPass = pass.trim()

        return registeredUsers.find {
            it.firstName.trim().equals(searchName, ignoreCase = true) &&
                    it.password.trim() == searchPass
        }
    }
}