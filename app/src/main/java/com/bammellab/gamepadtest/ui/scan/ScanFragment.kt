/*
 *
 *  * Copyright 2023 Bammellab / James Andreas
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License
 *
 */



@file:Suppress("UnnecessaryVariable")

package com.bammellab.gamepadtest.ui.scan

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.bammellab.gamepadtest.gamepad.GamepadServices
import com.bammellab.gamepadtest.gamepad.LocalBroadcastReceiver
import com.bammellab.gamepadtest.gamepad.SourceDataToString


class ScanFragment :
    Fragment(), InputManager.InputDeviceListener, LocalBroadcastReceiver.Callback {

    private lateinit var scanViewModel: ScanViewModel
    private lateinit var binding: FragmentScanBinding
    private lateinit var bcontext: Context
    private val sourceToString = SourceDataToString()

    private lateinit var theRecyclerView: RecyclerView
    private lateinit var scanDeviceAdapter: ScanDeviceAdapter
    private lateinit var contextLocal: Context
    private var scanner : BluetoothData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScanBinding.inflate(inflater)
        binding.lifecycleOwner = this
        contextLocal = binding.root.context
        GamepadServices.broadcastReceiver.setCallback(this)

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

        scanDeviceAdapter = ScanDeviceAdapter(
            binding.root.context,
            scanViewModel,
            this,
            object : ScanDeviceAdapter.DeviceAdapterOnClickHandler {
                override fun onClick() {
                    Toast.makeText(
                        activity,
                        "Recycler view - CardView click",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        theRecyclerView.adapter = scanDeviceAdapter
        updateDeviceStringArray()

        scanner = BluetoothData(binding.root.context)

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
        if (scanner != null) {
            try {
                val deviceArray = scanner!!.assembleDescriptionStrings()
                scanViewModel.updateDevStringArray(deviceArray)
                scanDeviceAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.e("ScanFragment", "Exception in Bluetooth scanning")
            }
        }
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

    override fun updateBluetoothStatus(state: Int) {
        updateDeviceStringArray()
    }
}