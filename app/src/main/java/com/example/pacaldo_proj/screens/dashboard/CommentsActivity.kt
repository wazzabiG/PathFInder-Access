package com.example.pacaldo_proj.screens.dashboard

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.Comment
import com.example.pacaldo_proj.data.models.UserSession
import com.example.pacaldo_proj.data.repositories.BarrierRepository
import com.example.pacaldo_proj.utils.BaseActivity

class CommentsActivity : BaseActivity() {

    companion object {
        const val EXTRA_REPORT_ID = "report_id"
    }

    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var reportId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        reportId = intent.getStringExtra(EXTRA_REPORT_ID) ?: run { finish(); return }

        val report = BarrierRepository.getAllReports().find { it.id == reportId } ?: run { finish(); return }

        // Header info
        findViewById<TextView>(R.id.tvCommentsLocation).text = report.location
        findViewById<TextView>(R.id.tvCommentsDescription).text = report.description

        // Back button
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        // RecyclerView
        val rv = findViewById<RecyclerView>(R.id.rvComments)
        commentsAdapter = CommentsAdapter(BarrierRepository.getComments(reportId).toMutableList())
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = commentsAdapter

        // Post comment
        val etComment = findViewById<EditText>(R.id.etComment)
        findViewById<ImageButton>(R.id.btnPostComment).setOnClickListener {
            val text = etComment.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, "Write something first!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val author = UserSession.currentUser?.firstName ?: "Anonymous"
            val comment = Comment(author = author, text = text)
            BarrierRepository.addComment(reportId, comment)
            commentsAdapter.addComment(comment)
            etComment.text.clear()
            rv.scrollToPosition(commentsAdapter.itemCount - 1)
        }
    }
}