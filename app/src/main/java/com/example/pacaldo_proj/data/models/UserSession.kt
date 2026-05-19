package com.example.pacaldo_proj.data.models

object UserSession {
    var currentUser: User? = null

    fun logout() {
        currentUser = null
    }
}