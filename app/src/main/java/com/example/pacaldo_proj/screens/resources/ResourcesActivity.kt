package com.example.pacaldo_proj.screens.resources

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.ResourceType
import com.example.pacaldo_proj.data.repositories.ResourceRepository
import com.example.pacaldo_proj.utils.BaseActivity
import com.example.pacaldo_proj.utils.NavHelper

class ResourcesActivity : BaseActivity() {

    private lateinit var adapter: ResourcesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)

        val rv = findViewById<RecyclerView>(R.id.rvResources)
        adapter = ResourcesAdapter(ResourceRepository.getAll())
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // Search
        findViewById<EditText>(R.id.etSearch).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.updateData(
                    if (s.isNullOrBlank()) ResourceRepository.getAll()
                    else ResourceRepository.search(s.toString())
                )
            }
        })

        // Type filter chips
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupResourceFilter)

        val allChip = Chip(this).apply {
            text = "All"
            isCheckable = true
            isChecked = true
            setOnClickListener { adapter.updateData(ResourceRepository.getAll()) }
        }
        chipGroup.addView(allChip)

        ResourceType.values().forEach { type ->
            val chip = Chip(this).apply {
                text = "${type.emoji}  ${type.displayName}"
                isCheckable = true
                textSize = 13f
                setOnClickListener { adapter.updateData(ResourceRepository.getByType(type)) }
            }
            chipGroup.addView(chip)
        }

        NavHelper.setup(this, R.id.nav_resources)
    }

    override fun onResume() {
        super.onResume()
        NavHelper.setup(this, R.id.nav_resources)
    }
}