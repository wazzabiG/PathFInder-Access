package com.example.pacaldo_proj.screens.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.Comment
import com.example.pacaldo_proj.utils.DateUtils

class CommentsAdapter(
    private val comments: MutableList<Comment>
) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAuthor: TextView    = view.findViewById(R.id.tvCommentAuthor)
        val tvText: TextView      = view.findViewById(R.id.tvCommentText)
        val tvTimestamp: TextView = view.findViewById(R.id.tvCommentTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.tvAuthor.text    = comment.author
        holder.tvText.text      = comment.text
        holder.tvTimestamp.text = DateUtils.formatRelative(comment.timestamp)
    }

    override fun getItemCount() = comments.size

    fun addComment(comment: Comment) {
        comments.add(comment)
        notifyItemInserted(comments.size - 1)
    }
}