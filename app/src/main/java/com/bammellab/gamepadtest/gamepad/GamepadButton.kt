/*
 *
 * Copyright 2023-2025 Bammellab / James Andreas
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 *
 */

package com.bammellab.gamepadtest.gamepad

import android.view.KeyEvent

class GamepadButton {

    interface GamepadKeyCallback {
        fun onKeyDown(keycode: Int, keyEvent: KeyEvent?)
        fun onKeyUp(keycode: Int, keyEvent: KeyEvent?)
    }

    private var keyListener: GamepadKeyCallback? = null

    fun forwardButtonDown(keyCode: Int, event: KeyEvent?) {
        val logger = GamepadServices.gamepadLoggerService
        logger.logKeyEventDown(keyCode)
        keyListener?.onKeyDown(keyCode, event)
    }

    fun forwardButtonUp(keyCode: Int, event: KeyEvent?) {
        val logger = GamepadServices.gamepadLoggerService
        logger.logKeyEventUp(keyCode)
        keyListener?.onKeyUp(keyCode, event)
    }

    fun initKeyListener(listener: GamepadKeyCallback){
        this.keyListener = listener
    }
}