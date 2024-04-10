package com.coolkosta.simbirsofttestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.util.NewsItem

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder>() {

    val list: List<NewsItem> = listOf(
        NewsItem(
            imageId = R.drawable.img_child,
            title = "Конкурс по вокальному пению в детском доме №6",
            description = "Дубовская школа-интернат для детей\n" +
                    "с ограниченными возможностями здоровья стала первой в области …",
            dateTime = "Октябрь 20, 2016"
        ),
    )

    inner class NewsItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.news_event_iv)
        private val title: TextView = view.findViewById(R.id.news_title_tv)
        private val eventDescription: TextView = view.findViewById(R.id.news_description_tv)
        private val eventDate: TextView = view.findViewById(R.id.news_datetime_tv)
        fun bind(newsItem: NewsItem) {
            imageView.setImageResource(newsItem.imageId)
            title.text = newsItem.title
            eventDescription.text = newsItem.description
            eventDate.text = newsItem.dateTime
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
        return list.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val content = list[holder.bindingAdapterPosition]
        holder.bind(content)
    }

}