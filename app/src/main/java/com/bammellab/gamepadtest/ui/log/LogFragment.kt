@file:Suppress("UnnecessaryVariable")

package com.bammellab.gamepadtest.ui.log

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bammellab.gamepadtest.databinding.FragmentLogBinding
import com.bammellab.gamepadtest.gamepad.SourceDataToString


class LogFragment : Fragment() {

    private lateinit var logViewModel: LogViewModel
    private lateinit var binding: FragmentLogBinding
    private lateinit var bcontext: Context

    private lateinit var theRecyclerView: RecyclerView
    private lateinit var logAdapter: LogAdapter
    private lateinit var contextLocal: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLogBinding.inflate(inflater)
        binding.lifecycleOwner = this
        contextLocal = binding.root.context

        logViewModel =
            ViewModelProvider(this).get(LogViewModel::class.java)
        binding.viewModel = logViewModel
        bcontext = binding.root.context

        theRecyclerView = binding.fragmentLogRecyclerView
        val layoutManager = LinearLayoutManager(binding.root.context)
        theRecyclerView.layoutManager = layoutManager
        theRecyclerView.setHasFixedSize(false)

        logAdapter = LogAdapter(
            binding.root.context,
            logViewModel,
            this )
//            object : LogAdapter.DeviceAdapterOnClickHandler {
//                override fun onClick() {
//                    Toast.makeText(
//                        activity,
//                        "Recycler view - CardView click",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
        theRecyclerView.adapter = logAdapter

        logViewModel.logInfoStringArray.observe(viewLifecycleOwner, {
            val stringList = it
            logAdapter.notifyDataSetChanged()
        })

        return binding.root
    }

    /**
     *
     * @link: https://developer.android.com/guide/topics/connectivity/bluetooth?hl=en
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // updateDeviceStringArray()
    }


}