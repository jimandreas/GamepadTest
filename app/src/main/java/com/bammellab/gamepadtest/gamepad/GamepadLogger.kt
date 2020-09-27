package com.bammellab.gamepadtest.gamepad

import android.util.Log

class GamepadLogger {

    private val numToString = GamepadServices.keycodes.keyEventMap

    fun logKeyEventDown(keyCode: Int) {
        val keyStr = numToString[keyCode]
        Log.v("KeyEvent","DOWN: $keyCode($keyStr)")
    }

    fun logKeyEventUp(keyCode: Int) {
        val keyStr = numToString[keyCode]
        Log.v("KeyEvent","  UP: $keyCode($keyStr)")
    }
}