package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.BundleCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.coolkosta.news.R
import com.coolkosta.news.di.NewsComponentProvider
import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.news.util.ImageResource
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayAt

class EventDetailFragment : Fragment() {

    private val viewModel: EventDetailViewModel by viewModels {
        (requireActivity().application as NewsComponentProvider).getNewsComponent()
            .newsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendEvent(
            EventDetailsEvent.CurrentEvent(
                BundleCompat.getParcelable(
                    requireArguments(),
                    EVENT_DETAIL_KEY,
                    EventEntity::class.java
                ) as EventEntity
            )
        )
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

        val title = view.findViewById<TextView>(R.id.event_title_tv)
        val dateTime = view.findViewById<TextView>(R.id.event_datetime_tv)
        val foundation = view.findViewById<TextView>(R.id.foundation_tv)
        val address = view.findViewById<TextView>(R.id.address_tv)
        val contactInfo = view.findViewById<TextView>(R.id.contact_phone_tv)
        val imageView = view.findViewById<ImageView>(R.id.card_image_1)
        val description = view.findViewById<TextView>(R.id.event_description_tv)
        val donate = view.findViewById<TextView>(R.id.donation)

        donate.setOnClickListener {
            showDonateDialog()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->

                    state.event?.let {
                        toolbar.title = state.event.title
                        title.text = state.event.title
                        val today = Clock.System.todayAt(TimeZone.currentSystemDefault())
                        val eventDay = LocalDate.parse(state.event.date)
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
                        foundation.text = state.event.foundation
                        address.text = state.event.location
                        contactInfo.text = state.event.contactInfo
                        val imageResource = ImageResource.from(state.event.imageName)
                        imageView.setImageResource(imageResource.resourceId)
                        description.text = state.event.description
                    }
                }
            }
        }
    }

    private fun showDonateDialog() {
        val dialogView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_event_detail, null)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(dialogView)
            .create()
        val sendButton = dialogView.findViewById<Button>(R.id.send_button)
        dialogView.findViewById<MaterialButtonToggleGroup>(R.id.toggleButton)
            .addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        R.id.amount_100_btn -> {
                            viewModel.sendEvent(EventDetailsEvent.DonationQueryChanged(100))
                        }

                        R.id.amount_500_btn -> {
                            viewModel.sendEvent(EventDetailsEvent.DonationQueryChanged(500))
                        }

                        R.id.amount_1000_btn -> {
                            viewModel.sendEvent(EventDetailsEvent.DonationQueryChanged(1000))
                        }

                        R.id.amount_2000_btn -> {
                            viewModel.sendEvent(EventDetailsEvent.DonationQueryChanged(2000))
                        }
                    }
                }
            }

        dialogView.findViewById<EditText>(R.id.sum_edit_text)
            .addTextChangedListener {
                viewModel.sendEvent(
                    if (it.isNullOrBlank()) {
                        EventDetailsEvent.DonationQueryChanged(0)
                    } else {
                        EventDetailsEvent.DonationQueryChanged(it.toString().toInt())
                    }
                )
            }

        dialogView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            dialog.dismiss()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    sendButton.isEnabled = state.isEnabled
                }
            }
        }
        sendButton.setOnClickListener {
            viewModel.sendEvent(EventDetailsEvent.DonationTransferred)
            dialog.dismiss()
        }
        dialog.show()
    }

    companion object {
        private const val EVENT_DETAIL_KEY = "selected_event_key"
        fun newInstance(eventEntity: EventEntity) = EventDetailFragment().apply {
            arguments = Bundle().apply { putParcelable(EVENT_DETAIL_KEY, eventEntity) }
        }
    }
}