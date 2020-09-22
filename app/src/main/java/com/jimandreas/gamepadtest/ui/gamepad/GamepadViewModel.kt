@file:Suppress("MoveVariableDeclarationIntoWhen")

package com.jimandreas.gamepadtest.ui.gamepad

import android.util.Log
import android.view.KeyEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jimandreas.gamepadtest.gamepad.GamepadButton
import com.jimandreas.gamepadtest.gamepad.GamepadServices
import com.jimandreas.gamepadtest.gamepad.UpdateJoystickData


class GamepadViewModel : ViewModel(), UpdateJoystickData, GamepadButton.GamepadKeyCallback {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the gamepad Fragment"
    }
    val text: LiveData<String> = _text

    private val _joyLeft = MutableLiveData<Pair<Float, Float>>().apply {
        value = Pair(0f, 0f)
    }
    val joyLeft: LiveData<Pair<Float, Float>> = _joyLeft

    private val _joyRight = MutableLiveData<Pair<Float, Float>>().apply {
        value = Pair(0f, 0f)
    }
    val joyRight: LiveData<Pair<Float, Float>> = _joyRight

    private val _triggerLeft = MutableLiveData<Float>().apply {
        value = 0f
    }
    val triggerLeft: LiveData<Float> = _triggerLeft

    private val _triggerRight = MutableLiveData<Float>().apply {
        value = 0f
    }
    val triggerRight: LiveData<Float> = _triggerRight

    /**
     * The Int is the keycode
     * If the Boolean is true it is a keydown, otherwise keyup
     */
    private val _buttonDown = MutableLiveData<Pair<Int, Boolean>>().apply {
        value = Pair(0, false)
    }
    val buttonDown: LiveData<Pair< Int, Boolean>> = _buttonDown

    init {
        GamepadServices.gamepadJoystickService.initOnClickInterface(this)
        GamepadServices.gamepadButtonService.initKeyListener(this)
    }

    override fun updateJoysticks(lx: Float, ly: Float, rx: Float, ry: Float) {
        val logString = StringBuilder()
            .append("leftX ", String.format("%4.2f", lx))
            .append(" leftY ", String.format("%4.2f", ly))
            .append(" rightX ", String.format("%4.2f", rx))
            .append(" rightY ", String.format("%4.2f", ry))
            .toString()

        Log.i("GAMEPAD VIEWMODEL:", logString)

        _joyLeft.value = Pair(lx, ly)
        _joyRight.value = Pair(rx, ry)

    }

    override fun updateTriggers(left: Float, right: Float) {
        _triggerLeft.value = left
        _triggerRight.value = right
    }

    /**
     *  dPad events are sent as Motion Events.  Go figure. Probably for
     *  historical reasons.   Transform them into KeyEvents
     */
    override fun updateDpad(
        left: Boolean,
        right: Boolean,
        up: Boolean,
        down: Boolean,
        center: Boolean
    ) {
        if (left) {
            onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null)
        } else {
            onKeyUp(KeyEvent.KEYCODE_DPAD_LEFT, null)
        }
        if (right) {
            onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null)
        } else {
            onKeyUp(KeyEvent.KEYCODE_DPAD_RIGHT, null)
        }
        if (up) {
            onKeyDown(KeyEvent.KEYCODE_DPAD_UP, null)
        } else {
            onKeyUp(KeyEvent.KEYCODE_DPAD_UP, null)
        }
        if (down) {
            onKeyDown(KeyEvent.KEYCODE_DPAD_DOWN, null)
        } else {
            onKeyUp(KeyEvent.KEYCODE_DPAD_DOWN, null)
        }
        if (center) {
            onKeyDown(KeyEvent.KEYCODE_DPAD_CENTER, null)
        } else {
            onKeyUp(KeyEvent.KEYCODE_DPAD_CENTER, null)
        }
    }

    

    override fun onKeyDown(keycode: Int, keyEvent: KeyEvent?) {
        _buttonDown.value = Pair(keycode, true)

//        when (keycode) {
//            KeyEvent.KEYCODE_DPAD_LEFT -> Log.i("VMdown", "LEFT")
//            KeyEvent.KEYCODE_DPAD_RIGHT -> Log.i("VMdown", "RIGHT")
//            KeyEvent.KEYCODE_DPAD_UP -> Log.i("VMdown", "UP")
//            KeyEvent.KEYCODE_DPAD_DOWN -> Log.i("VMdown", "DOWN")
//            KeyEvent.KEYCODE_DPAD_CENTER -> Log.i("VMdown", "CENTER")
//
//            KeyEvent.KEYCODE_BUTTON_R1 -> Log.i("VMdown", "R1")
//            KeyEvent.KEYCODE_BUTTON_L1 -> Log.i("VMdown", "L1")
//
//
//            KeyEvent.KEYCODE_BUTTON_A -> Log.i("VMdown", "A")
//            KeyEvent.KEYCODE_BUTTON_B -> Log.i("VMdown", "B")
//            KeyEvent.KEYCODE_BUTTON_C -> Log.i("VMdown", "C")
//
//
//            KeyEvent.KEYCODE_BUTTON_X -> Log.i("VMdown", "X")
//            KeyEvent.KEYCODE_BUTTON_Y -> Log.i("VMdown", "Y")
//            KeyEvent.KEYCODE_BUTTON_Z -> Log.i("VMdown", "Z")
//
//
//            KeyEvent.KEYCODE_BUTTON_THUMBL -> Log.i("VMdown", "THUMB L")
//            KeyEvent.KEYCODE_BUTTON_THUMBR -> Log.i("VMdown", "THUMB R")
//
//
//            else -> Log.i("VMdown", "Other $keycode")
//        }

    }

    override fun onKeyUp(keycode: Int, keyEvent: KeyEvent?) {
        _buttonDown.value = Pair(keycode, false)

//        when (keycode) {
//            KeyEvent.KEYCODE_DPAD_LEFT -> Log.i("VMup", "LEFT")
//            KeyEvent.KEYCODE_DPAD_RIGHT -> Log.i("VMup", "RIGHT")
//            KeyEvent.KEYCODE_DPAD_UP -> Log.i("VMup", "UP")
//            KeyEvent.KEYCODE_DPAD_DOWN -> Log.i("VMup", "DOWN")
//            KeyEvent.KEYCODE_DPAD_CENTER -> Log.i("VMup", "CENTER")
//
//            KeyEvent.KEYCODE_BUTTON_R1 -> Log.i("VMup", "R1")
//            KeyEvent.KEYCODE_BUTTON_L1 -> Log.i("VMup", "L1")
//
//
//            KeyEvent.KEYCODE_BUTTON_A -> Log.i("VMup", "A")
//            KeyEvent.KEYCODE_BUTTON_B -> Log.i("VMup", "B")
//            KeyEvent.KEYCODE_BUTTON_C -> Log.i("VMup", "C")
//
//
//            KeyEvent.KEYCODE_BUTTON_X -> Log.i("VMup", "X")
//            KeyEvent.KEYCODE_BUTTON_Y -> Log.i("VMup", "Y")
//            KeyEvent.KEYCODE_BUTTON_Z -> Log.i("VMup", "Z")
//
//            else -> Log.i("VMup", "Other $keycode")
//        }

    }



}