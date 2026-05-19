package com.example.pacaldo_proj.screens.dashboard

import com.example.pacaldo_proj.data.models.BarrierCategory
import com.example.pacaldo_proj.data.repositories.BarrierRepository

class DashboardPresenter(private var view: DashboardContract.View?) : DashboardContract.Presenter {

    // 1. Add this variable to remember the current filter
    private var currentCategory: BarrierCategory? = null

    override fun loadAlerts(category: BarrierCategory?) {
        // 2. Save the category every time the user taps a chip
        currentCategory = category

        val alerts = if (category == null) {
            BarrierRepository.getReports()
        } else {
            BarrierRepository.getReportsByCategory(category)
        }

        view?.displayAlerts(alerts)
        view?.showAlertCount(BarrierRepository.getReports().size)
    }

    override fun onReportBarrierClicked() {
        view?.navigateToReportScreen()
    }

    override fun upvoteAlert(reportId: String) {
        BarrierRepository.upvoteReport(reportId)
        // 3. Pass the remembered category back in so the list stays filtered
        loadAlerts(currentCategory)
    }

    // 4. Apply the exact same fix to your dislike/downvote function
    override fun downvoteAlert(reportId: String) {
        BarrierRepository.downvoteReport(reportId)
        // Pass the remembered category back in
        loadAlerts(currentCategory)
    }

    override fun onDestroy() {
        view = null
    }
}