package com.coolkosta.simbirsofttestapp.fragment


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolkosta.simbirsofttestapp.R
import com.coolkosta.simbirsofttestapp.adapter.HelpAdapter
import com.coolkosta.simbirsofttestapp.entity.HelpItem
import com.coolkosta.simbirsofttestapp.service.HelpCategoryService
import com.google.android.material.progressindicator.CircularProgressIndicator


class HelpFragment : Fragment() {

    private lateinit var progress: CircularProgressIndicator

    private lateinit var adapter: HelpAdapter
    private var helpItems: List<HelpItem>? = null

    @Suppress("DEPRECATION")
    private val helpReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val items =
                intent?.getParcelableArrayListExtra<HelpItem>(SERVICE_DATA_KEY) as List<HelpItem>
            items.let {
                helpItems = it
                adapter.updateList(it)
                progress.visibility = View.GONE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            helpItems =
                savedInstanceState.getParcelableArrayList(SAVED_INSTANCE_KEY, HelpItem::class.java)
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
        } else progress.visibility = View.GONE
        val serviceIntent = Intent(requireContext(), HelpCategoryService::class.java)
        requireContext().startService(serviceIntent)
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(
            helpReceiver, IntentFilter(ACTION_SERVICE_FILTER_KEY),
            Context.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(helpReceiver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SAVED_INSTANCE_KEY, helpItems?.let { ArrayList(it) })
    }

    companion object {
        const val SERVICE_DATA_KEY = "help_items"
        const val ACTION_SERVICE_FILTER_KEY = "help_item_action"
        private const val SAVED_INSTANCE_KEY = "saved_data"
        fun newInstance() = HelpFragment()
    }
}
