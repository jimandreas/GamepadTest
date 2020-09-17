@file:Suppress("unused")

package com.jimandreas.gamepadtest.gamepad


import android.os.Vibrator
import android.util.Log
import android.view.InputDevice
import android.view.MotionEvent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import kotlin.math.abs


interface UpdateJoystickData {
    fun updateJoysticks(lx: Float, ly: Float, rx: Float, ry: Float)
    fun updateDpad(left: Boolean, right: Boolean, up: Boolean, down: Boolean, center: Boolean)
    fun updateTriggers(left: Float, right: Float)
}

class GamepadJoysticks() {

    init {
        getDeviceInfo()
    }

    private fun getDeviceInfo() {
        val ids = InputDevice.getDeviceIds()
        var str = StringBuilder("#devs: " + ids.size)
        var dev: InputDevice
        var vib: Vibrator
        for (id in ids) {
            dev = InputDevice.getDevice(id)
            str.append("dev $id")
            str.append("name: " + dev.name)
            vib = dev.vibrator
            str.append("has vib: " + vib.hasVibrator())
            if (vib.hasVibrator()) {
                vib.vibrate(1000)
            }
        }

    }

    interface JoysticksCallback {
        fun onJoysticksUpdate(left: Pair<Float, Float>, right: Pair<Float, Float>)
        fun onJoysticksAvailability(available: Boolean)
    }

    var listener: UpdateJoystickData? = null

    private var leftX: Float = 0f
    private var leftY: Float = 0f
    private var rightX: Float = 0f
    private var rightY: Float = 0f

    private var lTrigger: Float = 0f
    private var rTrigger: Float = 0f

    private var dPadX: Float = 0f
    private var dPadY: Float = 0f


    fun processJoystickInput(event: MotionEvent, historyPos: Int) {

        with(event) {
            leftX = getCenteredAxis(this, MotionEvent.AXIS_X, historyPos)
            leftY = getCenteredAxis(this, MotionEvent.AXIS_Y, historyPos)
            rightX = getCenteredAxis(this, MotionEvent.AXIS_Z, historyPos)
            rightY = getCenteredAxis(this, MotionEvent.AXIS_RZ, historyPos)

            lTrigger = event.getAxisValue(MotionEvent.AXIS_BRAKE)
            rTrigger = event.getAxisValue(MotionEvent.AXIS_GAS)


            val logString = StringBuilder()
                .append("leftX ", String.format("%4.2f", leftX))
                .append(" leftY ", String.format("%4.2f", leftY))
                .append(" rightX ", String.format("%4.2f", rightX))
                .append(" rightY ", String.format("%4.2f", rightY))
                .append(" LTrig ", String.format("%4.2f", lTrigger))
                .append(" RTrig ", String.format("%4.2f", rTrigger))
                .append(" position ", String.format("%d", historyPos))
                .toString()

            Log.i("GAMEPAD:", logString)

            val dpad = source and InputDevice.SOURCE_DPAD
            dPadX = getAxisValue(MotionEvent.AXIS_HAT_X)
            dPadY = getAxisValue(MotionEvent.AXIS_HAT_Y)
            Log.i("DPAD:", "$dpad $dPadX $dPadY")


        }

        if (listener != null) {
            listener?.updateJoysticks(leftX, leftY, rightX, rightY)
            listener?.updateTriggers(lTrigger, rTrigger)

            listener?.updateDpad(
                dPadX == -1.0f,
                dPadX == 1.0f,
                dPadY == -1.0f, // note that -1 is "UP"
                dPadY == 1.0f,
                false // haven't seen a "Center" implementation yet
            )
        }

    }

    fun initOnClickInterface(listener: UpdateJoystickData) {
        this.listener = listener
    }

    private fun getCenteredAxis(
        event: MotionEvent,
        axis: Int,
        historyPos: Int
    ): Float {

        val device = event.device
        val range: InputDevice.MotionRange? = device.getMotionRange(axis, event.source)

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        range?.apply {
            val value: Float = if (historyPos < 0) {
                event.getAxisValue(axis)
            } else {
                event.getHistoricalAxisValue(axis, historyPos)
            }

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (abs(value) > flat) {
                return value
            }
        }
        return 0f
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startOfLifecycle() {
        Log.i("GAMEPAD:", " on start")

        // Note that the Thread the handler runs on is determined by a class called Looper.
        // In this case, no looper is defined, and it defaults to the main or UI thread.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer() {
        Log.i("GAMEPAD:", " on stop")
    }

}