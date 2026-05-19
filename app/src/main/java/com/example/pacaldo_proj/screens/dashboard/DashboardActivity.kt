package com.example.pacaldo_proj.screens.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.BarrierCategory
import com.example.pacaldo_proj.data.models.BarrierReport
import com.example.pacaldo_proj.data.models.UserSession
import com.example.pacaldo_proj.data.repositories.BarrierRepository
import com.example.pacaldo_proj.screens.emergency.EmergencyActivity
import com.example.pacaldo_proj.screens.report.ReportActivity
import com.example.pacaldo_proj.utils.BaseActivity
import com.example.pacaldo_proj.utils.NavHelper

class DashboardActivity : BaseActivity(), DashboardContract.View {

    private lateinit var presenter: DashboardContract.Presenter
    private lateinit var alertsAdapter: AlertsAdapter
    private lateinit var rvAlerts: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var tvAlertBadge: TextView
    private var currentFilter: BarrierCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        presenter = DashboardPresenter(this)

        val displayName = UserSession.currentUser?.firstName ?: "User"
        findViewById<TextView>(R.id.textviewWelcome).text = "Welcome, $displayName"

        tvAlertBadge = findViewById(R.id.tvAlertBadge)
        tvEmpty = findViewById(R.id.tvEmpty)

        // RecyclerView
        rvAlerts = findViewById(R.id.rvAlerts)
        alertsAdapter = AlertsAdapter(
            alerts = emptyList(),
            onUpvote = { id -> presenter.upvoteAlert(id) },
            onDownvote = { id ->
                BarrierRepository.downvoteReport(id)
                presenter.loadAlerts(currentFilter)
            },
            onViewComments = { id ->
                val intent = Intent(this, CommentsActivity::class.java)
                intent.putExtra(CommentsActivity.EXTRA_REPORT_ID, id)
                startActivity(intent)
            }
        )
        rvAlerts.layoutManager = LinearLayoutManager(this)
        rvAlerts.adapter = alertsAdapter

        // "All" chip
        findViewById<Chip>(R.id.chipAll).setOnClickListener { presenter.loadAlerts(null) }

        // Category chips — created dynamically
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupFilter)
        BarrierCategory.values().forEach { category ->
            val chip = Chip(this).apply {
                text = "${category.emoji}  ${category.displayName}"
                isCheckable = true
                textSize = 13f
                setOnClickListener {
                    currentFilter = category
                    presenter.loadAlerts(category)
                }
            }
            chipGroup.addView(chip)
        }

        // SOS button (now a LinearLayout)
        findViewById<LinearLayout>(R.id.btnEmergency).setOnClickListener {
            startActivity(Intent(this, EmergencyActivity::class.java))
        }

        // Report FAB
        findViewById<ExtendedFloatingActionButton>(R.id.fabReport).setOnClickListener {
            presenter.onReportBarrierClicked()
        }

        NavHelper.setup(this, R.id.nav_dashboard)
    }

    override fun onResume() {
        super.onResume()
        NavHelper.setup(this, R.id.nav_dashboard)
        presenter.loadAlerts(currentFilter)
    }

    override fun displayAlerts(alerts: List<BarrierReport>) {
        alertsAdapter.updateData(alerts)
        rvAlerts.visibility = if (alerts.isEmpty()) View.GONE else View.VISIBLE
        tvEmpty.visibility  = if (alerts.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun showAlertCount(count: Int) {
        tvAlertBadge.text = "$count active community alerts"
    }

    override fun navigateToReportScreen() {
        startActivity(Intent(this, ReportActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}