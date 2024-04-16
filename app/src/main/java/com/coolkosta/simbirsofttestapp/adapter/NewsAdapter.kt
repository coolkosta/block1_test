package com.coolkosta.simbirsofttestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.util.Event

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder>() {
    private var items: List<Event> = listOf()

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
    }

    enum class ImageResource(val resourceId: Int) {
        IMAGE_CHILD(R.drawable.img_child),
        IMAGE_KID(R.drawable.image_kid),
        IMAGE_ANIMAL(R.drawable.image_animal),
        IMAGE_OLD(R.drawable.image_old),
        IMAGE_WORLD(R.drawable.image_world),
        IMAGE_RUNNER(R.drawable.image_runner),
        DEFAULT_IMAGE(R.drawable.image_charity);

        companion object {
            fun from(name: String): ImageResource {
                return when (name) {
                    "image_child" -> IMAGE_CHILD
                    "image_kid" -> IMAGE_KID
                    "image_animal" -> IMAGE_ANIMAL
                    "image_old" -> IMAGE_OLD
                    "image_world" -> IMAGE_WORLD
                    "image_runner" -> IMAGE_RUNNER
                    else -> DEFAULT_IMAGE
                }
            }
        }
    }

}