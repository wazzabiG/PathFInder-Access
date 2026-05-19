package com.example.pacaldo_proj.data.models

data class AccessibleResource(
    val name: String,
    val type: ResourceType,
    val address: String,
    val description: String,
    val features: List<String> = emptyList(),
    val isVerified: Boolean = true,
    val phone: String? = null
)

enum class ResourceType(val displayName: String, val emoji: String) {
    HOSPITAL("Hospital / Clinic", "🏥"),
    PHARMACY("Pharmacy", "💊"),
    RESTROOM("Accessible Restroom", "🚻"),
    PARKING("Accessible Parking", "🅿️"),
    TRANSPORT("Accessible Transport Hub", "🚌"),
    RAMP("Ramp / Elevator Access", "♿"),
    SHELTER("Rest Area / Shelter", "🏠")
}