@file:Suppress("UnnecessaryVariable", "JoinDeclarationAndAssignment")

package com.bammellab.gamepadtest.ui.scan

import android.content.Context
import android.util.Log
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
            TODO("Not yet implemented")
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


