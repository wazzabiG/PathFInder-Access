package com.example.pacaldo_proj.screens.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.BarrierReport
import com.example.pacaldo_proj.utils.DateUtils

class AlertsAdapter(
    private var alerts: List<BarrierReport>,
    private val onUpvote: (String) -> Unit,
    private val onDownvote: (String) -> Unit,
    private val onViewComments: (String) -> Unit
) : RecyclerView.Adapter<AlertsAdapter.AlertViewHolder>() {

    inner class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory: TextView       = view.findViewById(R.id.tvAlertCategory)
        val tvLocation: TextView       = view.findViewById(R.id.tvAlertLocation)
        val tvDescription: TextView    = view.findViewById(R.id.tvAlertDescription)
        val tvMeta: TextView           = view.findViewById(R.id.tvAlertMeta)
        val btnUpvote: Button          = view.findViewById(R.id.btnUpvote)
        val btnDownvote: Button        = view.findViewById(R.id.btnDownvote)
        val btnViewComments: LinearLayout = view.findViewById(R.id.btnViewComments)
        val tvCommentCount: TextView   = view.findViewById(R.id.tvCommentCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alert, parent, false)
        return AlertViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val report = alerts[position]
        holder.tvCategory.text    = "${report.category.emoji}  ${report.category.displayName}"
        holder.tvLocation.text    = report.location
        holder.tvDescription.text = report.description
        holder.tvMeta.text        = "Reported by ${report.reportedBy} · ${DateUtils.formatRelative(report.timestamp)}"
        holder.btnUpvote.text     = "👍  ${report.upvotes}"
        holder.btnDownvote.text   = "👎  ${report.downvotes}"

        val count = report.comments.size
        holder.tvCommentCount.text = if (count == 1) "1 comment" else "$count comments"

        holder.btnUpvote.setOnClickListener      { onUpvote(report.id) }
        holder.btnDownvote.setOnClickListener    { onDownvote(report.id) }
        holder.btnViewComments.setOnClickListener { onViewComments(report.id) }
    }

    override fun getItemCount() = alerts.size

    fun updateData(newAlerts: List<BarrierReport>) {
        alerts = newAlerts
        notifyDataSetChanged()
    }
}