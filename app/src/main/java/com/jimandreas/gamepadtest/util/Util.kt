package com.jimandreas.gamepadtest.util

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
                clipboard.primaryClip = clip
                Snackbar.make(textView,
                    "Copied $clipLabel", Snackbar.LENGTH_LONG).show()
            }
        }

    }
}