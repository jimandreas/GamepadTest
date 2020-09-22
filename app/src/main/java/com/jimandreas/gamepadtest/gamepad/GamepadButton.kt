package com.jimandreas.gamepadtest.gamepad

import android.view.KeyEvent

class GamepadButton {

    interface GamepadKeyCallback {
        fun onKeyDown(keycode: Int, keyEvent: KeyEvent?)
        fun onKeyUp(keycode: Int, keyEvent: KeyEvent?)
    }

    var keyListener: GamepadKeyCallback? = null

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