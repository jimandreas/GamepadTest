@file:Suppress("UnnecessaryVariable")

package com.bammellab.gamepadtest.ui.scan

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bammellab.gamepadtest.databinding.FragmentScanBinding
import com.bammellab.gamepadtest.gamepad.BluetoothData
import com.bammellab.gamepadtest.gamepad.SourceDataToString


class ScanFragment : Fragment(), InputManager.InputDeviceListener {

    private lateinit var scanViewModel: ScanViewModel
    private lateinit var binding: FragmentScanBinding
    private lateinit var bcontext: Context
    private val sourceToString = SourceDataToString()

    private lateinit var theRecyclerView: RecyclerView
    private lateinit var deviceAdapter: DeviceAdapter
    private lateinit var contextLocal: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScanBinding.inflate(inflater)
        binding.lifecycleOwner = this
        contextLocal = binding.root.context

        scanViewModel =
            ViewModelProvider(this).get(ScanViewModel::class.java)
        binding.viewModel = scanViewModel
        bcontext = binding.root.context

        val im = contextLocal.getSystemService(
            Context.INPUT_SERVICE
        ) as InputManager
        im.registerInputDeviceListener(this, Handler(Looper.getMainLooper()))

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

    /**
     *
     * @link: https://developer.android.com/guide/topics/connectivity/bluetooth?hl=en
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateDeviceStringArray()
    }

    private fun updateDeviceStringArray() {
        val scanner = BluetoothData(binding.root.context)
        val deviceArray = scanner.assembleDescriptionStrings()
        scanViewModel.updateDevStringArray(deviceArray)
        deviceAdapter.notifyDataSetChanged()
    }

    override fun onInputDeviceAdded(deviceId: Int) {
        updateDeviceStringArray()
    }

    override fun onInputDeviceRemoved(deviceId: Int) {
        updateDeviceStringArray()
    }

    override fun onInputDeviceChanged(deviceId: Int) {
        updateDeviceStringArray()
    }
}