package com.example.pacaldo_proj.screens.report

import com.example.pacaldo_proj.data.models.BarrierCategory
import com.example.pacaldo_proj.data.models.BarrierReport
import com.example.pacaldo_proj.data.models.UserSession
import com.example.pacaldo_proj.data.repositories.BarrierRepository

class ReportPresenter(private var view: ReportContract.View?) : ReportContract.Presenter {

    override fun submitBarrierReport(location: String, description: String, category: BarrierCategory) {
        if (location.trim().isEmpty()) {
            view?.showValidationError("Please enter a location.")
            return
        }
        if (description.trim().isEmpty()) {
            view?.showValidationError("Please describe the barrier.")
            return
        }
        val report = BarrierReport(
            location    = location.trim(),
            description = description.trim(),
            category    = category,
            reportedBy  = UserSession.currentUser?.firstName ?: "Anonymous"
        )
        BarrierRepository.addReport(report)
        view?.showSubmissionSuccess()
    }

    override fun onDestroy() {
        view = null
    }
}