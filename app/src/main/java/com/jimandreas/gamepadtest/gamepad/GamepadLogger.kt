package com.jimandreas.gamepadtest.gamepad

import android.view.KeyEvent

class GamepadLogger {

    val numToString = GamepadServices.keycodes.keyEventMap

    fun logKeyEventDown(keyCode: Int) {
        val keyStr = numToString[keyCode]
        println("DOWN: $keyCode($keyStr)")
    }

    fun logKeyEventUp(keyCode: Int) {
        val keyStr = numToString[keyCode]
        println("  UP: $keyCode($keyStr)")
    }
}