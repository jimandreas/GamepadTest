@file:Suppress("unused")

package com.bammellab.gamepadtest.gamepad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.preference.PreferenceManager
import com.bammellab.gamepadtest.ui.settings.SettingsFragment
import kotlin.math.abs

interface UpdateJoystickData {
    fun updateJoysticks(lx: Float, ly: Float, rx: Float, ry: Float)
    fun updateDpad(left: Boolean, right: Boolean, up: Boolean, down: Boolean, center: Boolean)
    fun updateTriggers(left: Float, right: Float)
}

class GamepadJoysticks {
    private var listener: UpdateJoystickData? = null

    private var leftX: Float = 0f
    private var leftY: Float = 0f
    private var rightX: Float = 0f
    private var rightY: Float = 0f

    private var lTrigger: Float = 0f
    private var rTrigger: Float = 0f

    private var dPadX: Float = 0f
    private var dPadY: Float = 0f

    val logger = GamepadServices.gamepadLoggerService

    fun processJoystickInput(activityIn: Activity, event: MotionEvent) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activityIn)
        val doLogMotionEvents = sharedPreferences.getBoolean("motion", true)


        val action = event.action

        with(event) {
            leftX = getCenteredAxis(this, MotionEvent.AXIS_X)
            leftY = getCenteredAxis(this, MotionEvent.AXIS_Y)
            rightX = getCenteredAxis(this, MotionEvent.AXIS_Z)
            rightY = getCenteredAxis(this, MotionEvent.AXIS_RZ)

            lTrigger = getAxisValue(MotionEvent.AXIS_BRAKE)
            rTrigger = getAxisValue(MotionEvent.AXIS_GAS)

            dPadX = getAxisValue(MotionEvent.AXIS_HAT_X)
            dPadY = getAxisValue(MotionEvent.AXIS_HAT_Y)

        }

        if (doLogMotionEvents) {
            if (leftX != 0f
                || leftY != 0f
                || rightX != 0f
                || rightY != 0f

            ) {
                val logString = StringBuilder()
                    .append("leftX ", String.format("%4.2f", leftX))
                    .append(" leftY ", String.format("%4.2f", leftY))
                    .append(" rightX ", String.format("%4.2f", rightX))
                    .append(" rightY ", String.format("%4.2f", rightY))
                    .toString()

                Log.v("Joystick", logString)
            }

            if (lTrigger != 0f
                || rTrigger != 0f
            ) {
                val logString = StringBuilder()
                    .append("left Trig ", String.format("%4.2f", lTrigger))
                    .append(" right Trig ", String.format("%4.2f", rTrigger))
                    .toString()

                Log.v("Triggers", logString)
            }

            if (dPadX != 0f
                || dPadY != 0f
            ) {
                val logString = StringBuilder()
                    .append("dPadX ", String.format("%4.2f", dPadX))
                    .append(" dPadY ", String.format("%4.2f", dPadY))
                    .toString()

                Log.v("Dpad", logString)
            }
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
        axis: Int
    ): Float {

        val device = event.device
        val range = device.getMotionRange(axis, event.source)

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.

        val axisValue = event.getAxisValue(axis)

        // Ignore axis values that are within the 'flat' region of the
        // joystick axis center.

        if (abs(axisValue) > range.flat) {
            return axisValue
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