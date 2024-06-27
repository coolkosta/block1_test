package com.coolkosta.simbirsofttestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.entity.CategoryEntity
import com.google.android.material.switchmaterial.SwitchMaterial

class FilterAdapter(
    private val items: List<CategoryEntity>,
    private val filterList: List<Int>,
    private val listener: (Int, Boolean) -> Unit,
) :
    RecyclerView.Adapter<FilterAdapter.FilterItemViewHolder>() {

    inner class FilterItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.filter_item_tv)
        private val switcher: SwitchMaterial = view.findViewById(R.id.filter_check)
        private val div: View = view.findViewById(R.id.item_div)
        fun bind(category: CategoryEntity) {
            switcher.isChecked = filterList.contains(category.id)
            switcher.setOnCheckedChangeListener { _, isChecked ->
                listener(category.id, isChecked)
            }
            title.text = category.title
            div.visibility =
                if (bindingAdapterPosition == items.size - 1) View.GONE else View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        val view = LayoutInflater.from(parent.context)
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
