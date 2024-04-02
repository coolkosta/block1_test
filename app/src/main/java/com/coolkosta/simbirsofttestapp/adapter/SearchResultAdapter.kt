package com.coolkosta.simbirsofttestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R

class SearchResultAdapter(private var items: List<String>) :
    RecyclerView.Adapter<SearchResultAdapter.SearchItemViewHolder>() {

    fun updateList(newItems: List<String>) {

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newItems[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newItems[newItemPosition]
            }
        })

        this.items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    inner class SearchItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.search_item_result_tv)
        val div: View = view.findViewById(R.id.item_div)
        fun bind(title: String) {
            textView.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return SearchItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val content = items[holder.adapterPosition]
        holder.bind(content)
        if (position == items.size - 1) holder.div.visibility = View.GONE
    }
}