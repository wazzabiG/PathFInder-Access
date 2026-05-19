package com.example.pacaldo_proj.data.models

import java.util.UUID

data class BarrierReport(
    val id: String = UUID.randomUUID().toString(),
    val location: String,
    val description: String,
    val category: BarrierCategory = BarrierCategory.OTHER,
    val reportedBy: String,
    val timestamp: Long = System.currentTimeMillis(),
    var upvotes: Int = 0,
    var downvotes: Int = 0,
    var isResolved: Boolean = false,
    val comments: MutableList<Comment> = mutableListOf()
)