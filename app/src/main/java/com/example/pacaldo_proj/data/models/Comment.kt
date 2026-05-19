package com.example.pacaldo_proj.data.models

import java.util.UUID

data class Comment(
    val id: String = UUID.randomUUID().toString(),
    val author: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)