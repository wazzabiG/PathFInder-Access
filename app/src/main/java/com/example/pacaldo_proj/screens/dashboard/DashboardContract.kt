package com.example.pacaldo_proj.screens.dashboard

import com.example.pacaldo_proj.data.models.BarrierCategory
import com.example.pacaldo_proj.data.models.BarrierReport

interface DashboardContract {

    interface View {
        fun displayAlerts(alerts: List<BarrierReport>)
        fun navigateToReportScreen()
        fun showAlertCount(count: Int)
    }

    interface Presenter {
        fun loadAlerts(category: BarrierCategory? = null)
        fun onReportBarrierClicked()
        fun upvoteAlert(reportId: String)
        fun downvoteAlert(reportId: String)
        fun onDestroy()
    }
}