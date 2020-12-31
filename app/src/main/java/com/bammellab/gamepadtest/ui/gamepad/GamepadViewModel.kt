@file:Suppress("MoveVariableDeclarationIntoWhen")

package com.bammellab.gamepadtest.ui.gamepad

import android.view.KeyEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bammellab.gamepadtest.gamepad.GamepadButton
import com.bammellab.gamepadtest.gamepad.GamepadServices
import com.bammellab.gamepadtest.gamepad.UpdateJoystickData


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



    }

    override fun onKeyUp(keycode: Int, keyEvent: KeyEvent?) {
        _buttonDown.value = Pair(keycode, false)


    }



}