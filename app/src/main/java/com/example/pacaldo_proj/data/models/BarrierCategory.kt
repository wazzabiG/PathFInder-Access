package com.example.pacaldo_proj.data.models

enum class BarrierCategory(val displayName: String, val emoji: String) {
    ELEVATOR("Broken Elevator", "🛗"),
    RAMP("Damaged Ramp", "⚠️"),
    PATHWAY("Blocked Pathway", "🚧"),
    ENTRANCE("Inaccessible Entrance", "🚪"),
    SIDEWALK("Damaged Sidewalk", "🛤️"),
    SIGNAL("Missing Audio/Visual Signal", "🔔"),
    RESTROOM("Inaccessible Restroom", "🚻"),
    PARKING("No Accessible Parking", "🅿️"),
    OTHER("Other", "📍")
}