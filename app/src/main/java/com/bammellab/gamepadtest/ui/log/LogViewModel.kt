package com.bammellab.gamepadtest.ui.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogViewModel : ViewModel() {


    private val _logInfoStringArray = MutableLiveData<List<String>>().apply {
        value = listOf(
            """
                Logging recycler view
                this
                that
                the other thing
                this
                that
                the other thing
                this
                that
                the other thing
                this
                that
                the other thing
                this
                that
                the other thing
                this
                that
                the other thing
                this
                that
                the other thingthis
                that
                the other thingthis
                that
                the other thing
                this
                that
                the other thing
                this
                that
                the other thing
                this
                that
                the other thing
                this
                that
                the other thing
                this
                that
                the other thing
                
                
            """.trimIndent()
        )

    }
    val logInfoStringArray: LiveData<List<String>> = _logInfoStringArray

    fun updateLogInfoStringArray(devInfo: List<String>) {
        _logInfoStringArray.value = devInfo
    }

    // TODO: copy the contents to the clipboard on a long click
    fun onClick() {

    }
}


