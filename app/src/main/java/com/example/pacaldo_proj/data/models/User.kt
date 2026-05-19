package com.example.pacaldo_proj.data.models

data class User(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phoneNumber: String? = null,
    var username: String = "",
    var password: String = "",
    var mobilityType: MobilityType = MobilityType.UNSPECIFIED,
    var emergencyContactName: String? = null,
    var emergencyContactPhone: String? = null
)