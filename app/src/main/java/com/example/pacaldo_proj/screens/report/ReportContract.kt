package com.example.pacaldo_proj.screens.report

import com.example.pacaldo_proj.data.models.BarrierCategory

interface ReportContract {
    interface View {
        fun showSubmissionSuccess()
        fun showValidationError(message: String)
    }
    interface Presenter {
        fun submitBarrierReport(location: String, description: String, category: BarrierCategory)
        fun onDestroy()
    }
}