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
                val clip = ClipData.newPlainText(clipLabel, textView.text)
                clipboard.setPrimaryClip(clip) // API 30
                Snackbar.make(textView,
                    "Copied $clipLabel", Snackbar.LENGTH_LONG).show()
            }
        }

    }

    /**
     *
     * link:  https://gist.github.com/florina-muntenescu/08d751d843d55b75061039fee4e97931
     *
     */
   /* fun formatString(context: Context, str: String) {
        // get the text as SpannedString so we can get the spans attached to the text
        val theTextString = SpannedString(str)

        val annotations = theTextString.getSpans(
            0,
            theTextString.length,
            Annotation::class.java
        )// create a copy of the title text as a SpannableString
        // so we can add and remove spans
        val spannableString = SpannableString(theTextString)// iterate through all the annotation spans
        for (annotation in annotations) {       // look for the span with the key "font"
            if (annotation.key == "font") {
                val fontName =
                    annotation.value         // check the value associated with the annotation key
                if (fontName == "title_emphasis") {             // create the typeface
                    val typeface =
                        ResourcesCompat.getFont(context, R. .font.permanent_marker)             // set the span to the same indices as the annotation
                    spannableString.setSpan(
                        CustomTypefaceSpan(typeface),
                        theTextString.getSpanStart(annotation),
                        theTextString.getSpanEnd(annotation),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }// now the spannableString contains both the annotation spans and the CustomTypefaceSpan
        styledText.text = spannableString
    }*/
}