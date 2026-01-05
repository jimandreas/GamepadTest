/*
 *
 * Copyright 2023-2025 Bammellab / James Andreas
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 *
 */

@file:Suppress("UnnecessaryVariable", "JoinDeclarationAndAssignment", "unused")

package com.bammellab.gamepadtest.ui.scan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bammellab.gamepadtest.R
import com.bammellab.gamepadtest.ui.scan.ScanDeviceAdapter.DeviceViewHolder


class ScanDeviceAdapter(
    private val contextLocal: Context,
    private val scanViewModel: ScanViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val clickHandler: DeviceAdapterOnClickHandler
) : RecyclerView.Adapter<DeviceViewHolder>() {

    inner class DeviceViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        val deviceInfoText = view.findViewById<View>(R.id.recycler_device_info_text) as TextView
        override fun onClick(v: View?) {
            // not implemented
        }
    }

    interface DeviceAdapterOnClickHandler {
        fun onClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val inflateStuff = LayoutInflater.from(parent.context)
        val rootView = inflateStuff.inflate(R.layout.fragment_scan_list_item, parent, false)

        return DeviceViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val strings : List<String>
        if (scanViewModel.devInfoStringArray.value == null) {
            holder.deviceInfoText.text = contextLocal.getString(R.string.funky)
            return
        }
        strings = scanViewModel.devInfoStringArray.value as List<String>
        if (position <= scanViewModel.devInfoStringArray.value!!.size-1) {
            holder.deviceInfoText.text = strings[position]
        } else {
            holder.deviceInfoText.text = contextLocal.getString(R.string.funky)
        }
    }

    override fun getItemCount(): Int {
        return scanViewModel.devInfoStringArray.value!!.size
    }

}


