package com.bammellab.gamepadtest.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

object Util {
    /**
     * Param:  cliplabel, textview, context
     */
    fun attachClickToCopyText(textView: TextView?, clipLabel: String, context: Context?) {
        if (textView != null && null != context) {
            textView.setOnClickListener {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(clipLabel, textView!!.text)
                clipboard.setPrimaryClip(clip) // API 30
                Snackbar.make(textView,
                    "Copied $clipLabel", Snackbar.LENGTH_LONG).show()
            }
        }

    }
}