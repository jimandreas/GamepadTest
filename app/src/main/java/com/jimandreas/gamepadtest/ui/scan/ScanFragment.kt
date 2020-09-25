@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.ui.scan

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
import com.jimandreas.gamepadtest.databinding.FragmentScanBinding
import com.jimandreas.gamepadtest.gamepad.BluetoothData
import com.jimandreas.gamepadtest.gamepad.GamepadServices
import com.jimandreas.gamepadtest.gamepad.SourceDataToString

class ScanFragment : Fragment() {

    private lateinit var scanViewModel: ScanViewModel
    private lateinit var binding: FragmentScanBinding
    private lateinit var bcontext: Context
    private val sourceToString = SourceDataToString()

    private lateinit var theRecyclerView : RecyclerView
    private lateinit var deviceAdapter : DeviceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentScanBinding.inflate(inflater)
        binding.lifecycleOwner = this

        scanViewModel =
            ViewModelProvider(this).get(ScanViewModel::class.java)
        binding.viewModel = scanViewModel
        bcontext = binding.root.context


        theRecyclerView = binding.recyclerListThing
        val layoutManager = LinearLayoutManager(binding.root.context)
        theRecyclerView.layoutManager = layoutManager
        theRecyclerView.setHasFixedSize(false)

        deviceAdapter = DeviceAdapter(
            binding.root.context,
            scanViewModel,
            this,
            object : DeviceAdapter.DeviceAdapterOnClickHandler {
            override fun onClick() {
                Toast.makeText(
                    activity,
                    "Recycler view - CardView click",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        theRecyclerView.adapter = deviceAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val scanner = BluetoothData(binding.root.context)
        val deviceArray = scanner.assembleDescriptionStrings()
        val sizeOf = deviceArray.size

        scanViewModel.updateDevStringArray(deviceArray)
    }
}