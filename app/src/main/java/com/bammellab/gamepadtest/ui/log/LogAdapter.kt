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
import com.bammellab.gamepadtest.ui.log.LogAdapter.LogViewHolder


class LogAdapter(
    private val contextLocal: Context,
    private val logViewModel: LogViewModel,
    private val lifecycleOwner: LifecycleOwner,
) : RecyclerView.Adapter<LogViewHolder>() {

    private var devStringList: List<String> = listOf("")

    inner class LogViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        val deviceInfoText = view.findViewById<View>(R.id.logging_textview) as TextView
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val inflateStuff = LayoutInflater.from(parent.context)
        val rootView = inflateStuff.inflate(R.layout.fragment_log_list_item, parent, false)

        return LogViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        if ( logViewModel.logInfoStringArray.value != null) {
            val text =  logViewModel.logInfoStringArray.value!![position]
            holder.deviceInfoText.text = text
        } else {
            holder.deviceInfoText.text = contextLocal.getString(R.string.empty_list)
        }
    }

    override fun getItemCount(): Int {
        val size =  logViewModel.logInfoStringArray.value?.size ?: 0
        return size
    }

}


