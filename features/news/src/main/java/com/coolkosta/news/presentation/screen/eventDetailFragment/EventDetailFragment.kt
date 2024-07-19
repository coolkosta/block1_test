package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import com.coolkosta.news.R
import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.news.util.ImageResource
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayAt

class EventDetailFragment : Fragment() {

    private lateinit var title: TextView
    private lateinit var dateTime: TextView
    private lateinit var foundation: TextView
    private lateinit var address: TextView
    private lateinit var contactInfo: TextView
    private lateinit var imageView: ImageView
    private lateinit var description: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentEventEntity =
            BundleCompat.getParcelable(
                requireArguments(),
                EVENT_DETAIL_KEY,
                EventEntity::class.java
            ) as EventEntity
        val toolbar = view.findViewById<Toolbar>(R.id.event_detail_toolbar).apply {
            setNavigationIcon(com.coolkosta.core.R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_share -> {
                        true
                    }

                    else -> false
                }
            }
        }

        title = view.findViewById(R.id.event_title_tv)
        dateTime = view.findViewById(R.id.event_datetime_tv)
        foundation = view.findViewById(R.id.foundation_tv)
        address = view.findViewById(R.id.address_tv)
        contactInfo = view.findViewById(R.id.contact_phone_tv)
        imageView = view.findViewById(R.id.card_image_1)
        description = view.findViewById(R.id.event_description_tv)

        currentEventEntity.let {
            toolbar.title = currentEventEntity.title
            title.text = currentEventEntity.title

            val today = Clock.System.todayAt(TimeZone.currentSystemDefault())
            val eventDay = LocalDate.parse(currentEventEntity.date)
            when (val daysLeft = today.daysUntil(eventDay)) {
                in 0..20 -> dateTime.text = getString(
                    R.string.daytime_text_with_left_days,
                    daysLeft,
                    eventDay.toString()
                )

                else -> dateTime.text = getString(
                    R.string.daytime_text_without_left_days,
                    eventDay.toString()
                )
            }

            foundation.text = currentEventEntity.foundation
            address.text = currentEventEntity.location
            contactInfo.text = currentEventEntity.contactInfo
            val imageResource = ImageResource.from(currentEventEntity.imageName)
            imageView.setImageResource(imageResource.resourceId)
            description.text = currentEventEntity.description

        }
    }

    companion object {
        private const val EVENT_DETAIL_KEY = "selected_event_key"
        fun newInstance(eventEntity: EventEntity) = EventDetailFragment().apply {
            arguments = Bundle().apply { putParcelable(EVENT_DETAIL_KEY, eventEntity) }
        }
    }
}