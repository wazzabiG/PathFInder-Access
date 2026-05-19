package com.example.pacaldo_proj.data.repositories

import com.example.pacaldo_proj.data.models.BarrierCategory
import com.example.pacaldo_proj.data.models.BarrierReport
import com.example.pacaldo_proj.data.models.Comment

object BarrierRepository {

    private val reports = mutableListOf<BarrierReport>().apply {
        add(BarrierReport(
            location = "Ayala Center Cebu, Level B1",
            description = "Main elevator near parking is out of service. Use the elevator near the food court as an alternative.",
            category = BarrierCategory.ELEVATOR,
            reportedBy = "Community",
            upvotes = 14,
            comments = mutableListOf(
                Comment(author = "Maria", text = "Still broken as of this morning, use the food court elevator."),
                Comment(author = "Jose", text = "Management said it'll be fixed by Friday.")
            )
        ))
        add(BarrierReport(
            location = "Mango Ave. cor. General Maxilom",
            description = "Road construction blocking the accessible ramp. Temporary pathway available but surface is uneven.",
            category = BarrierCategory.RAMP,
            reportedBy = "Community",
            upvotes = 8,
            comments = mutableListOf(
                Comment(author = "Ana", text = "The temporary pathway is really rough, be careful.")
            )
        ))
        add(BarrierReport(
            location = "SM City Cebu, Main Entrance",
            description = "Automatic sliding door is broken — manual push required. Heavy for wheelchair users and the elderly.",
            category = BarrierCategory.ENTRANCE,
            reportedBy = "Community",
            upvotes = 22
        ))
        add(BarrierReport(
            location = "Fuente Osmena Rotunda",
            description = "Broken sidewalk tiles near the west crossing. Large gaps that can catch wheelchair wheels or crutches.",
            category = BarrierCategory.SIDEWALK,
            reportedBy = "Community",
            upvotes = 5
        ))
    }

    fun getReports(): List<BarrierReport> = reports.filter { !it.isResolved }

    fun getAllReports(): List<BarrierReport> = reports.toList()

    fun getReportsByCategory(category: BarrierCategory): List<BarrierReport> =
        reports.filter { it.category == category && !it.isResolved }

    fun addReport(report: BarrierReport) {
        reports.add(0, report)
    }

    fun upvoteReport(reportId: String) {
        reports.find { it.id == reportId }?.let { foundReport ->
            foundReport.upvotes++
        }
    }

    fun downvoteReport(reportId: String) {
        reports.find { it.id == reportId }?.let { foundReport ->
            foundReport.downvotes++
        }
    }

    fun addComment(reportId: String, comment: Comment) {
        reports.find { it.id == reportId }?.comments?.add(comment)
    }

    fun getComments(reportId: String): List<Comment> =
        reports.find { it.id == reportId }?.comments ?: emptyList()

    fun markResolved(reportId: String) {
        reports.find { it.id == reportId }?.isResolved = true
    }
}