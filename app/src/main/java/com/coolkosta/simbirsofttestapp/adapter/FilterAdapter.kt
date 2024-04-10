package com.coolkosta.simbirsofttestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.util.FilterItem
import com.coolkosta.simbirsofttestapp.util.Generator
import com.google.android.material.switchmaterial.SwitchMaterial

class FilterAdapter() : RecyclerView.Adapter<FilterAdapter.FilterItemViewHolder>() {

    private val items: List<FilterItem> = Generator().generateFilterList()

    inner class FilterItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.filter_item_tv)
        private val isAvailable: SwitchMaterial = view.findViewById(R.id.filter_check)
        private val div: View = view.findViewById(R.id.item_div)
        fun bind(filterItem: FilterItem) {
            title.text = filterItem.title
            isAvailable.isChecked = filterItem.isAvailable
            div.visibility =
                if (bindingAdapterPosition == items.size - 1) View.GONE else View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_news_filter_card, parent, false)
        return FilterItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        val content = items[holder.bindingAdapterPosition]
        holder.bind(content)
    }

}