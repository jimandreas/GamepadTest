package com.bammellab.gamepadtest.ui.log

import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bammellab.gamepadtest.gamepad.GamepadJoysticks
import com.bammellab.gamepadtest.gamepad.GamepadServices
import com.bammellab.gamepadtest.gamepad.UpdateLogString

class LogViewModel : ViewModel(), UpdateLogString {

    val firstString : Spanned =
        HtmlCompat.fromHtml("Logging recycler View", HtmlCompat.FROM_HTML_MODE_LEGACY)
    private val logList = mutableListOf(firstString)

    private val _logInfoStringArray = MutableLiveData<List<Spanned>>().apply {
        value = logList
    }

    val logInfoStringArray: LiveData<List<Spanned>> = _logInfoStringArray

    init {
        GamepadServices.gamepadJoystickService.addLogListener(this)
    }

    // TODO: copy the contents to the clipboard on a long click
    fun onClick() {

    }

    override fun addNewString(str: Spanned) {
        //logList.add(str)

        logList.add(1, str)
        _logInfoStringArray.value = logList
    }
}


