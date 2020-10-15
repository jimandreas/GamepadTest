package com.bammellab.gamepadtest.ui.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogViewModel : ViewModel() {


    private val _devInfoStringArray = MutableLiveData<List<String>>().apply {
        value = listOf("")

    }
    val devInfoStringArray: LiveData<List<String>> = _devInfoStringArray

    fun updateDevStringArray(devInfo: List<String>) {
        _devInfoStringArray.value = devInfo
    }

    // TODO: copy the contents to the clipboard on a long click
    fun onClick() {

    }
}


