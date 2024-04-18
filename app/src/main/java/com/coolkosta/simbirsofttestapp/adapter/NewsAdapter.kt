package com.coolkosta.simbirsofttestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.util.Event
import com.coolkosta.simbirsofttestapp.util.ImageResource

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder>() {
    private var items: List<Event> = listOf()
    var onItemClick: ((Event) -> Unit)? = null
    fun submitList(list: List<Event>): List<Event> {
        items = list
        return items
    }

    inner class NewsItemViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.news_event_iv)
        private val title: TextView = view.findViewById(R.id.news_title_tv)
        private val eventDescription: TextView = view.findViewById(R.id.news_description_tv)
        private val eventDate: TextView = view.findViewById(R.id.news_datetime_tv)

        fun bind(item: Event) {
            title.text = item.title
            eventDescription.text = item.description
            val imageResource = ImageResource.from(item.imageName)
            imageView.setImageResource(imageResource.resourceId)
            eventDate.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_news_card, parent, false)
        return NewsItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val content = items[holder.bindingAdapterPosition]
        holder.bind(content)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(content)
        }
    }
}