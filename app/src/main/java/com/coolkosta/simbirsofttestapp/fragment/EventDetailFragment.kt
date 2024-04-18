package com.coolkosta.simbirsofttestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.util.ImageResource
import com.coolkosta.simbirsofttestapp.viewmodel.NewsViewModel

class EventDetailFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var title: TextView
    private lateinit var dateTime: TextView
    private lateinit var foundation: TextView
    private lateinit var address: TextView
    private lateinit var contactInfo: TextView
    private lateinit var imageView: ImageView
    private lateinit var description: TextView

    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById<Toolbar>(R.id.event_detail_toolbar).apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
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

        viewModel.currentEvent.observe(viewLifecycleOwner) { event ->
            toolbar.title = event.title
            title.text = event.title
            dateTime.text = event.date
            foundation.text = event.foundation
            address.text = event.location
            contactInfo.text = event.contactInfo
            val imageResource = ImageResource.from(event.imageName)
            imageView.setImageResource(imageResource.resourceId)
            description.text = event.description
        }
    }

    companion object {
        fun newInstance() = EventDetailFragment()
    }
}