package com.coolkosta.simbirsofttestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.util.Generator
import com.coolkosta.simbirsofttestapp.entity.HelpItem

class HelpAdapter : RecyclerView.Adapter<HelpAdapter.HelpItemViewHolder>() {

    private val items: List<HelpItem> = Generator().generateHelpList()

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
