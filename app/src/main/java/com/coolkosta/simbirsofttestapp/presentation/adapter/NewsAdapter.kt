package com.coolkosta.simbirsofttestapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.data.source.local.entity.EventEntity
import com.coolkosta.simbirsofttestapp.domain.model.Event

import com.coolkosta.simbirsofttestapp.util.ImageResource
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayAt

class NewsAdapter(
    private var onItemClick: ((Event) -> Unit),
) : RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder>() {

    private var items: List<Event> = listOf()

    fun submitList(newItems: List<Event>) {
        val diffUtilCallback = DiffUtilCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    inner class DiffUtilCallback(
        private val oldItems: List<Event>,
        private val newItems: List<Event>,
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

    inner class NewsItemViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.news_event_iv)
        private val title: TextView = view.findViewById(R.id.news_title_tv)
        private val eventDescription: TextView = view.findViewById(R.id.news_description_tv)
        private val eventDate: TextView = view.findViewById(R.id.news_datetime_tv)

        fun bind(item: Event) {
            title.text = item.title
            eventDescription.text = item.description
            when (val imageResource = ImageResource.from(item.imageName)) {
                ImageResource.DEFAULT_IMAGE -> {
                    Glide
                        .with(itemView.context)
                        .load(item.imageName)
                        .placeholder(R.drawable.ic_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageView)
                }

                else -> {
                    Glide
                        .with(itemView.context)
                        .load(imageResource.resourceId)
                        .placeholder(R.drawable.ic_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageView)
                }
            }

            val today: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
            val eventDay: LocalDate = LocalDate.parse(item.date)
            when (val daysLeft = today.daysUntil(eventDay)) {
                in 0..20 -> eventDate.text =
                    itemView.context.getString(
                        R.string.daytime_text_with_left_days,
                        daysLeft,
                        eventDay.toString()
                    )

                else -> eventDate.text = itemView.context.getString(
                    R.string.daytime_text_without_left_days,
                    eventDay.toString()
                )
            }
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
            onItemClick.invoke(content)
        }
    }
}