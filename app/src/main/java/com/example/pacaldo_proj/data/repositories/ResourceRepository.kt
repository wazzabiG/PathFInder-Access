package com.example.pacaldo_proj.data.repositories

import com.example.pacaldo_proj.data.models.AccessibleResource
import com.example.pacaldo_proj.data.models.ResourceType

object ResourceRepository {

    private val resources = listOf(
        AccessibleResource(
            name = "Chong Hua Hospital",
            type = ResourceType.HOSPITAL,
            address = "Don Marcelino Velez St, Cebu City",
            description = "Full-service hospital with wheelchair access throughout all floors.",
            features = listOf("Wheelchair Ramp", "Accessible Restrooms", "Elevator", "Disabled Parking"),
            phone = "+63322556000"
        ),
        AccessibleResource(
            name = "Vicente Sotto Memorial Medical Center",
            type = ResourceType.HOSPITAL,
            address = "B. Rodriguez St, Cebu City",
            description = "Public hospital with accessibility ramps and dedicated PWD priority lanes.",
            features = listOf("Ramp Access", "PWD Priority Lane", "Free Service"),
            phone = "+63322536041"
        ),
        AccessibleResource(
            name = "Cebu Doctor's University Hospital",
            type = ResourceType.HOSPITAL,
            address = "Osmena Blvd, Cebu City",
            description = "Private hospital with full PWD facilities and accessible emergency entry.",
            features = listOf("24/7 ER", "Elevator", "Wheelchair Ramp", "Accessible Parking"),
            phone = "+63322556622"
        ),
        AccessibleResource(
            name = "Mercury Drug — Fuente Osmena",
            type = ResourceType.PHARMACY,
            address = "Osmena Blvd, Cebu City",
            description = "Ground-floor pharmacy with step-free access and wide aisles.",
            features = listOf("Ground Floor", "Wide Aisles", "PWD Discount"),
            phone = "+632888-8799"
        ),
        AccessibleResource(
            name = "SM City Cebu — PWD Restrooms",
            type = ResourceType.RESTROOM,
            address = "North Reclamation Area, Cebu City",
            description = "Dedicated PWD restrooms located on all floors near elevator banks.",
            features = listOf("Grab Bars", "Wide Entry", "All Floors", "Attendant Available")
        ),
        AccessibleResource(
            name = "Ayala Center Cebu — PWD Restrooms",
            type = ResourceType.RESTROOM,
            address = "Archbishop Reyes Ave, Cebu City",
            description = "Clean accessible restrooms on Level 1 and Level 3 near elevators.",
            features = listOf("Grab Bars", "Level 1 & 3", "Near Elevator")
        ),
        AccessibleResource(
            name = "PWD Parking — Ayala Center Cebu",
            type = ResourceType.PARKING,
            address = "Archbishop Reyes Ave, Cebu City",
            description = "Reserved PWD parking spaces on Level B1 near the main elevator bank.",
            features = listOf("Level B1", "Near Elevator", "Wide Bays", "Free for PWD")
        ),
        AccessibleResource(
            name = "PWD Parking — SM City Cebu",
            type = ResourceType.PARKING,
            address = "North Reclamation Area, Cebu City",
            description = "Marked PWD spaces on Ground Level near the main entrance ramp.",
            features = listOf("Ground Level", "Near Entrance", "Monitored")
        ),
        AccessibleResource(
            name = "Accessible Jeepney Route 01C",
            type = ResourceType.TRANSPORT,
            address = "Carbon Market Terminal, Cebu City",
            description = "Modern jeepneys on Route 01C have low-floor entry and PWD priority seating.",
            features = listOf("Low-Floor Entry", "Priority Seating", "Audio Announcements")
        ),
        AccessibleResource(
            name = "Fuente Osmena Circle — Rest Area",
            type = ResourceType.SHELTER,
            address = "Osmena Blvd, Cebu City",
            description = "Shaded rest area with benches and a flat paved path around the rotunda.",
            features = listOf("Shaded", "Flat Pathway", "Rest Benches", "Public")
        ),
        AccessibleResource(
            name = "IT Park — Ramp & Elevator Network",
            type = ResourceType.RAMP,
            address = "Cebu IT Park, Lahug, Cebu City",
            description = "Multiple ramp access points throughout the park. Elevators inside all major BPO buildings.",
            features = listOf("Multiple Ramps", "Smooth Pavement", "Good Lighting", "24/7 Access")
        ),
        AccessibleResource(
            name = "Colon Street Bayanihan Ramp",
            type = ResourceType.RAMP,
            address = "Colon St, Cebu City",
            description = "Community-built ramp at the main Colon St crossing, suitable for wheelchairs.",
            features = listOf("Gentle Gradient", "Both Sides of Street")
        )
    )

    fun getAll(): List<AccessibleResource> = resources

    fun getByType(type: ResourceType): List<AccessibleResource> =
        resources.filter { it.type == type }

    fun search(query: String): List<AccessibleResource> {
        val q = query.lowercase()
        return resources.filter {
            it.name.lowercase().contains(q) ||
                    it.address.lowercase().contains(q) ||
                    it.description.lowercase().contains(q) ||
                    it.type.displayName.lowercase().contains(q) ||
                    it.features.any { f -> f.lowercase().contains(q) }
        }
    }
}