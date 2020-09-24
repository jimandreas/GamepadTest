@file:Suppress("UnnecessaryVariable")

package com.jimandreas.gamepadtest.ui.scan

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jimandreas.gamepadtest.R
import com.jimandreas.gamepadtest.ui.scan.DeviceAdapter.DeviceViewHolder


class DeviceAdapter(
    private val contextLocal: Context,
    private val clickHandler: DeviceAdapterOnClickHandler)
    : RecyclerView.Adapter<DeviceViewHolder>() {


    inner class DeviceViewHolder internal constructor(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

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
        val rootView = inflateStuff.inflate(R.layout.list_item, parent, false)

        return DeviceViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        Log.i("DeviceAdapter", "OnBIND")
        holder.deviceInfoText.text = "hello from RECYCLERVIEW"
    }

    override fun getItemCount(): Int {
        return 1
    }

}


