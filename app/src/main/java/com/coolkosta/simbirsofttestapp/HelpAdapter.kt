package com.coolkosta.simbirsofttestapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HelpAdapter : RecyclerView.Adapter<HelpItemViewHolder>() {

    private val items: List<HelpItem> = Generator().generateHelpList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_help_card, parent, false)
        return HelpItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HelpItemViewHolder, position: Int) {
        val content = items[holder.adapterPosition]
        holder.populate(content)
    }
}

class HelpItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val image: ImageView = view.findViewById(R.id.icon_iv)
    private val tittle: TextView = view.findViewById(R.id.card_label_tv)

    fun populate(helpItem: HelpItem) {
        image.setImageResource(helpItem.imageId)
        tittle.setText(helpItem.title)
    }
}
