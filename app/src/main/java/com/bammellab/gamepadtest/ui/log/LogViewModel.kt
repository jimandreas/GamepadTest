package com.bammellab.gamepadtest.ui.log

import android.text.Spanned
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bammellab.gamepadtest.gamepad.GamepadServices
import com.bammellab.gamepadtest.gamepad.UpdateLogList

class LogViewModel : ViewModel(), UpdateLogList {

    private val logList = GamepadServices.gamepadLoggerService.getLogList()

    private val _logInfoStringArray = MutableLiveData<List<Spanned>>().apply {
        value = logList
    }

    val logInfoStringArray: LiveData<List<Spanned>> = _logInfoStringArray

    init {
        GamepadServices.gamepadLoggerService.addLogListener(this)
    }

    override fun updateList(list:MutableList<Spanned>) {
        _logInfoStringArray.value = list
    }
}


