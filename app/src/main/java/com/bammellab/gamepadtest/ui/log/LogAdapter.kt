@file:Suppress("UnnecessaryVariable")

package com.bammellab.gamepadtest.ui.log

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bammellab.gamepadtest.R
import com.bammellab.gamepadtest.ui.log.LogAdapter.DeviceViewHolder


class LogAdapter(
    private val contextLocal: Context,
    private val logViewModel: LogViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val clickHandler: DeviceAdapterOnClickHandler
) : RecyclerView.Adapter<DeviceViewHolder>() {

    private var devStringList: List<String> = listOf("FUNKY FUNKY")

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
        val rootView = inflateStuff.inflate(R.layout.list_item, parent, false)

        return DeviceViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        Log.i("DeviceAdapter", "OnBIND")

        logViewModel.devInfoStringArray.observe(lifecycleOwner, {
            devStringList = it
        })

        if (position <= devStringList.size-1) {
            holder.deviceInfoText.text = devStringList[position]
        } else {
            holder.deviceInfoText.text = contextLocal.getString(R.string.funky)
        }
    }

    override fun getItemCount(): Int {
        return devStringList.size
    }

}


