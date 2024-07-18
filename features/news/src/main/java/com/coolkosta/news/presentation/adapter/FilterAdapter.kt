package com.coolkosta.news.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.news.R


import com.coolkosta.news.domain.model.CategoryEntity
import com.google.android.material.switchmaterial.SwitchMaterial

class FilterAdapter(
    private val listener: (Int, Boolean) -> Unit,
) :
    RecyclerView.Adapter<FilterAdapter.FilterItemViewHolder>() {

    private var items: List<CategoryEntity> = listOf()
    private var filterList: List<Int> = listOf()

    fun submitList(newItems: List<CategoryEntity>, newFilterList: List<Int>) {
        val diffUtilCallback = DiffUtilCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        items = newItems
        filterList = newFilterList
        diffResult.dispatchUpdatesTo(this)
    }


    inner class DiffUtilCallback(
        private val oldItems: List<CategoryEntity>,
        private val newItems: List<CategoryEntity>,

        ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id == newItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }

    }

    inner class FilterItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.filter_item_tv)
        private val switcher: SwitchMaterial = view.findViewById(R.id.filter_check)
        private val div: View = view.findViewById(R.id.item_div)
        fun bind(categoryEntity: CategoryEntity) {
            switcher.isChecked = filterList.contains(categoryEntity.id)
            switcher.setOnCheckedChangeListener { _, isChecked ->
                listener(categoryEntity.id, isChecked)
            }
            title.text = categoryEntity.title
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
