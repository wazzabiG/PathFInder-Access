package com.example.pacaldo_proj.screens.resources

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pacaldo_proj.R
import com.example.pacaldo_proj.data.models.AccessibleResource

class ResourcesAdapter(private var items: List<AccessibleResource>) :
    RecyclerView.Adapter<ResourcesAdapter.ResourceViewHolder>() {

    inner class ResourceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvType: TextView        = view.findViewById(R.id.tvResourceType)
        val tvName: TextView        = view.findViewById(R.id.tvResourceName)
        val tvAddress: TextView     = view.findViewById(R.id.tvResourceAddress)
        val tvDescription: TextView = view.findViewById(R.id.tvResourceDescription)
        val tvFeatures: TextView    = view.findViewById(R.id.tvResourceFeatures)
        val btnCall: Button         = view.findViewById(R.id.btnCall)
        val btnLocation: Button     = view.findViewById(R.id.btnLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resource, parent, false)
        return ResourceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        val res = items[position]
        holder.tvType.text        = "${res.type.emoji}  ${res.type.displayName}"
        holder.tvName.text        = res.name
        holder.tvAddress.text     = "📍 ${res.address}"
        holder.tvDescription.text = res.description
        holder.tvFeatures.text    = if (res.features.isEmpty()) ""
        else res.features.joinToString(" · ") { "✓ $it" }

        // Call button
        if (res.phone != null) {
            holder.btnCall.visibility = View.VISIBLE
            holder.btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${res.phone}"))
                holder.itemView.context.startActivity(intent)
            }
        } else {
            holder.btnCall.visibility = View.GONE
        }

        // See Location button — opens Google Maps with the address as a search query
        holder.btnLocation.setOnClickListener {
            val query = Uri.encode(res.address)
            val uri = Uri.parse("geo:0,0?q=$query")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.google.android.apps.maps")
            }
            // Fall back to browser Maps if Google Maps isn't installed
            if (intent.resolveActivity(holder.itemView.context.packageManager) != null) {
                holder.itemView.context.startActivity(intent)
            } else {
                val browserUri = Uri.parse("https://maps.google.com/?q=$query")
                holder.itemView.context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
            }
        }
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<AccessibleResource>) {
        items = newItems
        notifyDataSetChanged()
    }
}