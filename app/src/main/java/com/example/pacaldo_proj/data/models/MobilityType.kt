package com.example.pacaldo_proj.data.models

enum class MobilityType(val displayName: String) {
    UNSPECIFIED("Prefer not to say"),
    WHEELCHAIR("Wheelchair User"),
    CRUTCHES("Crutches / Walker"),
    VISUAL("Visual Impairment"),
    HEARING("Hearing Impairment"),
    ELDERLY("Senior / Elderly"),
    INJURY("Temporary Injury"),
    OTHER("Other")
}