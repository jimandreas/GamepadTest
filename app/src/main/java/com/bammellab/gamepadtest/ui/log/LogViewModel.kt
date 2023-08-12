/*
 *
 *  * Copyright 2023 Bammellab / James Andreas
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License
 *
 */

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


