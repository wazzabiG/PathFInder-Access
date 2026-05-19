package com.example.pacaldo_proj.screens.report

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.BarrierCategory
import com.example.pacaldo_proj.utils.BaseActivity

class ReportActivity : BaseActivity(), ReportContract.View {

    private lateinit var presenter: ReportContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        presenter = ReportPresenter(this)

        val etLocation    = findViewById<EditText>(R.id.edittextBarrierLocation)
        val etDescription = findViewById<EditText>(R.id.edittextBarrierDescription)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)

        val categories = BarrierCategory.values()
        spinnerCategory.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item,
            categories.map { "${it.emoji}  ${it.displayName}" }
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        findViewById<Button>(R.id.buttonSubmitReport).setOnClickListener {
            presenter.submitBarrierReport(
                location    = etLocation.text.toString(),
                description = etDescription.text.toString(),
                category    = categories[spinnerCategory.selectedItemPosition]
            )
        }
    }

    override fun showSubmissionSuccess() {
        Toast.makeText(this, "Thank you! Your report helps the whole community.", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun showValidationError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}