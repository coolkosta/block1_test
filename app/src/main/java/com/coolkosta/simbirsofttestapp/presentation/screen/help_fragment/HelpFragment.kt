package com.coolkosta.simbirsofttestapp.presentation.screen.help_fragment


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.presentation.model.HelpItem
import com.coolkosta.simbirsofttestapp.presentation.adapter.HelpAdapter
import com.coolkosta.simbirsofttestapp.service.HelpCategoryService
import com.google.android.material.progressindicator.CircularProgressIndicator


class HelpFragment : Fragment() {

    private lateinit var progress: CircularProgressIndicator

    private lateinit var adapter: HelpAdapter
    private var helpItems: List<HelpItem>? = null

    private val helpReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                val items = IntentCompat.getParcelableArrayListExtra(
                    intent,
                    SERVICE_DATA_KEY,
                    HelpItem::class.java
                ) as? List<HelpItem>
                items?.let {
                    helpItems = it
                    adapter.updateList(it)
                    progress.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            helpItems =
                BundleCompat.getParcelableArrayList(
                    savedInstanceState,
                    SAVED_INSTANCE_KEY,
                    HelpItem::class.java
                )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress = view.findViewById(R.id.progress_circular)
        if (helpItems == null) {
            progress.visibility = View.VISIBLE
            val serviceIntent = Intent(requireContext(), HelpCategoryService::class.java)
            requireContext().startService(serviceIntent)
        } else progress.visibility = View.GONE
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_container)
        adapter = HelpAdapter(helpItems ?: emptyList())
        recyclerView.layoutManager =
            GridLayoutManager(
                context,
                2,
                LinearLayoutManager.VERTICAL,
                false
            )
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        ContextCompat.registerReceiver(
            requireActivity(),
            helpReceiver, IntentFilter(ACTION_SERVICE_FILTER_KEY),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(helpReceiver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        helpItems?.let {
            outState.putParcelableArrayList(SAVED_INSTANCE_KEY, ArrayList(it))
        }
    }

    companion object {
        const val SERVICE_DATA_KEY = "help_items"
        const val ACTION_SERVICE_FILTER_KEY = "help_item_action"
        private const val SAVED_INSTANCE_KEY = "saved_data"
        fun newInstance() = HelpFragment()
    }
}
