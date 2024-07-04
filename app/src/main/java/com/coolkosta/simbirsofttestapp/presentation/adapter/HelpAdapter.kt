package com.coolkosta.simbirsofttestapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.domain.model.HelpItem

class HelpAdapter(private var items: List<HelpItem>) : RecyclerView.Adapter<HelpAdapter.HelpItemViewHolder>() {

    fun updateList(newItems: List<HelpItem>) {

        val diffUtilCallback = DiffUtilCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

        this.items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    inner class DiffUtilCallback(
        private val oldItems: List<HelpItem>,
        private val newItems: List<HelpItem>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
           return true
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }

    inner class HelpItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.icon_iv)
        private val tittle: TextView = view.findViewById(R.id.card_label_tv)

        fun bind(helpItem: HelpItem) {
            image.setImageResource(helpItem.imageId)
            tittle.setText(helpItem.title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_help_card, parent, false)
        return HelpItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HelpItemViewHolder, position: Int) {
        val content = items[holder.bindingAdapterPosition]
        holder.bind(content)
    }
}
