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

@file:Suppress("JoinDeclarationAndAssignment")

package com.bammellab.gamepadtest.gamepad

import android.text.Spanned
import android.text.SpannedString
import android.util.Log
import androidx.core.text.toSpannable
import com.bammellab.gamepadtest.R
import com.bammellab.gamepadtest.util.PrefsUtil


interface UpdateLogList {
    fun updateList(list: MutableList<Spanned>)
}

class GamepadLogger {

    private val logStringList: MutableList<Spanned> = mutableListOf(SpannedString(""))
    private val numToString = GamepadServices.keycodes.keyEventMap
    private var listenerLogList = mutableListOf<UpdateLogList?>()
    private var logMotionEventsFlag = true

    init {
        val key = PrefsUtil.prefsContext!!.resources.getString(R.string.settings_log_motion_key)
        val prefbool = PrefsUtil.getBool(key, true)
        flagToLogMotionEvents(prefbool)
    }

    fun logKeyEventDown(keyCode: Int) {
        val keyStr = numToString[keyCode] ?: ""
        Log.v("KeyEvent", "DOWN: $keyCode($keyStr)")
        addLogLine("$keyStr DOWN".toSpannable())
    }

    fun logKeyEventUp(keyCode: Int) {
        val keyStr = numToString[keyCode] ?: ""
        Log.v("KeyEvent", "  UP: $keyCode($keyStr)")
        addLogLine("$keyStr UP".toSpannable())
    }

    fun clearLogList() {
        val firstLine = logStringList[0]
        logStringList.clear()
        logStringList.add(firstLine)
    }

    fun getLogList(): List<Spanned> {
        return logStringList
    }

    fun addLogLine(logLine: Spanned) {
        logStringList.add(1, logLine)

        for (listener in listenerLogList) {
            listener?.updateList(logStringList)
        }
    }

    fun addLogListener(listener: UpdateLogList) {
        listenerLogList.add(listener)
    }

    fun flagToLogMotionEvents(prefbool: Boolean) {
        logMotionEventsFlag = prefbool
        if (prefbool) {
            logStringList[0] =
                "Logging: Motion Events + Key Events".toSpannable()
        } else {
            logStringList[0] =
                "Logging: Key Events".toSpannable()
        }

    }
}